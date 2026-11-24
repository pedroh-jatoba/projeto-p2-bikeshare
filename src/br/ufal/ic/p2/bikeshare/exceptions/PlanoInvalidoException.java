package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o tipo de plano informado nao e valido.
 */
public class PlanoInvalidoException extends BikeshareException {
    public PlanoInvalidoException() {
        super("Plano invalido.");
    }
}
