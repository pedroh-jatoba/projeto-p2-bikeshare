package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaCartaoException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaPixException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaSaldoPreCarregadoException;

import java.io.Serializable;

/**
 * Classe base abstrata para os metodos de pagamento suportados pelo sistema Bikeshare.
 * Especializada em {@link Cartao}, {@link Pix} e {@link SaldoPreCarregado}.
 */
public abstract class MetodoPagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Retorna o identificador textual do tipo de metodo de pagamento
     * ({@code cartao}, {@code pix} ou {@code saldoPreCarregado}).
     *
     * @return tipo do metodo de pagamento
     */
    public abstract String getTipo();

    /**
     * Valida se o metodo de pagamento permite iniciar uma retirada de bicicleta.
     * Por padrao, metodos pos-pagos (como {@link Cartao} e {@link Pix}) sempre autorizam.
     *
     * @throws BikeshareException se o metodo nao autorizar a retirada
     */
    public void validarParaRetirada() throws BikeshareException {
        // Cartao e Pix autorizam a retirada sem saldo previo.
    }

    /**
     * Efetua a cobranca do valor de uma corrida neste metodo de pagamento.
     *
     * @param valor valor em reais a cobrar
     * @throws BikeshareException se nao for possivel quitar a cobranca (ex.: saldo insuficiente)
     */
    public void cobrar(double valor) throws BikeshareException {
        // Cartao e Pix aprovam a cobranca diretamente.
    }

    /**
     * Consulta um atributo especifico do metodo de pagamento.
     * Subclasses sobrescrevem este metodo para responder apenas aos seus proprios atributos.
     *
     * @param atributo nome do atributo consultado
     * @return valor textual do atributo
     * @throws BikeshareException se o atributo nao pertencer ao tipo concreto deste metodo
     */
    public String getAtributo(String atributo) throws BikeshareException {
        if ("numeroCartao".equals(atributo) || "validadeCartao".equals(atributo)) {
            throw new UsuarioNaoUsaCartaoException();
        }
        if ("chavePix".equals(atributo)) {
            throw new UsuarioNaoUsaPixException();
        }
        if ("saldo".equals(atributo)) {
            throw new UsuarioNaoUsaSaldoPreCarregadoException();
        }
        throw new AtributoNaoExisteException();
    }
}
