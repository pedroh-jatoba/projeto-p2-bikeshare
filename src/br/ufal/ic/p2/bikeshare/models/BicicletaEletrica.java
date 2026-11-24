package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.NivelBateriaForaDoIntervaloException;
import br.ufal.ic.p2.bikeshare.exceptions.NivelBateriaInsuficienteException;
import br.ufal.ic.p2.bikeshare.exceptions.NivelBateriaNaoNumericoException;
import br.ufal.ic.p2.bikeshare.exceptions.NivelBateriaNuloException;

/**
 * Representa uma bicicleta eletrica, que possui nivel de bateria (0 a 100)
 * e exige bateria minima para contar como disponivel para retirada.
 */
public class BicicletaEletrica extends Bicicleta {
    private static final long serialVersionUID = 1L;

    /** Percentual minimo de bateria necessario para a bicicleta eletrica ficar disponivel para retirada. */
    public static final int NIVEL_MINIMO_BATERIA = 15;

    /** Cobranca adicional por minuto de uso de bicicleta eletrica. */
    public static final double ADICIONAL_POR_MINUTO = 0.20;

    private int nivelBateria;

    /**
     * Cria uma bicicleta eletrica validando seu nivel de bateria inteiro entre 0 e 100.
     *
     * @param id              identificador unico da bicicleta
     * @param estacaoId       identificador da estacao inicial
     * @param nivelBateriaStr representacao textual do nivel de bateria
     * @throws BikeshareException se o nivel de bateria for nulo, nao inteiro ou fora de 0..100
     */
    public BicicletaEletrica(String id, String estacaoId, String nivelBateriaStr) throws BikeshareException {
        super(id, estacaoId);
        if (nivelBateriaStr == null || nivelBateriaStr.trim().isEmpty()) {
            throw new NivelBateriaNuloException();
        }
        int nivel;
        try {
            nivel = Integer.parseInt(nivelBateriaStr.trim());
        } catch (NumberFormatException e) {
            throw new NivelBateriaNaoNumericoException();
        }
        if (nivel < 0 || nivel > 100) {
            throw new NivelBateriaForaDoIntervaloException();
        }
        this.nivelBateria = nivel;
    }

    @Override
    public String getTipo() {
        return "eletrica";
    }

    @Override
    public boolean estaDisponivelParaRetirada() {
        return super.estaDisponivelParaRetirada() && this.nivelBateria >= NIVEL_MINIMO_BATERIA;
    }

    @Override
    public void validarParaRetirada() throws BikeshareException {
        if (this.nivelBateria < NIVEL_MINIMO_BATERIA) {
            throw new NivelBateriaInsuficienteException();
        }
    }

    @Override
    public double calcularAdicional(long minutos) {
        return minutos * ADICIONAL_POR_MINUTO;
    }

    @Override
    public String getAtributo(String atributo) throws BikeshareException {
        if ("nivelBateria".equals(atributo)) {
            return String.valueOf(this.nivelBateria);
        }
        return super.getAtributo(atributo);
    }

    /** @return nivel percentual atual de bateria (0 a 100) */
    public int getNivelBateria() { return nivelBateria; }

    /** @param nivelBateria novo nivel percentual de bateria */
    public void setNivelBateria(int nivelBateria) { this.nivelBateria = nivelBateria; }
}
