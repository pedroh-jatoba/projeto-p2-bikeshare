package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o identificador do aluguel informado e nulo ou vazio.
 */
public class IdentificacaoAluguelNulaException extends BikeshareException {
    public IdentificacaoAluguelNulaException() {
        super("Identificacao do aluguel nao pode ser nula.");
    }
}
