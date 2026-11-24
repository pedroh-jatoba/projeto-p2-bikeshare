package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando a mensalidade do plano assinante e nula ou vazia.
 */
public class MensalidadeNulaException extends BikeshareException {
    public MensalidadeNulaException() {
        super("Mensalidade nao pode ser nula.");
    }
}
