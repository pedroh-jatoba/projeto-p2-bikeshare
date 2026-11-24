package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o valor informado para mensalidade e negativo.
 */
public class MensalidadeNegativaException extends BikeshareException {
    public MensalidadeNegativaException() {
        super("Mensalidade deve ser nao-negativa.");
    }
}
