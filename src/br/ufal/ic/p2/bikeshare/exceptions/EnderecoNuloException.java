package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o endereco informado para uma estacao e nulo ou vazio.
 */
public class EnderecoNuloException extends BikeshareException {
    public EnderecoNuloException() {
        super("Endereco nao pode ser nulo.");
    }
}
