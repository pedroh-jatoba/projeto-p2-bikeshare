package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o nivel de bateria informado para uma bicicleta eletrica e nulo ou vazio.
 */
public class NivelBateriaNuloException extends BikeshareException {
    public NivelBateriaNuloException() {
        super("Nivel de bateria nao pode ser nulo.");
    }
}
