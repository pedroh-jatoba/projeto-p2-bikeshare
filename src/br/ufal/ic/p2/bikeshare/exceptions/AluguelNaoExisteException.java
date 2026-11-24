package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando nenhum aluguel e encontrado para o identificador informado.
 */
public class AluguelNaoExisteException extends BikeshareException {
    public AluguelNaoExisteException() {
        super("Aluguel nao existe.");
    }
}
