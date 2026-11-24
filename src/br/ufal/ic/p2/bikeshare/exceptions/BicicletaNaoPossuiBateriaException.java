package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se consulta ou informa nivel de bateria para uma bicicleta convencional.
 */
public class BicicletaNaoPossuiBateriaException extends BikeshareException {
    public BicicletaNaoPossuiBateriaException() {
        super("Bicicleta nao possui bateria.");
    }
}
