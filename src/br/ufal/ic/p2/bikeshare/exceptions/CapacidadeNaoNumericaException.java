package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a capacidade informada para uma estacao nao e um numero inteiro valido.
 */
public class CapacidadeNaoNumericaException extends BikeshareException {
    public CapacidadeNaoNumericaException() {
        super("Capacidade deve ser numerica.");
    }
}
