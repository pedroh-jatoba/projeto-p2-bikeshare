package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a chave Pix informada e nula ou vazia.
 */
public class ChavePixNulaException extends BikeshareException {
    public ChavePixNulaException() {
        super("Chave pix nao pode ser nula.");
    }
}
