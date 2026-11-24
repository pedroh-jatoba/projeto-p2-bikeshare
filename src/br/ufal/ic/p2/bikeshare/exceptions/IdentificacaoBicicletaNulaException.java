package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o identificador da bicicleta informado e nulo ou vazio.
 */
public class IdentificacaoBicicletaNulaException extends BikeshareException {
    public IdentificacaoBicicletaNulaException() {
        super("Identificacao da bicicleta nao pode ser nula.");
    }
}
