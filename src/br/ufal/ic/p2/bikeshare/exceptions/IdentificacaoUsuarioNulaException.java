package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o identificador do usuario informado e nulo ou vazio.
 */
public class IdentificacaoUsuarioNulaException extends BikeshareException {
    public IdentificacaoUsuarioNulaException() {
        super("Identificacao do usuario nao pode ser nula.");
    }
}
