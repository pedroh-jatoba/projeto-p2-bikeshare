package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a data informada e nula ou vazia.
 */
public class DataNulaException extends BikeshareException {
    public DataNulaException() {
        super("Data nao pode ser nula.");
    }
}
