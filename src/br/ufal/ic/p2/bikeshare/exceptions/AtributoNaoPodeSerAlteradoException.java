package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se tenta alterar um atributo inexistente ou controlado pelo sistema.
 */
public class AtributoNaoPodeSerAlteradoException extends BikeshareException {
    public AtributoNaoPodeSerAlteradoException() {
        super("Atributo nao pode ser alterado.");
    }
}
