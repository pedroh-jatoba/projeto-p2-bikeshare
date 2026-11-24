package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se tenta devolver um aluguel que ja foi finalizado, cancelado ou expirado.
 */
public class AluguelNaoEstaEmAndamentoException extends BikeshareException {
    public AluguelNaoEstaEmAndamentoException() {
        super("Aluguel nao esta em andamento.");
    }
}
