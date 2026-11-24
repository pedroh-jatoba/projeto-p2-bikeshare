package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Excecao base para todos os erros de regra de negocio do sistema Bikeshare.
 */
public class BikeshareException extends Exception {

    /**
     * Cria uma nova excecao do sistema com a mensagem informada.
     *
     * @param message descricao do erro ocorrido
     */
    public BikeshareException(String message) {
        super(message);
    }
}
