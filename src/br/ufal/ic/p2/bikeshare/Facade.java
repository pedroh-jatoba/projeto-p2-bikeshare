package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.services.SistemaBikeshare;

import java.io.*;

/**
 * Facade - Ponto unico de entrada do sistema BIKESHARE.
 */
public class Facade {

    private static final String AINDA_NAO_IMPLEMENTADO = "Ainda não implementado.";
    private static final String ARQUIVO_DADOS = "bikeshare.ser";

    private SistemaBikeshare sistema;

    /**
     * Inicializa a Facade carregando o estado persistido do sistema, se existir.
     */
    public Facade() {
        this.sistema = carregarSistema();
    }

    // =====================================================================
    // US1 - Cadastro de usuario
    // =====================================================================

    /**
     * Cria um usuario pessoa fisica com plano padrao ({@code avulso}).
     *
     * @param nome      nome do usuario
     * @param email     e-mail unico do usuario
     * @param senha     senha de acesso
     * @param tipo      tipo do usuario ({@code pessoaFisica})
     * @param cpfOuCnpj CPF do usuario
     * @return identificador unico gerado
     * @throws BikeshareException em caso de dados invalidos ou e-mail duplicado
     */
    public String criarUsuario(String nome, String email, String senha, String tipo, String cpfOuCnpj) throws BikeshareException {
        return sistema.getUsuarioService().criarUsuario(nome, email, senha, tipo, cpfOuCnpj);
    }

