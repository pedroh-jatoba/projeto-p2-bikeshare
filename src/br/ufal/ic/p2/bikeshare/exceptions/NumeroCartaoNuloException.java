package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o numero do cartao informado e nulo ou vazio.
 */
public class NumeroCartaoNuloException extends BikeshareException {
    public NumeroCartaoNuloException() {
        super("Numero do cartao nao pode ser nulo.");
    }
}
