package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o tipo de usuario informado nao e valido.
 */
public class TipoUsuarioInvalidoException extends BikeshareException {
    public TipoUsuarioInvalidoException() {
        super("Tipo invalido.");
    }
}
