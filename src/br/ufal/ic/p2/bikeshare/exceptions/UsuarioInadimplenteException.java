package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando um usuario inadimplente tenta iniciar um novo aluguel.
 */
public class UsuarioInadimplenteException extends BikeshareException {
    public UsuarioInadimplenteException() {
        super("Usuario inadimplente.");
    }
}
