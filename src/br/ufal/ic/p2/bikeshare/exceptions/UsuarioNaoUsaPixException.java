package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se consulta atributo de Pix em usuario que nao usa Pix.
 */
public class UsuarioNaoUsaPixException extends BikeshareException {
    public UsuarioNaoUsaPixException() {
        super("Usuario nao usa pix.");
    }
}
