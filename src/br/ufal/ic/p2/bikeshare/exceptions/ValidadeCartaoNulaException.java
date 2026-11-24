package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a data de validade do cartao informada e nula ou vazia.
 */
public class ValidadeCartaoNulaException extends BikeshareException {
    public ValidadeCartaoNulaException() {
        super("Validade do cartao nao pode ser nula.");
    }
}
