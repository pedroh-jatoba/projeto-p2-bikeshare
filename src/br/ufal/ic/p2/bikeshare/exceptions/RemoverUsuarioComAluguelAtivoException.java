package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se tenta remover um usuario que ainda possui um aluguel em andamento.
 */
public class RemoverUsuarioComAluguelAtivoException extends BikeshareException {
    public RemoverUsuarioComAluguelAtivoException() {
        super("Nao e possivel remover usuario com aluguel em andamento.");
    }
}
