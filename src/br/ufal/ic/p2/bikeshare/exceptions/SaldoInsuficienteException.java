package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o saldo pre-carregado do usuario e insuficiente para retirar ou quitar a devolucao.
 */
public class SaldoInsuficienteException extends BikeshareException {
    public SaldoInsuficienteException() {
        super("Saldo insuficiente.");
    }
}
