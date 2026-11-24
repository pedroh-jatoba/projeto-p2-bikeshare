package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se tenta remover ou operar uma bicicleta que nao esta na estacao informada.
 */
public class BicicletaNaoEstaNestaEstacaoException extends BikeshareException {
    public BicicletaNaoEstaNestaEstacaoException() {
        super("Bicicleta nao esta nesta estacao.");
    }
}
