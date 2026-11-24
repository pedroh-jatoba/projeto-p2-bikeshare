package br.ufal.ic.p2.bikeshare.services;

import br.ufal.ic.p2.bikeshare.exceptions.BicicletaJaExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoEstaNestaEstacaoException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.IdentificacaoBicicletaNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.NivelBateriaNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.TipoBicicletaInvalidoException;
import br.ufal.ic.p2.bikeshare.models.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.BicicletaConvencional;
import br.ufal.ic.p2.bikeshare.models.BicicletaEletrica;
import br.ufal.ic.p2.bikeshare.models.Estacao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Servico responsavel pelo cadastro, remocao e consulta de bicicletas nas estacoes.
 */
public class BicicletaService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Bicicleta> bicicletas;
    private EstacaoService estacaoService;

    /**
     * Inicializa o cadastro de bicicletas vinculado ao servico de estacoes.
     *
     * @param estacaoService servico de estacoes utilizado para validar e alocar vagas
     */
    public BicicletaService(EstacaoService estacaoService) {
        this.bicicletas = new HashMap<>();
        this.estacaoService = estacaoService;
    }

    /**
     * Adiciona uma bicicleta convencional em uma estacao.
     * Valida os dados da bicicleta antes de verificar a existencia de vaga disponivel.
     *
     * @param estacaoId   identificador da estacao de destino
     * @param bicicletaId identificador unico da bicicleta
     * @param tipo        tipo da bicicleta (deve ser {@code convencional})
     * @throws BikeshareException se algum parametro for invalido, a bicicleta ja existir ou a estacao estiver cheia
     */
    public void adicionarBicicleta(String estacaoId, String bicicletaId, String tipo) throws BikeshareException {
        Estacao estacao = estacaoService.buscarEstacao(estacaoId);
        validarNovaBicicleta(bicicletaId);

        if ("eletrica".equals(tipo)) {
            throw new NivelBateriaNuloException();
        }
        if (!"convencional".equals(tipo)) {
            throw new TipoBicicletaInvalidoException();
        }

        Bicicleta novaBicicleta = new BicicletaConvencional(bicicletaId, estacaoId);
        estacao.adicionarBicicleta(novaBicicleta);
        bicicletas.put(bicicletaId, novaBicicleta);
    }

    /**
     * Adiciona uma bicicleta eletrica com nivel de bateria informado em uma estacao.
     * Valida os dados da bicicleta antes de verificar a existencia de vaga disponivel.
     *
     * @param estacaoId    identificador da estacao de destino
     * @param bicicletaId  identificador unico da bicicleta
     * @param tipo         tipo da bicicleta (deve ser {@code eletrica})
     * @param nivelBateria nivel percentual inicial de bateria (0 a 100)
     * @throws BikeshareException se algum parametro for invalido, a bicicleta ja existir ou a estacao estiver cheia
     */
    public void adicionarBicicleta(String estacaoId, String bicicletaId, String tipo, String nivelBateria) throws BikeshareException {
        Estacao estacao = estacaoService.buscarEstacao(estacaoId);
        validarNovaBicicleta(bicicletaId);

        if ("convencional".equals(tipo)) {
            throw new BicicletaNaoPossuiBateriaException();
        }
        if (!"eletrica".equals(tipo)) {
            throw new TipoBicicletaInvalidoException();
        }

        Bicicleta novaBicicleta = new BicicletaEletrica(bicicletaId, estacaoId, nivelBateria);
        estacao.adicionarBicicleta(novaBicicleta);
        bicicletas.put(bicicletaId, novaBicicleta);
    }

    /**
     * Remove uma bicicleta estacionada em uma estacao, liberando a vaga e o identificador da bicicleta.
     *
     * @param estacaoId   identificador da estacao onde a bicicleta deve estar
     * @param bicicletaId identificador da bicicleta a remover
     * @throws BikeshareException se a estacao ou bicicleta nao existir, ou se a bicicleta nao estiver na estacao
     */
    public void removerBicicleta(String estacaoId, String bicicletaId) throws BikeshareException {
        Estacao estacao = estacaoService.buscarEstacao(estacaoId);
        buscarBicicleta(bicicletaId);

        if (!estacao.contemBicicleta(bicicletaId)) {
            throw new BicicletaNaoEstaNestaEstacaoException();
        }

        estacao.removerBicicleta(bicicletaId);
        bicicletas.remove(bicicletaId);
    }

    /**
     * Consulta o valor de um atributo de uma bicicleta cadastrada.
     *
     * @param bicicletaId identificador da bicicleta
     * @param atributo    nome do atributo desejado
     * @return valor textual do atributo
     * @throws BikeshareException se a bicicleta ou o atributo nao existir
     */
    public String getAtributoBicicleta(String bicicletaId, String atributo) throws BikeshareException {
        Bicicleta bicicleta = buscarBicicleta(bicicletaId);
        return bicicleta.getAtributo(atributo);
    }

    /**
     * Consulta o estado operacional atual de uma bicicleta cadastrada.
     *
     * @param bicicletaId identificador da bicicleta
     * @return estado da bicicleta (ex.: {@code disponivel})
     * @throws BikeshareException se o identificador for nulo/vazio ou a bicicleta nao existir
     */
    public String getEstadoBicicleta(String bicicletaId) throws BikeshareException {
        Bicicleta bicicleta = buscarBicicleta(bicicletaId);
        return bicicleta.getEstado();
    }

    /**
     * Valida o identificador e retorna a bicicleta correspondente no cadastro.
     *
     * @param bicicletaId identificador da bicicleta
     * @return instancia da bicicleta encontrada
     * @throws BikeshareException se o identificador for nulo/vazio ou a bicicleta nao existir
     */
    public Bicicleta buscarBicicleta(String bicicletaId) throws BikeshareException {
        if (bicicletaId == null || bicicletaId.trim().isEmpty()) {
            throw new IdentificacaoBicicletaNulaException();
        }
        Bicicleta bicicleta = bicicletas.get(bicicletaId);
        if (bicicleta == null) {
            throw new BicicletaNaoExisteException();
        }
        return bicicleta;
    }

    /**
     * Valida se o identificador de uma nova bicicleta e nao nulo e ainda nao esta em uso.
     *
     * @param bicicletaId identificador proposto para a bicicleta
     * @throws BikeshareException se o identificador for nulo/vazio ou ja existir
     */
    private void validarNovaBicicleta(String bicicletaId) throws BikeshareException {
        if (bicicletaId == null || bicicletaId.trim().isEmpty()) {
            throw new IdentificacaoBicicletaNulaException();
        }
        if (bicicletas.containsKey(bicicletaId)) {
            throw new BicicletaJaExisteException();
        }
    }
}
