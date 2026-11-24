package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a capacidade informada para uma estacao e nula ou vazia.
 */
public class CapacidadeNulaException extends BikeshareException {
    public CapacidadeNulaException() {
        super("Capacidade nao pode ser nula.");
    }
}
