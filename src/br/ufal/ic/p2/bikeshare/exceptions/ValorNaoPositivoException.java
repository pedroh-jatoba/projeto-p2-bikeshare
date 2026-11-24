package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando um valor monetario informado e menor ou igual a zero.
 */
public class ValorNaoPositivoException extends BikeshareException {
    public ValorNaoPositivoException() {
        super("Valor deve ser positivo.");
    }
}
