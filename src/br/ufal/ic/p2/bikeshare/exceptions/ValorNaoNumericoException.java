package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando um valor monetario informado nao e numerico.
 */
public class ValorNaoNumericoException extends BikeshareException {
    public ValorNaoNumericoException() {
        super("Valor deve ser numerico.");
    }
}
