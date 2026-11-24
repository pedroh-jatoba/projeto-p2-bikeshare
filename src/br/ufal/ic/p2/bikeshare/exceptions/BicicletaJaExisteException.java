package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando ja existe uma bicicleta cadastrada com o identificador informado.
 */
public class BicicletaJaExisteException extends BikeshareException {
    public BicicletaJaExisteException() {
        super("Bicicleta ja existe.");
    }
}
