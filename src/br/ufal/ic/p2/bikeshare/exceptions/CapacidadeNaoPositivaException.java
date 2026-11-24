package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a capacidade informada para uma estacao e menor ou igual a zero.
 */
public class CapacidadeNaoPositivaException extends BikeshareException {
    public CapacidadeNaoPositivaException() {
        super("Capacidade deve ser positiva.");
    }
}
