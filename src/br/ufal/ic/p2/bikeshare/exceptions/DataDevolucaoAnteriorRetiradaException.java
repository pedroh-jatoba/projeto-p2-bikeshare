package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a data de devolucao informada e anterior a data de retirada do aluguel.
 */
public class DataDevolucaoAnteriorRetiradaException extends BikeshareException {
    public DataDevolucaoAnteriorRetiradaException() {
        super("Data de devolucao anterior a data de retirada.");
    }
}
