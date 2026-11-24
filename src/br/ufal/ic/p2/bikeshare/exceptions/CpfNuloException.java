package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o CPF informado para pessoa fisica e nulo ou vazio.
 */
public class CpfNuloException extends BikeshareException {
    public CpfNuloException() {
        super("CPF nao pode ser nulo.");
    }
}
