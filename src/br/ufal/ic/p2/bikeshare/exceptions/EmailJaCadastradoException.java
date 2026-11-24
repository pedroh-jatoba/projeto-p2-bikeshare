package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando ja existe um usuario cadastrado com o e-mail informado.
 */
public class EmailJaCadastradoException extends BikeshareException {
    public EmailJaCadastradoException() {
        super("Email ja cadastrado.");
    }
}
