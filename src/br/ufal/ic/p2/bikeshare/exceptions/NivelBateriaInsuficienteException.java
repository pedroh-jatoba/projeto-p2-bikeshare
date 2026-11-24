package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se tenta retirar uma bicicleta eletrica com bateria abaixo do minimo permitido.
 */
public class NivelBateriaInsuficienteException extends BikeshareException {
    public NivelBateriaInsuficienteException() {
        super("Nivel de bateria insuficiente.");
    }
}
