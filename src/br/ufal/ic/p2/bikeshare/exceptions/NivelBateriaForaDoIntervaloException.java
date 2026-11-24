package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o nivel de bateria informado esta fora do intervalo permitido (0 a 100).
 */
public class NivelBateriaForaDoIntervaloException extends BikeshareException {
    public NivelBateriaForaDoIntervaloException() {
        super("Nivel de bateria deve estar entre 0 e 100.");
    }
}
