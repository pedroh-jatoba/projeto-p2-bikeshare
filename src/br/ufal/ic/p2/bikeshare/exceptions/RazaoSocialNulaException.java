package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a razao social informada para pessoa juridica e nula ou vazia.
 */
public class RazaoSocialNulaException extends BikeshareException {
    public RazaoSocialNulaException() {
        super("Razao social nao pode ser nula.");
    }
}
