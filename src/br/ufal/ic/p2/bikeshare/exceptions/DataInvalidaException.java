package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a data/hora informada nao possui formato ou calendario valido.
 */
public class DataInvalidaException extends BikeshareException {
    public DataInvalidaException() {
        super("Data invalida.");
    }
}
