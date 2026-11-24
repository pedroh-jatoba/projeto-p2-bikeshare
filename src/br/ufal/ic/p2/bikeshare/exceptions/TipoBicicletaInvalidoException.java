package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o tipo de bicicleta informado nao e valido ({@code convencional} ou {@code eletrica}).
 */
public class TipoBicicletaInvalidoException extends BikeshareException {
    public TipoBicicletaInvalidoException() {
        super("Tipo invalido.");
    }
}
