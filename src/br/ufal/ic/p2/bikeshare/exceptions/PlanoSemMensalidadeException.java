package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se consulta ou informa mensalidade para um plano que nao possui mensalidade.
 */
public class PlanoSemMensalidadeException extends BikeshareException {
    public PlanoSemMensalidadeException() {
        super("Plano nao possui mensalidade.");
    }
}
