package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o nivel de bateria informado nao e um numero inteiro valido.
 */
public class NivelBateriaNaoNumericoException extends BikeshareException {
    public NivelBateriaNaoNumericoException() {
        super("Nivel de bateria deve ser numerico.");
    }
}
