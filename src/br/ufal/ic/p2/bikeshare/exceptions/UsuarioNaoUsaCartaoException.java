package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se consulta ou opera atributo de cartao em usuario que nao usa cartao.
 */
public class UsuarioNaoUsaCartaoException extends BikeshareException {
    public UsuarioNaoUsaCartaoException() {
        super("Usuario nao usa cartao.");
    }
}
