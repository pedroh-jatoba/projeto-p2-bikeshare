package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.MensalidadeNaoNumericaException;
import br.ufal.ic.p2.bikeshare.exceptions.MensalidadeNegativaException;
import br.ufal.ic.p2.bikeshare.exceptions.MensalidadeNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.PlanoInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.PlanoSemMensalidadeException;

import java.io.Serializable;
import java.util.Locale;

/**
 * Representa o plano de tarifacao contratado por um usuario ({@code avulso} ou {@code assinante}).
 */
public class Plano implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Mensalidade padrao aplicada quando um usuario migra para o plano assinante. */
    public static final String MENSALIDADE_PADRAO_ASSINANTE = "30.00";

    /** Valor por minuto cobrado no plano avulso. */
    public static final double VALOR_MINUTO_AVULSO = 0.50;

    /** Franquia gratuita em minutos para o plano assinante. */
    public static final long FRANQUIA_MINUTOS_ASSINANTE = 60L;

    /** Valor por minuto excedente cobrado no plano assinante. */
    public static final double VALOR_MINUTO_EXCEDENTE_ASSINANTE = 0.30;

    private String tipo;
    private Double mensalidade;

    /**
     * Cria um plano validando seu tipo e a compatibilidade com o valor de mensalidade.
     *
     * @param tipo           tipo do plano ({@code avulso} ou {@code assinante})
     * @param mensalidadeStr valor textual da mensalidade (deve ser nulo para {@code avulso})
     * @throws BikeshareException se o tipo ou a mensalidade forem invalidos
     */
    public Plano(String tipo, String mensalidadeStr) throws BikeshareException {
        if (tipo == null || (!tipo.equals("avulso") && !tipo.equals("assinante"))) {
            throw new PlanoInvalidoException();
        }

        this.tipo = tipo;

        if (tipo.equals("avulso")) {
            if (mensalidadeStr != null && !mensalidadeStr.trim().isEmpty()) {
                throw new PlanoSemMensalidadeException();
            }
            this.mensalidade = null;
        } else { // assinante
            if (mensalidadeStr == null || mensalidadeStr.trim().isEmpty()) {
                throw new MensalidadeNulaException();
            }
            double valor;
            try {
                valor = Double.parseDouble(mensalidadeStr.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                throw new MensalidadeNaoNumericaException();
            }
            if (valor < 0) {
                throw new MensalidadeNegativaException();
            }
            this.mensalidade = valor;
        }
    }

    /**
     * Calcula a tarifa base da corrida de acordo com o tipo de plano e a duracao em minutos.
     *
     * @param minutos duracao total da corrida em minutos
     * @return tarifa base em reais (sem adicionais da bicicleta)
     */
    public double calcularTarifa(long minutos) {
        if ("assinante".equals(this.tipo)) {
            long excedente = Math.max(0L, minutos - FRANQUIA_MINUTOS_ASSINANTE);
            return excedente * VALOR_MINUTO_EXCEDENTE_ASSINANTE;
        }
        return minutos * VALOR_MINUTO_AVULSO;
    }

    /** @return tipo do plano ({@code avulso} ou {@code assinante}) */
    public String getTipo() {
        return tipo;
    }

    /** @return valor numerico da mensalidade, ou {@code null} se nao houver */
    public Double getMensalidade() {
        return mensalidade;
    }

    /**
     * Retorna a mensalidade formatada no padrao brasileiro (ex.: {@code 30,00}).
     *
     * @return mensalidade formatada, ou {@code null} se o plano nao tiver mensalidade
     */
    public String getMensalidadeFormatada() {
        if (mensalidade == null) {
            return null;
        }
        return String.format(new Locale("pt", "BR"), "%.2f", mensalidade);
    }
}
