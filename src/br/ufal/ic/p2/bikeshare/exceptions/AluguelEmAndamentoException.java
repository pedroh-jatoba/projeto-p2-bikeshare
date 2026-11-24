package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se consulta um atributo de encerramento de um aluguel ainda em andamento.
 */
public class AluguelEmAndamentoException extends BikeshareException {
    public AluguelEmAndamentoException() {
        super("Aluguel ainda esta em andamento.");
    }
}
