package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o nome do usuario informado e nulo ou vazio.
 */
public class NomeNuloException extends BikeshareException {
    public NomeNuloException() {
        super("Nome nao pode ser nulo.");
    }
}
