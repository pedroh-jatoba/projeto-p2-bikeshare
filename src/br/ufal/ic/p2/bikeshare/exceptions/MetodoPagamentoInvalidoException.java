package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando o tipo ou combinacao de parametros do metodo de pagamento e invalido.
 */
public class MetodoPagamentoInvalidoException extends BikeshareException {
    public MetodoPagamentoInvalidoException() {
        super("Valor de metodo de pagamento invalido.");
    }
}
