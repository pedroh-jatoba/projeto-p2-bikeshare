package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando nenhuma bicicleta e encontrada para o identificador informado.
 */
public class BicicletaNaoExisteException extends BikeshareException {
    public BicicletaNaoExisteException() {
        super("Bicicleta nao existe.");
    }
}
