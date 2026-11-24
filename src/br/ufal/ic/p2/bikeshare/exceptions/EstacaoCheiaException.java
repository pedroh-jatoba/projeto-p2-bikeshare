package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se tenta devolver uma bicicleta em uma estacao sem vagas disponiveis.
 */
public class EstacaoCheiaException extends BikeshareException {
    public EstacaoCheiaException() {
        super("Estacao cheia.");
    }
}
