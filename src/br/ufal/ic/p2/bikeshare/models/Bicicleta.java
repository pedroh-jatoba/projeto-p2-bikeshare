package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;

import java.io.Serializable;

/**
 * Representa uma bicicleta generica do sistema Bikeshare.
 * Especializada em {@link BicicletaConvencional} e {@link BicicletaEletrica}.
 */
public abstract class Bicicleta implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String estado;
    private String estacaoId;

    /**
     * Cria uma bicicleta no estado inicial {@code disponivel}, vinculada a uma estacao.
     *
     * @param id        identificador unico da bicicleta
     * @param estacaoId identificador da estacao onde a bicicleta foi estacionada
     */
    public Bicicleta(String id, String estacaoId) {
        this.id = id;
        this.estado = "disponivel";
        this.estacaoId = estacaoId;
    }

    /**
     * Retorna o tipo concreto da bicicleta ({@code convencional} ou {@code eletrica}).
     *
     * @return tipo da bicicleta
     */
    public abstract String getTipo();

    /**
     * Indica se a bicicleta esta apta a ser retirada por um usuario.
     * Subclasses podem acrescentar restricoes proprias (ex.: nivel minimo de bateria).
     *
     * @return {@code true} se estiver disponivel para retirada; {@code false} caso contrario
     */
    public boolean estaDisponivelParaRetirada() {
        return "disponivel".equals(this.estado);
    }

    /**
     * Consulta o valor de um atributo da bicicleta pelo seu nome.
     *
     * @param atributo nome do atributo consultado
     * @return representacao textual do atributo
     * @throws BikeshareException se o atributo nao existir ou nao se aplicar a este tipo de bicicleta
     */
    public String getAtributo(String atributo) throws BikeshareException {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "tipo":
                return this.getTipo();
            case "estado":
                return this.estado;
            case "estacao":
                return this.estacaoId == null ? "nenhuma" : this.estacaoId;
            case "nivelBateria":
                throw new BicicletaNaoPossuiBateriaException();
            default:
                throw new AtributoNaoExisteException();
        }
    }

    /** @return identificador unico da bicicleta */
    public String getId() { return id; }

    /** @return estado operacional atual da bicicleta */
    public String getEstado() { return estado; }

    /** @param estado novo estado operacional da bicicleta */
    public void setEstado(String estado) { this.estado = estado; }

    /** @return identificador da estacao atual, ou {@code null} se estiver em uso */
    public String getEstacaoId() { return estacaoId; }

    /** @param estacaoId novo identificador de estacao */
    public void setEstacaoId(String estacaoId) { this.estacaoId = estacaoId; }
}
