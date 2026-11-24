package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se consulta atributo exclusivo de pessoa juridica em uma pessoa fisica.
 */
public class UsuarioNaoEhPessoaJuridicaException extends BikeshareException {
    public UsuarioNaoEhPessoaJuridicaException() {
        super("Usuario nao eh pessoa juridica.");
    }
}
