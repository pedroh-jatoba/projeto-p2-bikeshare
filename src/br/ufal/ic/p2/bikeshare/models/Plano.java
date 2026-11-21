package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import java.io.Serializable;
import java.util.Locale;

public class Plano implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tipo;
    private Double mensalidade;

    public Plano(String tipo, String mensalidadeStr) throws BikeshareException {
        if (tipo == null || (!tipo.equals("avulso") && !tipo.equals("assinante"))) {
            throw new BikeshareException("Plano invalido.");
        }

        this.tipo = tipo;

        if (tipo.equals("avulso")) {
            if (mensalidadeStr != null && !mensalidadeStr.trim().isEmpty()) {
                throw new BikeshareException("Plano nao possui mensalidade.");
            }
            this.mensalidade = null;
        } else { // assinante
            if (mensalidadeStr == null || mensalidadeStr.trim().isEmpty()) {
                throw new BikeshareException("Mensalidade nao pode ser nula.");
            }
            double valor;
            try {
                valor = Double.parseDouble(mensalidadeStr.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                throw new BikeshareException("Mensalidade deve ser numerica.");
            }
            if (valor < 0) {
                throw new BikeshareException("Mensalidade deve ser nao-negativa.");
            }
            this.mensalidade = valor;
        }
    }

    public String getTipo() {
        return tipo;
    }

    public Double getMensalidade() {
        return mensalidade;
    }

    public String getMensalidadeFormatada() {
        if (mensalidade == null) {
            return null;
        }
        return String.format(new Locale("pt", "BR"), "%.2f", mensalidade);
    }
}
