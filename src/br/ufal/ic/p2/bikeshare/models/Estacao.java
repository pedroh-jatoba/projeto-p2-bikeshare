package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.CapacidadeNaoNumericaException;
import br.ufal.ic.p2.bikeshare.exceptions.CapacidadeNaoPositivaException;
import br.ufal.ic.p2.bikeshare.exceptions.CapacidadeNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.EnderecoNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.EstacaoSemVagasDisponiveisException;
import br.ufal.ic.p2.bikeshare.exceptions.NomeNuloException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa uma estacao fisica de retirada e devolucao de bicicletas do sistema Bikeshare.
 */
public class Estacao implements Serializable {
    private static final long serialVersionUID = 2L;

    private String id;
    private String nome;
    private String endereco;
    private int capacidade;
    private Map<String, Bicicleta> bicicletas;

    /**
     * Cria uma estacao validando nome, endereco e capacidade inteira positiva.
     *
     * @param id            identificador unico da estacao (ex.: {@code E1})
     * @param nome          nome da estacao
     * @param endereco      endereco fisico da estacao
     * @param capacidadeStr representacao textual da capacidade maxima de vagas
     * @throws BikeshareException se nome, endereco ou capacidade forem nulos ou invalidos
     */
    public Estacao(String id, String nome, String endereco, String capacidadeStr) throws BikeshareException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeNuloException();
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new EnderecoNuloException();
        }
        if (capacidadeStr == null || capacidadeStr.trim().isEmpty()) {
            throw new CapacidadeNulaException();
        }

        int capacidadeParseada;
        try {
            capacidadeParseada = Integer.parseInt(capacidadeStr.trim());
        } catch (NumberFormatException e) {
            throw new CapacidadeNaoNumericaException();
        }
        if (capacidadeParseada <= 0) {
            throw new CapacidadeNaoPositivaException();
        }

        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.capacidade = capacidadeParseada;
        this.bicicletas = new HashMap<>();
    }

    /**
     * Estaciona uma bicicleta em uma vaga livre da estacao.
     *
     * @param bicicleta bicicleta a ser adicionada
     * @throws EstacaoSemVagasDisponiveisException se a estacao estiver sem vagas livres
     */
    public void adicionarBicicleta(Bicicleta bicicleta) throws EstacaoSemVagasDisponiveisException {
        if (getVagasDisponiveis() <= 0) {
            throw new EstacaoSemVagasDisponiveisException();
        }
        this.bicicletas.put(bicicleta.getId(), bicicleta);
    }

    /**
     * Verifica se a bicicleta com o identificador informado esta presente nesta estacao.
     *
     * @param bicicletaId identificador da bicicleta
     * @return {@code true} se a bicicleta estiver estacionada aqui; {@code false} caso contrario
     */
    public boolean contemBicicleta(String bicicletaId) {
        return this.bicicletas.containsKey(bicicletaId);
    }

    /**
     * Remove a bicicleta com o identificador informado das vagas desta estacao.
     *
     * @param bicicletaId identificador da bicicleta a remover
     */
    public void removerBicicleta(String bicicletaId) {
        this.bicicletas.remove(bicicletaId);
    }

    /**
     * Retorna a quantidade atual de vagas livres na estacao.
     *
     * @return numero de vagas disponiveis ({@code capacidade - bicicletas presentes})
     */
    public int getVagasDisponiveis() {
        return capacidade - bicicletas.size();
    }

    /**
     * Retorna a quantidade atual de bicicletas efetivamente disponiveis para retirada na estacao.
     *
     * @return numero de bicicletas aptas para retirada
     */
    public int getBicicletasDisponiveis() {
        int disponiveis = 0;
        for (Bicicleta b : bicicletas.values()) {
            if (b.estaDisponivelParaRetirada()) {
                disponiveis++;
            }
        }
        return disponiveis;
    }

    /** @return identificador unico da estacao */
    public String getId() { return id; }

    /** @return nome da estacao */
    public String getNome() { return nome; }

    /** @return endereco fisico da estacao */
    public String getEndereco() { return endereco; }

    /** @return capacidade total de vagas da estacao */
    public int getCapacidade() { return capacidade; }
}
