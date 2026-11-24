package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o identificador da estacao informado e nulo ou vazio.
 */
public class IdentificacaoEstacaoNulaException extends BikeshareException {
    public IdentificacaoEstacaoNulaException() {
        super("Identificacao da estacao nao pode ser nula.");
    }
}
