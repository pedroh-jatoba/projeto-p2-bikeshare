package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando um valor monetario informado e nulo ou vazio.
 */
public class ValorNuloException extends BikeshareException {
    public ValorNuloException() {
        super("Valor nao pode ser nulo.");
    }
}
