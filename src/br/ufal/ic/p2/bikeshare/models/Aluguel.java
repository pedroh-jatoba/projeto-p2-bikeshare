package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.AluguelEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exceptions.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.DataInvalidaException;
import br.ufal.ic.p2.bikeshare.exceptions.DataNulaException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * Representa um aluguel (corrida) de uma bicicleta por um usuario do sistema Bikeshare.
 */
public class Aluguel implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Prazo maximo em minutos para cancelamento sem cobranca na mesma estacao. */
    public static final long MINUTOS_CARENCIA_CANCELAMENTO = 2L;

    /** Duracao maxima em minutos de uma corrida normal (24 horas). Acima disso, expira. */
    public static final long MINUTOS_MAXIMO_CORRIDA = 24L * 60L;

    /** Valor da penalidade automatica aplicada em caso de expiracao do aluguel. */
    public static final double PENALIDADE_EXPIRACAO = 10.0;

    private static final DateTimeFormatter FORMATADOR_DATA_HORA =
            DateTimeFormatter.ofPattern("d/M/uuuu H:mm").withResolverStyle(ResolverStyle.STRICT);

    private String id;
    private String usuarioId;
    private String bicicletaId;
    private String estacaoOrigemId;
    private String estacaoDestinoId;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private String estado;
    private double valorCobrado;

    /**
     * Cria um novo aluguel no estado {@code emAndamento}.
     *
     * @param id              identificador unico do aluguel (ex.: {@code A1})
     * @param usuarioId       identificador do usuario que retirou a bicicleta
     * @param bicicletaId     identificador da bicicleta retirada
     * @param estacaoOrigemId identificador da estacao de retirada
     * @param inicio          data e hora de inicio da corrida
     */
    public Aluguel(String id, String usuarioId, String bicicletaId, String estacaoOrigemId, LocalDateTime inicio) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.bicicletaId = bicicletaId;
        this.estacaoOrigemId = estacaoOrigemId;
        this.estacaoDestinoId = null;
        this.inicio = inicio;
        this.fim = null;
        this.estado = "emAndamento";
        this.valorCobrado = 0.0;
    }

    /**
     * Converte uma data/hora textual no padrao {@code d/M/uuuu H:mm} com validacao estrita de calendario.
     *
     * @param dataStr representacao textual da data e hora
     * @return instancia de {@link LocalDateTime}
     * @throws BikeshareException se a data for nula/vazia ou invalida
     */
    public static LocalDateTime parseDataHora(String dataStr) throws BikeshareException {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            throw new DataNulaException();
        }
        try {
            return LocalDateTime.parse(dataStr.trim(), FORMATADOR_DATA_HORA);
        } catch (DateTimeParseException e) {
            throw new DataInvalidaException();
        }
    }

    /**
     * Formata uma data/hora no padrao textual esperado pelo sistema ({@code d/M/uuuu H:mm}).
     *
     * @param dataHora data e hora a formatar
     * @return representacao textual formatada
     */
    public static String formatarDataHora(LocalDateTime dataHora) {
        return dataHora.format(FORMATADOR_DATA_HORA);
    }

    /**
     * Consulta o valor de um atributo do aluguel pelo seu nome.
     *
     * @param atributo nome do atributo consultado
     * @return representacao textual do atributo
     * @throws BikeshareException se o atributo nao existir ou exigir que o aluguel esteja encerrado
     */
    public String getAtributo(String atributo) throws BikeshareException {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "estado":
                return this.estado;
            case "usuario":
                return this.usuarioId;
            case "bicicleta":
                return this.bicicletaId;
            case "estacaoOrigem":
                return this.estacaoOrigemId;
            case "inicio":
                return formatarDataHora(this.inicio);
            case "fim":
                if (estaEmAndamento()) {
                    throw new AluguelEmAndamentoException();
                }
                return formatarDataHora(this.fim);
            case "estacaoDestino":
                if (estaEmAndamento()) {
                    throw new AluguelEmAndamentoException();
                }
                return this.estacaoDestinoId;
            case "valorCobrado":
                if (estaEmAndamento()) {
                    throw new AluguelEmAndamentoException();
                }
                return String.format(new Locale("pt", "BR"), "%.2f", this.valorCobrado);
            default:
                throw new AtributoNaoExisteException();
        }
    }

    /**
     * Encerra o aluguel como {@code finalizado}, registrando destino, horario de fim e valor cobrado.
     *
     * @param estacaoDestinoId identificador da estacao de devolucao
     * @param fim              data e hora da devolucao
     * @param valorCobrado     valor total cobrado pela corrida
     */
    public void finalizar(String estacaoDestinoId, LocalDateTime fim, double valorCobrado) {
        this.estacaoDestinoId = estacaoDestinoId;
        this.fim = fim;
        this.valorCobrado = valorCobrado;
        this.estado = "finalizado";
    }

    /**
     * Encerra o aluguel como {@code cancelado} (devolucao na carencia sem cobranca).
     *
     * @param estacaoDestinoId identificador da estacao de devolucao
     * @param fim              data e hora da devolucao
     */
    public void cancelar(String estacaoDestinoId, LocalDateTime fim) {
        this.estacaoDestinoId = estacaoDestinoId;
        this.fim = fim;
        this.valorCobrado = 0.0;
        this.estado = "cancelado";
    }

    /**
     * Encerra o aluguel como {@code expirado} (tempo maximo de 24h excedido, sem tarifa de corrida).
     *
     * @param estacaoDestinoId identificador da estacao de devolucao
     * @param fim              data e hora da devolucao
     */
    public void expirar(String estacaoDestinoId, LocalDateTime fim) {
        this.estacaoDestinoId = estacaoDestinoId;
        this.fim = fim;
        this.valorCobrado = 0.0;
        this.estado = "expirado";
    }

    /** @return {@code true} se o aluguel estiver em andamento */
    public boolean estaEmAndamento() {
        return "emAndamento".equals(this.estado);
    }

    /** @return identificador unico do aluguel */
    public String getId() { return id; }

    /** @return identificador do usuario associado */
    public String getUsuarioId() { return usuarioId; }

    /** @return identificador da bicicleta associada */
    public String getBicicletaId() { return bicicletaId; }

    /** @return identificador da estacao de origem */
    public String getEstacaoOrigemId() { return estacaoOrigemId; }

    /** @return identificador da estacao de destino, ou {@code null} se em andamento */
    public String getEstacaoDestinoId() { return estacaoDestinoId; }

    /** @return data e hora de inicio */
    public LocalDateTime getInicio() { return inicio; }

    /** @return data e hora de encerramento, ou {@code null} se em andamento */
    public LocalDateTime getFim() { return fim; }

    /** @return estado atual do aluguel */
    public String getEstado() { return estado; }

    /** @return valor cobrado pela corrida */
    public double getValorCobrado() { return valorCobrado; }
}
