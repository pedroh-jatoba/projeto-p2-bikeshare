package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a validade do cartao nao segue o formato MM/AAAA com mes entre 01 e 12.
 */
public class ValidadeCartaoInvalidaException extends BikeshareException {
    public ValidadeCartaoInvalidaException() {
        super("Validade do cartao invalida.");
    }
}
