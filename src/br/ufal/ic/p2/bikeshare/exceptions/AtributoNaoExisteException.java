package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o atributo solicitado na consulta nao existe.
 */
public class AtributoNaoExisteException extends BikeshareException {
    public AtributoNaoExisteException() {
        super("Atributo nao existe.");
    }
}
