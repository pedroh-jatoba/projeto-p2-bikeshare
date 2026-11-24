package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o valor informado para mensalidade nao e numerico.
 */
public class MensalidadeNaoNumericaException extends BikeshareException {
    public MensalidadeNaoNumericaException() {
        super("Mensalidade deve ser numerica.");
    }
}
