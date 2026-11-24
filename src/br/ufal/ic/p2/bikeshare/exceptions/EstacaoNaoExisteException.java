package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando nenhuma estacao e encontrada para o identificador informado.
 */
public class EstacaoNaoExisteException extends BikeshareException {
    public EstacaoNaoExisteException() {
        super("Estacao nao existe.");
    }
}
