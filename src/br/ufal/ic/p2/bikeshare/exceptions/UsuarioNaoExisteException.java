package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando nenhum usuario e encontrado para o identificador ou e-mail informado.
 */
public class UsuarioNaoExisteException extends BikeshareException {
    public UsuarioNaoExisteException() {
        super("Usuario nao existe.");
    }
}
