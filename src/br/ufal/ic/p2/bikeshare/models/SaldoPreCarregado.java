package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.SaldoInsuficienteException;
import br.ufal.ic.p2.bikeshare.exceptions.ValorNaoNumericoException;
import br.ufal.ic.p2.bikeshare.exceptions.ValorNaoPositivoException;
import br.ufal.ic.p2.bikeshare.exceptions.ValorNuloException;

import java.util.Locale;

/**
 * Metodo de pagamento por saldo pre-carregado com recargas acumulativas.
 */
public class SaldoPreCarregado extends MetodoPagamento {
    private static final long serialVersionUID = 1L;

    private double saldo;

    /**
     * Cria uma carteira de saldo pre-carregado com saldo inicial zerado.
     */
    public SaldoPreCarregado() {
        this.saldo = 0.0;
    }

    /**
     * Valida e soma uma recarga positiva ao saldo atual.
     *
     * @param valorStr representacao textual do valor a recarregar (aceita ponto ou virgula)
     * @throws BikeshareException se o valor for nulo, nao numerico ou nao positivo
     */
    public void adicionarSaldo(String valorStr) throws BikeshareException {
        if (valorStr == null || valorStr.trim().isEmpty()) {
            throw new ValorNuloException();
        }
        double valor;
        try {
            valor = Double.parseDouble(valorStr.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new ValorNaoNumericoException();
        }
        if (valor <= 0) {
            throw new ValorNaoPositivoException();
        }
        this.saldo += valor;
    }

    @Override
    public String getTipo() {
        return "saldoPreCarregado";
    }

    @Override
    public void validarParaRetirada() throws BikeshareException {
        if (this.saldo <= 0.0) {
            throw new SaldoInsuficienteException();
        }
    }

    @Override
    public void cobrar(double valor) throws BikeshareException {
        if (valor <= 0.0) {
            return;
        }
        if (this.saldo + 1e-9 < valor) {
            throw new SaldoInsuficienteException();
        }
        this.saldo = Math.max(0.0, this.saldo - valor);
    }

    @Override
    public String getAtributo(String atributo) throws BikeshareException {
        if ("saldo".equals(atributo)) {
            return String.format(new Locale("pt", "BR"), "%.2f", this.saldo);
        }
        return super.getAtributo(atributo);
    }

    /** @return saldo numerico disponivel */
    public double getSaldo() { return saldo; }
}
