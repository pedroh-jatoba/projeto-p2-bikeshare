package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando um usuario tenta retirar outra bicicleta ja tendo um aluguel em andamento.
 */
public class UsuarioJaPossuiAluguelEmAndamentoException extends BikeshareException {
    public UsuarioJaPossuiAluguelEmAndamentoException() {
        super("Usuario ja possui um aluguel em andamento.");
    }
}