    /**
     * Cria um usuario pessoa juridica com plano padrao ({@code avulso}).
     *
     * @param nome        nome de exibicao
     * @param email       e-mail unico do usuario
     * @param senha       senha de acesso
     * @param tipo        tipo do usuario ({@code pessoaJuridica})
     * @param cnpj        CNPJ da empresa
     * @param razaoSocial razao social da empresa
     * @return identificador unico gerado
     * @throws BikeshareException em caso de dados invalidos ou e-mail duplicado
     */
    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial) throws BikeshareException {
        return sistema.getUsuarioService().criarUsuario(nome, email, senha, tipo, cnpj, razaoSocial);
    }

    /**
     * Cria um usuario pessoa fisica com plano e mensalidade informados.
     *
     * @param nome        nome do usuario
     * @param email       e-mail unico do usuario
     * @param senha       senha de acesso
     * @param tipo        tipo do usuario ({@code pessoaFisica})
     * @param cpf         CPF do usuario
     * @param plano       tipo do plano
     * @param mensalidade valor da mensalidade
     * @return identificador unico gerado
     * @throws BikeshareException em caso de dados invalidos ou e-mail duplicado
     */
    public String criarUsuario(String nome, String email, String senha, String tipo, String cpf,
                               String plano, String mensalidade) throws BikeshareException {
        return sistema.getUsuarioService().criarUsuario(nome, email, senha, tipo, cpf, plano, mensalidade);
    }

    /**
     * Cria um usuario pessoa juridica com plano e mensalidade informados.
     *
     * @param nome        nome de exibicao
     * @param email       e-mail unico do usuario
     * @param senha       senha de acesso
     * @param tipo        tipo do usuario ({@code pessoaJuridica})
     * @param cnpj        CNPJ da empresa
     * @param razaoSocial razao social da empresa
     * @param plano       tipo do plano
     * @param mensalidade valor da mensalidade
     * @return identificador unico gerado
     * @throws BikeshareException em caso de dados invalidos ou e-mail duplicado
     */
    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj,
                               String razaoSocial, String plano, String mensalidade) throws BikeshareException {
        return sistema.getUsuarioService().criarUsuario(nome, email, senha, tipo, cnpj, razaoSocial, plano, mensalidade);
    }

    // =====================================================================
    // US2 - Consulta de usuario
    // =====================================================================

    /**
     * Consulta o valor de um atributo de um usuario cadastrado.
     *
     * @param usuario  identificador unico do usuario
     * @param atributo nome do atributo desejado
     * @return valor textual do atributo
     * @throws BikeshareException se o usuario ou o atributo nao existir
     */
    public String getAtributoUsuario(String usuario, String atributo) throws BikeshareException {
        return sistema.getUsuarioService().getAtributoUsuario(usuario, atributo);
    }

    /**
     * Busca o identificador de um usuario a partir do seu e-mail.
     *
     * @param email e-mail do usuario
     * @return identificador unico do usuario
     * @throws BikeshareException se o e-mail for nulo ou nao corresponder a nenhum usuario
     */
    public String getUsuarioPorEmail(String email) throws BikeshareException {
        return sistema.getUsuarioService().getUsuarioPorEmail(email);
    }

    // =====================================================================
    // US3 - Alteracao de usuario (inclui metodo de pagamento)
    // =====================================================================

    /**
     * Altera um atributo simples de um usuario cadastrado ou ativa {@code saldoPreCarregado}.
     *
     * @param usuario  identificador do usuario
     * @param atributo nome do atributo a alterar
     * @param valor    novo valor do atributo
     * @throws BikeshareException em caso de usuario inexistente, atributo nao alteravel ou valor invalido
     */
    public void alterarUsuario(String usuario, String atributo, String valor) throws BikeshareException {
        sistema.getUsuarioService().alterarUsuario(usuario, atributo, valor);
    }

    /**
     * Configura o metodo de pagamento Pix para um usuario cadastrado.
     *
     * @param usuario  identificador do usuario
     * @param atributo deve ser {@code metodoPagamento}
     * @param valor1   deve ser {@code pix}
     * @param chave    chave Pix do usuario
     * @throws BikeshareException em caso de usuario inexistente, parametros invalidos ou chave nula
     */
    public void alterarUsuario(String usuario, String atributo, String valor1, String chave) throws BikeshareException {
        sistema.getUsuarioService().alterarUsuario(usuario, atributo, valor1, chave);
    }

    /**
     * Configura o metodo de pagamento por cartao para um usuario cadastrado.
     *
     * @param usuario  identificador do usuario
     * @param atributo deve ser {@code metodoPagamento}
     * @param valor1   deve ser {@code cartao}
     * @param numero   numero do cartao
     * @param validade data de validade no formato {@code MM/AAAA}
     * @throws BikeshareException em caso de usuario inexistente, parametros invalidos ou dados do cartao invalidos
     */
    public void alterarUsuario(String usuario, String atributo, String valor1, String numero, String validade) throws BikeshareException {
        sistema.getUsuarioService().alterarUsuario(usuario, atributo, valor1, numero, validade);
    }

    // =====================================================================
    // US4 - Remocao de usuario
    // =====================================================================

    /**
     * Remove um usuario cadastrado do sistema.
     *
     * @param usuario identificador unico do usuario a remover
     * @throws BikeshareException se o identificador for nulo/vazio ou o usuario nao existir
     */
    public void removerUsuario(String usuario) throws BikeshareException {
        sistema.getUsuarioService().removerUsuario(usuario);
    }

    // =====================================================================
    // US5 - Cadastro de estacao
    // =====================================================================

    /**
     * Cadastra uma nova estacao no sistema.
     *
     * @param nome       nome da estacao
     * @param endereco   endereco fisico da estacao
     * @param capacidade capacidade maxima de vagas (inteiro positivo)
     * @return identificador unico gerado para a estacao
     * @throws BikeshareException se nome, endereco ou capacidade forem nulos ou invalidos
     */
    public String criarEstacao(String nome, String endereco, String capacidade) throws BikeshareException {
        return sistema.getEstacaoService().criarEstacao(nome, endereco, capacidade);
    }

    // =====================================================================
    // US6 - Gestao de bicicletas na estacao
    // =====================================================================

    /**
     * Adiciona uma bicicleta convencional a uma estacao.
     *
     * @param estacao   identificador da estacao
     * @param bicicleta identificador unico da bicicleta
     * @param tipo      tipo da bicicleta ({@code convencional})
     * @throws BikeshareException se os dados forem invalidos ou a estacao estiver cheia
     */
    public void adicionarBicicleta(String estacao, String bicicleta, String tipo) throws BikeshareException {
        sistema.getBicicletaService().adicionarBicicleta(estacao, bicicleta, tipo);
    }

    /**
     * Adiciona uma bicicleta eletrica com nivel de bateria a uma estacao.
     *
     * @param estacao      identificador da estacao
     * @param bicicleta    identificador unico da bicicleta
     * @param tipo         tipo da bicicleta ({@code eletrica})
     * @param nivelBateria nivel inicial de bateria (0 a 100)
     * @throws BikeshareException se os dados forem invalidos ou a estacao estiver cheia
     */
    public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria) throws BikeshareException {
        sistema.getBicicletaService().adicionarBicicleta(estacao, bicicleta, tipo, nivelBateria);
    }

    /**
     * Remove uma bicicleta de uma estacao.
     *
     * @param estacao   identificador da estacao
     * @param bicicleta identificador da bicicleta
     * @throws BikeshareException se a estacao ou bicicleta nao existir, ou se nao estiver na estacao
     */
    public void removerBicicleta(String estacao, String bicicleta) throws BikeshareException {
        sistema.getBicicletaService().removerBicicleta(estacao, bicicleta);
    }

    /**
     * Consulta o valor de um atributo de uma bicicleta cadastrada.
     *
     * @param bicicleta identificador da bicicleta
     * @param atributo  nome do atributo desejado
     * @return valor textual do atributo
     * @throws BikeshareException se a bicicleta ou o atributo nao existir
     */
    public String getAtributoBicicleta(String bicicleta, String atributo) throws BikeshareException {
        return sistema.getBicicletaService().getAtributoBicicleta(bicicleta, atributo);
    }

    // =====================================================================
    // US7 - Consulta de disponibilidade
    // =====================================================================

    /**
     * Consulta a quantidade de bicicletas disponiveis para retirada em uma estacao.
     *
     * @param estacao identificador unico da estacao
     * @return quantidade textual de bicicletas disponiveis
     * @throws BikeshareException se o identificador for nulo ou a estacao nao existir
     */
    public String getBicicletasDisponiveis(String estacao) throws BikeshareException {
        return sistema.getEstacaoService().getBicicletasDisponiveis(estacao);
    }

    /**
     * Consulta a quantidade de vagas livres em uma estacao.
     *
     * @param estacao identificador unico da estacao
     * @return quantidade textual de vagas disponiveis
     * @throws BikeshareException se o identificador for nulo ou a estacao nao existir
     */
    public String getVagasDisponiveis(String estacao) throws BikeshareException {
        return sistema.getEstacaoService().getVagasDisponiveis(estacao);
    }

    /**
     * Consulta o estado operacional atual de uma bicicleta.
     *
     * @param bicicleta identificador unico da bicicleta
     * @return estado atual da bicicleta
     * @throws BikeshareException se o identificador for nulo ou a bicicleta nao existir
     */
    public String getEstadoBicicleta(String bicicleta) throws BikeshareException {
        return sistema.getBicicletaService().getEstadoBicicleta(bicicleta);
    }

    // =====================================================================
    // US8 - Retirada de bicicleta
    // =====================================================================

    /**
     * Inicia um aluguel retirando uma bicicleta disponivel de uma estacao.
     *
     * @param usuario   identificador do usuario
     * @param estacao   identificador da estacao de retirada
     * @param bicicleta identificador da bicicleta
     * @param data      data e hora de retirada ({@code d/M/uuuu H:mm})
     * @return identificador unico gerado para o aluguel
     * @throws BikeshareException se alguma regra de retirada for violada
     */
    public String retirarBicicleta(String usuario, String estacao, String bicicleta, String data) throws BikeshareException {
        return sistema.getAluguelService().retirarBicicleta(usuario, estacao, bicicleta, data);
    }

    // =====================================================================
    // US9 - Devolucao de bicicleta
    // =====================================================================

    /**
     * Realiza a devolucao de uma bicicleta encerrando um aluguel em andamento.
     *
     * @param aluguel identificador unico do aluguel
     * @param estacao identificador da estacao de devolucao
     * @param data    data e hora de devolucao ({@code d/M/uuuu H:mm})
     * @throws BikeshareException se o aluguel/estacao/data for invalido, a estacao estiver cheia ou faltar saldo
     */
    public void devolverBicicleta(String aluguel, String estacao, String data) throws BikeshareException {
        sistema.getAluguelService().devolverBicicleta(aluguel, estacao, data);
    }

    // =====================================================================
    // US10 - Consulta de historico de alugueis
    // =====================================================================

    public String getHistoricoAlugueis(String usuario) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getHistoricoAlugueis(String usuario, String atributo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    /**
     * Consulta o valor de um atributo de um aluguel registrado.
     *
     * @param aluguel  identificador unico do aluguel
     * @param atributo nome do atributo desejado
     * @return valor textual do atributo
     * @throws BikeshareException se o aluguel ou atributo nao existir, ou estiver em andamento
     */
    public String getAtributoAluguel(String aluguel, String atributo) throws BikeshareException {
        return sistema.getAluguelService().getAtributoAluguel(aluguel, atributo);
    }

    // =====================================================================
    // US11/US12 - Planos e tarifacao / criacao de novos planos
    // =====================================================================

    public String criarPlano(String nome, String franquiaMinutos, String valorMinutoExcedente, String mensalidade) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // US13 - Regras de bloqueio / lancamento manual de penalidade
    // =====================================================================

    public void lancaPenalidade(String usuario, String tipo, String valor, String motivo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // US14 - Lancamento e conclusao de manutencao
    // =====================================================================

    public void lancaDefeito(String bicicleta, String descricao) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void concluiManutencao(String bicicleta) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // US15 - Relatorios
    // =====================================================================

    public String getTotalFaturado(String dataInicial, String dataFinal) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // US16 - Undo/redo
    // =====================================================================

    public void undo() {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void redo() {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // =====================================================================
    // Ciclo de vida do sistema (Persistencia e Reset)
    // =====================================================================

    /**
     * Reinicia o sistema em memoria e remove o arquivo de persistencia em disco.
     */
    public void zerarSistema() {
        this.sistema = new SistemaBikeshare();
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    /**
     * Persiste o estado atual do sistema em disco via serializacao.
     */
    public void encerrarSistema() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(this.sistema);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados do sistema.", e);
        }
    }

    /**
     * Carrega o estado salvo do sistema a partir do disco ou cria uma instancia nova.
     *
     * @return instancia carregada ou nova de {@link SistemaBikeshare}
     */
    private SistemaBikeshare carregarSistema() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return new SistemaBikeshare();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (SistemaBikeshare) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new SistemaBikeshare();
        }
    }
}
