package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se consulta ou recarrega saldo em usuario que nao usa saldo pre-carregado.
 */
public class UsuarioNaoUsaSaldoPreCarregadoException extends BikeshareException {
    public UsuarioNaoUsaSaldoPreCarregadoException() {
        super("Usuario nao usa saldo pre-carregado.");
    }
}
