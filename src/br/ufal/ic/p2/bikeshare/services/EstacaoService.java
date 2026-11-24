package br.ufal.ic.p2.bikeshare.services;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.IdentificacaoEstacaoNulaException;
import br.ufal.ic.p2.bikeshare.models.Estacao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Servico responsavel pelo cadastro e consulta de disponibilidade de estacoes.
 */
public class EstacaoService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Estacao> estacoes;
    private int contadorEstacoes;

    /**
     * Inicializa o cadastro de estacoes vazio com contador de identificadores partindo de 1.
     */
    public EstacaoService() {
        this.estacoes = new HashMap<>();
        this.contadorEstacoes = 1;
    }

    /**
     * Cadastra uma nova estacao no sistema com identificador sequencial (ex.: {@code E1}).
     * Cadastros recusados por validacao nao consomem identificador.
     *
     * @param nome          nome da estacao
     * @param endereco      endereco fisico da estacao
     * @param capacidadeStr capacidade maxima de vagas (inteiro positivo)
     * @return identificador unico gerado para a estacao
     * @throws BikeshareException se nome, endereco ou capacidade forem nulos ou invalidos
     */
    public String criarEstacao(String nome, String endereco, String capacidadeStr) throws BikeshareException {
        String id = "E" + contadorEstacoes;
        Estacao novaEstacao = new Estacao(id, nome, endereco, capacidadeStr);
        estacoes.put(id, novaEstacao);
        contadorEstacoes++;
        return id;
    }

    /**
     * Consulta a quantidade de bicicletas disponiveis para retirada em uma estacao.
     *
     * @param estacaoId identificador da estacao
     * @return quantidade textual de bicicletas disponiveis
     * @throws BikeshareException se o identificador for nulo/vazio ou a estacao nao existir
     */
    public String getBicicletasDisponiveis(String estacaoId) throws BikeshareException {
        Estacao estacao = buscarEstacao(estacaoId);
        return String.valueOf(estacao.getBicicletasDisponiveis());
    }

    /**
     * Consulta a quantidade de vagas livres em uma estacao.
     *
     * @param estacaoId identificador da estacao
     * @return quantidade textual de vagas disponiveis
     * @throws BikeshareException se o identificador for nulo/vazio ou a estacao nao existir
     */
    public String getVagasDisponiveis(String estacaoId) throws BikeshareException {
        Estacao estacao = buscarEstacao(estacaoId);
        return String.valueOf(estacao.getVagasDisponiveis());
    }

    /**
     * Valida o identificador e retorna a estacao correspondente no cadastro.
     *
     * @param estacaoId identificador da estacao
     * @return instancia da estacao encontrada
     * @throws BikeshareException se o identificador for nulo/vazio ou a estacao nao existir
     */
    public Estacao buscarEstacao(String estacaoId) throws BikeshareException {
        if (estacaoId == null || estacaoId.trim().isEmpty()) {
            throw new IdentificacaoEstacaoNulaException();
        }
        Estacao estacao = estacoes.get(estacaoId);
        if (estacao == null) {
            throw new EstacaoNaoExisteException();
        }
        return estacao;
    }
}
