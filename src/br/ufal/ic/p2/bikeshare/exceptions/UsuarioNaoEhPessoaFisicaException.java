package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se consulta atributo exclusivo de pessoa fisica em uma pessoa juridica.
 */
public class UsuarioNaoEhPessoaFisicaException extends BikeshareException {
    public UsuarioNaoEhPessoaFisicaException() {
        super("Usuario nao eh pessoa fisica.");
    }
}
