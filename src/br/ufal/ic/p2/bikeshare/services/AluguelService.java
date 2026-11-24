package br.ufal.ic.p2.bikeshare.services;

import br.ufal.ic.p2.bikeshare.exceptions.AluguelNaoEstaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exceptions.AluguelNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoEstaNestaEstacaoException;
import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.DataDevolucaoAnteriorRetiradaException;
import br.ufal.ic.p2.bikeshare.exceptions.EstacaoCheiaException;
import br.ufal.ic.p2.bikeshare.exceptions.IdentificacaoAluguelNulaException;
import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.models.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.Usuario;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Servico responsavel pelo ciclo de vida dos alugueis (retirada, devolucao e consultas).
 */
public class AluguelService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Aluguel> alugueis;
    private int contadorAlugueis;
    private UsuarioService usuarioService;
    private EstacaoService estacaoService;
    private BicicletaService bicicletaService;

    /**
     * Inicializa o servico de alugueis com seus colaboradores de dominio.
     *
     * @param usuarioService   servico de usuarios
     * @param estacaoService   servico de estacoes
     * @param bicicletaService servico de bicicletas
     */
    public AluguelService(UsuarioService usuarioService, EstacaoService estacaoService, BicicletaService bicicletaService) {
        this.alugueis = new HashMap<>();
        this.contadorAlugueis = 1;
        this.usuarioService = usuarioService;
        this.estacaoService = estacaoService;
        this.bicicletaService = bicicletaService;
    }

    /**
     * Realiza a retirada de uma bicicleta disponivel em uma estacao, iniciando um novo aluguel.
     * Retiradas recusadas por validacao nao consomem identificador de aluguel.
     *
     * @param usuarioId   identificador do usuario
     * @param estacaoId   identificador da estacao de retirada
     * @param bicicletaId identificador da bicicleta a retirar
     * @param dataStr     data e hora de retirada no formato {@code d/M/uuuu H:mm}
     * @return identificador unico gerado para o aluguel (ex.: {@code A1})
     * @throws BikeshareException se alguma validacao de usuario, estacao, bicicleta, data ou pagamento falhar
     */
    public String retirarBicicleta(String usuarioId, String estacaoId, String bicicletaId, String dataStr) throws BikeshareException {
        Usuario usuario = usuarioService.buscarUsuario(usuarioId);
        Estacao estacao = estacaoService.buscarEstacao(estacaoId);
        Bicicleta bicicleta = bicicletaService.buscarBicicleta(bicicletaId);
        LocalDateTime dataInicio = Aluguel.parseDataHora(dataStr);

        usuario.validarElegibilidadeParaRetirada();

        if (!estacao.contemBicicleta(bicicletaId)) {
            throw new BicicletaNaoEstaNestaEstacaoException();
        }

        bicicleta.validarParaRetirada();
        usuario.validarPagamentoParaRetirada();

        String aluguelId = "A" + contadorAlugueis;
        Aluguel novoAluguel = new Aluguel(aluguelId, usuarioId, bicicletaId, estacaoId, dataInicio);

        alugueis.put(aluguelId, novoAluguel);
        contadorAlugueis++;

        estacao.removerBicicleta(bicicletaId);
        bicicleta.registrarRetirada();
        usuario.setAluguelAtivo(aluguelId);

        return aluguelId;
    }

    /**
     * Realiza a devolucao de uma bicicleta em uma estacao com vaga livre,
     * tratando expiracao (> 24h), cancelamento na carencia (mesma estacao, <= 2 min)
     * ou finalizacao normal com calculo polimorfico de tarifa e cobranca.
     *
     * @param aluguelId        identificador unico do aluguel em andamento
     * @param estacaoDestinoId identificador da estacao de devolucao
     * @param dataStr          data e hora de devolucao no formato {@code d/M/uuuu H:mm}
     * @throws BikeshareException se alguma validacao falhar, a estacao estiver cheia ou o saldo for insuficiente
     */
    public void devolverBicicleta(String aluguelId, String estacaoDestinoId, String dataStr) throws BikeshareException {
        Aluguel aluguel = buscarAluguel(aluguelId);
        Estacao estacaoDestino = estacaoService.buscarEstacao(estacaoDestinoId);
        LocalDateTime dataFim = Aluguel.parseDataHora(dataStr);

        if (!aluguel.estaEmAndamento()) {
            throw new AluguelNaoEstaEmAndamentoException();
        }
        if (dataFim.isBefore(aluguel.getInicio())) {
            throw new DataDevolucaoAnteriorRetiradaException();
        }
        if (estacaoDestino.getVagasDisponiveis() <= 0) {
            throw new EstacaoCheiaException();
        }

        Usuario usuario = usuarioService.buscarUsuario(aluguel.getUsuarioId());
        Bicicleta bicicleta = bicicletaService.buscarBicicleta(aluguel.getBicicletaId());
        long minutos = Duration.between(aluguel.getInicio(), dataFim).toMinutes();

        if (minutos > Aluguel.MINUTOS_MAXIMO_CORRIDA) {
            aluguel.expirar(estacaoDestinoId, dataFim);
            bicicleta.registrarDevolucao(estacaoDestinoId, "emManutencao");
            estacaoDestino.adicionarBicicleta(bicicleta);
            usuario.setAluguelAtivo(null);
            usuario.registrarPenalidade(Aluguel.PENALIDADE_EXPIRACAO);
            return;
        }

        if (estacaoDestinoId.equals(aluguel.getEstacaoOrigemId())
                && minutos <= Aluguel.MINUTOS_CARENCIA_CANCELAMENTO) {
            aluguel.cancelar(estacaoDestinoId, dataFim);
            bicicleta.registrarDevolucao(estacaoDestinoId, "disponivel");
            estacaoDestino.adicionarBicicleta(bicicleta);
            usuario.setAluguelAtivo(null);
            return;
        }

        double valorTotal = usuario.getPlano().calcularTarifa(minutos)
                + bicicleta.calcularAdicional(minutos);

        usuario.cobrarCorrida(valorTotal);

        aluguel.finalizar(estacaoDestinoId, dataFim, valorTotal);
        bicicleta.registrarDevolucao(estacaoDestinoId, "disponivel");
        estacaoDestino.adicionarBicicleta(bicicleta);
        usuario.setAluguelAtivo(null);
    }

    /**
     * Consulta o valor de um atributo de um aluguel registrado.
     *
     * @param aluguelId identificador unico do aluguel
     * @param atributo  nome do atributo desejado
     * @return representacao textual do atributo
     * @throws BikeshareException se o aluguel ou atributo nao existir, ou exigir aluguel encerrado
     */
    public String getAtributoAluguel(String aluguelId, String atributo) throws BikeshareException {
        Aluguel aluguel = buscarAluguel(aluguelId);
        return aluguel.getAtributo(atributo);
    }

    /**
     * Valida o identificador e retorna o aluguel correspondente no registro.
     *
     * @param aluguelId identificador do aluguel
     * @return instancia do aluguel encontrado
     * @throws BikeshareException se o identificador for nulo/vazio ou o aluguel nao existir
     */
    public Aluguel buscarAluguel(String aluguelId) throws BikeshareException {
        if (aluguelId == null || aluguelId.trim().isEmpty()) {
            throw new IdentificacaoAluguelNulaException();
        }
        Aluguel aluguel = alugueis.get(aluguelId);
        if (aluguel == null) {
            throw new AluguelNaoExisteException();
        }
        return aluguel;
    }
}
