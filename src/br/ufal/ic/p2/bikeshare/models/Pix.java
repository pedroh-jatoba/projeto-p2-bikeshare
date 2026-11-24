package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.ChavePixNulaException;

/**
 * Metodo de pagamento via Pix, identificado por uma chave Pix.
 */
public class Pix extends MetodoPagamento {
    private static final long serialVersionUID = 1L;

    private String chave;

    /**
     * Cria um metodo de pagamento via Pix validando a chave obrigatoria.
     *
     * @param chave chave Pix do usuario
     * @throws BikeshareException se a chave for nula ou vazia
     */
    public Pix(String chave) throws BikeshareException {
        if (chave == null || chave.trim().isEmpty()) {
            throw new ChavePixNulaException();
        }
        this.chave = chave;
    }

    @Override
    public String getTipo() {
        return "pix";
    }

    @Override
    public String getAtributo(String atributo) throws BikeshareException {
        if ("chavePix".equals(atributo)) {
            return this.chave;
        }
        return super.getAtributo(atributo);
    }

    /** @return chave Pix cadastrada */
    public String getChave() { return chave; }
}
