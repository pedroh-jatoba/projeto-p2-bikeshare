package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o CNPJ informado para pessoa juridica e nulo ou vazio.
 */
public class CnpjNuloException extends BikeshareException {
    public CnpjNuloException() {
        super("CNPJ nao pode ser nulo.");
    }
}
