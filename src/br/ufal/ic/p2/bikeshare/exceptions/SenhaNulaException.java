package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a senha do usuario informada e nula ou vazia.
 */
public class SenhaNulaException extends BikeshareException {
    public SenhaNulaException() {
        super("Senha nao pode ser nula.");
    }
}
