package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando um usuario sem metodo de pagamento cadastrado tenta retirar uma bicicleta.
 */
public class MetodoPagamentoNaoCadastradoException extends BikeshareException {
    public MetodoPagamentoNaoCadastradoException() {
        super("Metodo de pagamento nao cadastrado.");
    }
}
