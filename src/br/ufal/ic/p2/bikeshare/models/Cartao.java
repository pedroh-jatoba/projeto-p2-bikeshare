package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.NumeroCartaoNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.ValidadeCartaoInvalidaException;
import br.ufal.ic.p2.bikeshare.exceptions.ValidadeCartaoNulaException;

/**
 * Metodo de pagamento por cartao, armazenando numero e data de validade ({@code MM/AAAA}).
 */
public class Cartao extends MetodoPagamento {
    private static final long serialVersionUID = 1L;

    private static final String PADRAO_VALIDADE = "^(0[1-9]|1[0-2])/\\d{4}$";

    private String numero;
    private String validade;

    /**
     * Cria um metodo de pagamento por cartao validando numero e data de validade.
     *
     * @param numero   numero do cartao
     * @param validade data de validade no formato {@code MM/AAAA}
     * @throws BikeshareException se o numero ou a validade forem nulos ou invalidos
     */
    public Cartao(String numero, String validade) throws BikeshareException {
        if (numero == null || numero.trim().isEmpty()) {
            throw new NumeroCartaoNuloException();
        }
        if (validade == null || validade.trim().isEmpty()) {
            throw new ValidadeCartaoNulaException();
        }
        if (!validade.trim().matches(PADRAO_VALIDADE)) {
            throw new ValidadeCartaoInvalidaException();
        }
        this.numero = numero;
        this.validade = validade.trim();
    }

    @Override
    public String getTipo() {
        return "cartao";
    }

    @Override
    public String getAtributo(String atributo) throws BikeshareException {
        if ("numeroCartao".equals(atributo)) {
            return this.numero;
        }
        if ("validadeCartao".equals(atributo)) {
            return this.validade;
        }
        return super.getAtributo(atributo);
    }

    /** @return numero do cartao */
    public String getNumero() { return numero; }

    /** @return data de validade do cartao ({@code MM/AAAA}) */
    public String getValidade() { return validade; }
}
