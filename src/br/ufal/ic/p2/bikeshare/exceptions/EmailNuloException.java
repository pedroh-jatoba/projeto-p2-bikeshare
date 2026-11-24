package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o e-mail informado e nulo ou vazio.
 */
public class EmailNuloException extends BikeshareException {
    public EmailNuloException() {
        super("Email nao pode ser nulo.");
    }
}
