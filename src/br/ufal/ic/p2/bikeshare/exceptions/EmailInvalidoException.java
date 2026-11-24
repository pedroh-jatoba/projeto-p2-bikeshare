package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o e-mail informado nao possui formato valido.
 */
public class EmailInvalidoException extends BikeshareException {
    public EmailInvalidoException() {
        super("Email invalido.");
    }
}
