package br.ufal.ic.p2.bikeshare.services;

import br.ufal.ic.p2.bikeshare.exceptions.AtributoNaoPodeSerAlteradoException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaJaExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoEstaNestaEstacaoException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoPossuiBateriaException;
import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.EmailJaCadastradoException;
import br.ufal.ic.p2.bikeshare.exceptions.EmailNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.IdentificacaoBicicletaNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.IdentificacaoEstacaoNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.IdentificacaoUsuarioNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.MetodoPagamentoInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.NivelBateriaNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.TipoBicicletaInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.TipoUsuarioInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.BicicletaConvencional;
import br.ufal.ic.p2.bikeshare.models.BicicletaEletrica;
import br.ufal.ic.p2.bikeshare.models.Cartao;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.PessoaFisica;
import br.ufal.ic.p2.bikeshare.models.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.models.Pix;
import br.ufal.ic.p2.bikeshare.models.Plano;
import br.ufal.ic.p2.bikeshare.models.Usuario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Servico central que mantem o estado e executa as regras de negocio do sistema Bikeshare.
 */
public class SistemaBikeshare implements Serializable {
    private static final long serialVersionUID = 3L;

    private Map<String, Usuario> usuarios;
    private int contadorUsuarios;
    private Map<String, Estacao> estacoes;
    private int contadorEstacoes;
    private Map<String, Bicicleta> bicicletas;

    /**
     * Inicializa um sistema vazio com contadores de identificadores partindo de 1.
     */
    public SistemaBikeshare() {
        this.usuarios = new HashMap<>();
        this.contadorUsuarios = 1;
        this.estacoes = new HashMap<>();
        this.contadorEstacoes = 1;
        this.bicicletas = new HashMap<>();
    }

    /**
     * Cadastra um usuario pessoa fisica com plano padrao ({@code avulso}).
     *
     * @param nome  nome do usuario
     * @param email e-mail unico do usuario
     * @param senha senha de acesso
     * @param tipo  deve ser {@code pessoaFisica}
     * @param cpf   CPF do usuario
     * @return identificador unico gerado (ex.: {@code U1})
     * @throws BikeshareException em caso de dados invalidos ou e-mail duplicado
     */
    public String criarUsuario(String nome, String email, String senha, String tipo, String cpf) throws BikeshareException {
        return criarUsuario(nome, email, senha, tipo, cpf, "avulso", null);
    }

    /**
     * Cadastra um usuario pessoa fisica com plano e mensalidade informados.
     *
     * @param nome           nome do usuario
     * @param email          e-mail unico do usuario
     * @param senha          senha de acesso
     * @param tipo           deve ser {@code pessoaFisica}
     * @param cpf            CPF do usuario
     * @param planoStr       tipo de plano ({@code avulso} ou {@code assinante})
     * @param mensalidadeStr valor da mensalidade (apenas para {@code assinante})
     * @return identificador unico gerado
     * @throws BikeshareException em caso de dados invalidos ou e-mail duplicado
     */
    public String criarUsuario(String nome, String email, String senha, String tipo, String cpf,
                               String planoStr, String mensalidadeStr) throws BikeshareException {
        validarEmailDuplicado(email, null);

        if (!"pessoaFisica".equals(tipo)) {
            throw new TipoUsuarioInvalidoException();
        }

        Plano plano = new Plano(planoStr, mensalidadeStr);
        String id = "U" + contadorUsuarios;

        Usuario novoUsuario = new PessoaFisica(id, nome, email, senha, plano, cpf);

        usuarios.put(id, novoUsuario);
        contadorUsuarios++;
        return id;
    }

    /**
     * Cadastra um usuario pessoa juridica com plano padrao ({@code avulso}).
     *
     * @param nome        nome de exibicao
     * @param email       e-mail unico do usuario
     * @param senha       senha de acesso
     * @param tipo        deve ser {@code pessoaJuridica}
     * @param cnpj        CNPJ da empresa
     * @param razaoSocial razao social da empresa
     * @return identificador unico gerado
     * @throws BikeshareException em caso de dados invalidos ou e-mail duplicado
     */
    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial) throws BikeshareException {
        return criarUsuario(nome, email, senha, tipo, cnpj, razaoSocial, "avulso", null);
    }

    /**
     * Cadastra um usuario pessoa juridica com plano e mensalidade informados.
     *
     * @param nome           nome de exibicao
     * @param email          e-mail unico do usuario
     * @param senha          senha de acesso
     * @param tipo           deve ser {@code pessoaJuridica}
     * @param cnpj           CNPJ da empresa
     * @param razaoSocial    razao social da empresa
     * @param planoStr       tipo de plano ({@code avulso} ou {@code assinante})
     * @param mensalidadeStr valor da mensalidade (apenas para {@code assinante})
     * @return identificador unico gerado
     * @throws BikeshareException em caso de dados invalidos ou e-mail duplicado
     */
    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj,
                               String razaoSocial, String planoStr, String mensalidadeStr) throws BikeshareException {
        validarEmailDuplicado(email, null);

        if (!"pessoaJuridica".equals(tipo)) {
            throw new TipoUsuarioInvalidoException();
        }

        Plano plano = new Plano(planoStr, mensalidadeStr);
        String id = "U" + contadorUsuarios;

        Usuario novoUsuario = new PessoaJuridica(id, nome, email, senha, plano, cnpj, razaoSocial);

        usuarios.put(id, novoUsuario);
        contadorUsuarios++;
        return id;
    }

    /**
     * Consulta o valor de um atributo de um usuario cadastrado.
     *
     * @param usuarioId identificador do usuario
     * @param atributo  nome do atributo desejado
     * @return valor textual do atributo
     * @throws BikeshareException se o usuario ou atributo nao existir
     */
    public String getAtributoUsuario(String usuarioId, String atributo) throws BikeshareException {
        Usuario usuario = buscarUsuario(usuarioId);
        return usuario.getAtributo(atributo);
    }

    /**
     * Localiza o identificador de um usuario pelo e-mail (ignorando maiusculas e minusculas).
     *
     * @param email e-mail a pesquisar
     * @return identificador unico do usuario encontrado
     * @throws BikeshareException se o e-mail for nulo/vazio ou nao pertencer a nenhum usuario
     */
    public String getUsuarioPorEmail(String email) throws BikeshareException {
        if (email == null || email.trim().isEmpty()) {
            throw new EmailNuloException();
        }
        String emailBuscado = email.trim();
        for (Usuario u : usuarios.values()) {
            if (u.getEmail().equalsIgnoreCase(emailBuscado)) {
                return u.getId();
            }
        }
        throw new UsuarioNaoExisteException();
    }

    /**
     * Altera um atributo simples do usuario ({@code nome}, {@code email}, {@code senha},
     * {@code plano}, {@code saldo} ou {@code metodoPagamento} para {@code saldoPreCarregado}).
     *
     * @param usuarioId identificador do usuario
     * @param atributo  nome do atributo a alterar
     * @param valor     novo valor para o atributo
     * @throws BikeshareException se o usuario nao existir, o atributo nao puder ser alterado ou o valor for invalido
     */
    public void alterarUsuario(String usuarioId, String atributo, String valor) throws BikeshareException {
        Usuario usuario = buscarUsuario(usuarioId);
        if (atributo == null) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        switch (atributo) {
            case "nome":
                usuario.alterarNome(valor);
                break;
            case "email":
                Usuario.validarEmail(valor);
                validarEmailDuplicado(valor, usuario.getId());
                usuario.alterarEmail(valor);
                break;
            case "senha":
                usuario.alterarSenha(valor);
                break;
            case "plano":
                usuario.alterarPlano(valor);
                break;
            case "saldo":
                usuario.recarregarSaldo(valor);
                break;
            case "metodoPagamento":
                if ("saldoPreCarregado".equals(valor)) {
                    usuario.ativarSaldoPreCarregado();
                } else {
                    throw new MetodoPagamentoInvalidoException();
                }
                break;
            default:
                throw new AtributoNaoPodeSerAlteradoException();
        }
    }

    /**
     * Altera o metodo de pagamento do usuario para Pix.
     *
     * @param usuarioId identificador do usuario
     * @param atributo  deve ser {@code metodoPagamento}
     * @param valor1    deve ser {@code pix}
     * @param chave     chave Pix do usuario
     * @throws BikeshareException se o usuario nao existir, o atributo/metodo for invalido ou a chave for nula
     */
    public void alterarUsuario(String usuarioId, String atributo, String valor1, String chave) throws BikeshareException {
        Usuario usuario = buscarUsuario(usuarioId);
        if (!"metodoPagamento".equals(atributo)) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        if (!"pix".equals(valor1)) {
            throw new MetodoPagamentoInvalidoException();
        }
        usuario.definirMetodoPagamento(new Pix(chave));
    }

    /**
     * Altera o metodo de pagamento do usuario para cartao.
     *
     * @param usuarioId identificador do usuario
     * @param atributo  deve ser {@code metodoPagamento}
     * @param valor1    deve ser {@code cartao}
     * @param numero    numero do cartao
     * @param validade  data de validade no formato {@code MM/AAAA}
     * @throws BikeshareException se o usuario nao existir, o atributo/metodo for invalido ou os dados do cartao forem invalidos
     */
    public void alterarUsuario(String usuarioId, String atributo, String valor1, String numero, String validade) throws BikeshareException {
        Usuario usuario = buscarUsuario(usuarioId);
        if (!"metodoPagamento".equals(atributo)) {
            throw new AtributoNaoPodeSerAlteradoException();
        }
        if (!"cartao".equals(valor1)) {
            throw new MetodoPagamentoInvalidoException();
        }
        usuario.definirMetodoPagamento(new Cartao(numero, validade));
    }

    /**
     * Remove um usuario cadastrado do sistema, liberando seu e-mail sem reaproveitar seu identificador.
     *
     * @param usuarioId identificador unico do usuario a remover
     * @throws BikeshareException se o identificador for nulo/vazio ou o usuario nao existir
     */
    public void removerUsuario(String usuarioId) throws BikeshareException {
        buscarUsuario(usuarioId);
        usuarios.remove(usuarioId);
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
     * Adiciona uma bicicleta convencional em uma estacao.
     * Valida os dados da bicicleta antes de verificar a existencia de vaga disponivel.
     *
     * @param estacaoId   identificador da estacao de destino
     * @param bicicletaId identificador unico da bicicleta
     * @param tipo        tipo da bicicleta (deve ser {@code convencional})
     * @throws BikeshareException se algum parametro for invalido, a bicicleta ja existir ou a estacao estiver cheia
     */
    public void adicionarBicicleta(String estacaoId, String bicicletaId, String tipo) throws BikeshareException {
        Estacao estacao = buscarEstacao(estacaoId);
        validarNovaBicicleta(bicicletaId);

        if ("eletrica".equals(tipo)) {
            throw new NivelBateriaNuloException();
        }
        if (!"convencional".equals(tipo)) {
            throw new TipoBicicletaInvalidoException();
        }

        Bicicleta novaBicicleta = new BicicletaConvencional(bicicletaId, estacaoId);
        estacao.adicionarBicicleta(novaBicicleta);
        bicicletas.put(bicicletaId, novaBicicleta);
    }

    /**
     * Adiciona uma bicicleta eletrica com nivel de bateria informado em uma estacao.
     * Valida os dados da bicicleta antes de verificar a existencia de vaga disponivel.
     *
     * @param estacaoId    identificador da estacao de destino
     * @param bicicletaId  identificador unico da bicicleta
     * @param tipo         tipo da bicicleta (deve ser {@code eletrica})
     * @param nivelBateria nivel percentual inicial de bateria (0 a 100)
     * @throws BikeshareException se algum parametro for invalido, a bicicleta ja existir ou a estacao estiver cheia
     */
    public void adicionarBicicleta(String estacaoId, String bicicletaId, String tipo, String nivelBateria) throws BikeshareException {
        Estacao estacao = buscarEstacao(estacaoId);
        validarNovaBicicleta(bicicletaId);

        if ("convencional".equals(tipo)) {
            throw new BicicletaNaoPossuiBateriaException();
        }
        if (!"eletrica".equals(tipo)) {
            throw new TipoBicicletaInvalidoException();
        }

        Bicicleta novaBicicleta = new BicicletaEletrica(bicicletaId, estacaoId, nivelBateria);
        estacao.adicionarBicicleta(novaBicicleta);
        bicicletas.put(bicicletaId, novaBicicleta);
    }

    /**
     * Remove uma bicicleta estacionada em uma estacao, liberando a vaga e o identificador da bicicleta.
     *
     * @param estacaoId   identificador da estacao onde a bicicleta deve estar
     * @param bicicletaId identificador da bicicleta a remover
     * @throws BikeshareException se a estacao ou bicicleta nao existir, ou se a bicicleta nao estiver na estacao
     */
    public void removerBicicleta(String estacaoId, String bicicletaId) throws BikeshareException {
        Estacao estacao = buscarEstacao(estacaoId);
        buscarBicicleta(bicicletaId);

        if (!estacao.contemBicicleta(bicicletaId)) {
            throw new BicicletaNaoEstaNestaEstacaoException();
        }

        estacao.removerBicicleta(bicicletaId);
        bicicletas.remove(bicicletaId);
    }

    /**
     * Consulta o valor de um atributo de uma bicicleta cadastrada.
     *
     * @param bicicletaId identificador da bicicleta
     * @param atributo    nome do atributo desejado
     * @return valor textual do atributo
     * @throws BikeshareException se a bicicleta ou o atributo nao existir
     */
    public String getAtributoBicicleta(String bicicletaId, String atributo) throws BikeshareException {
        Bicicleta bicicleta = buscarBicicleta(bicicletaId);
        return bicicleta.getAtributo(atributo);
    }

    /**
     * Consulta o estado operacional atual de uma bicicleta cadastrada.
     *
     * @param bicicletaId identificador da bicicleta
     * @return estado da bicicleta (ex.: {@code disponivel})
     * @throws BikeshareException se o identificador for nulo/vazio ou a bicicleta nao existir
     */
    public String getEstadoBicicleta(String bicicletaId) throws BikeshareException {
        Bicicleta bicicleta = buscarBicicleta(bicicletaId);
        return bicicleta.getEstado();
    }

    /**
     * Valida se o identificador de uma nova bicicleta e nao nulo e ainda nao esta em uso.
     *
     * @param bicicletaId identificador proposto para a bicicleta
     * @throws BikeshareException se o identificador for nulo/vazio ou ja existir
     */
    private void validarNovaBicicleta(String bicicletaId) throws BikeshareException {
        if (bicicletaId == null || bicicletaId.trim().isEmpty()) {
            throw new IdentificacaoBicicletaNulaException();
        }
        if (bicicletas.containsKey(bicicletaId)) {
            throw new BicicletaJaExisteException();
        }
    }

    /**
     * Valida o identificador e retorna a bicicleta correspondente no cadastro.
     *
     * @param bicicletaId identificador da bicicleta
     * @return instancia da bicicleta encontrada
     * @throws BikeshareException se o identificador for nulo/vazio ou a bicicleta nao existir
     */
    private Bicicleta buscarBicicleta(String bicicletaId) throws BikeshareException {
        if (bicicletaId == null || bicicletaId.trim().isEmpty()) {
            throw new IdentificacaoBicicletaNulaException();
        }
        Bicicleta bicicleta = bicicletas.get(bicicletaId);
        if (bicicleta == null) {
            throw new BicicletaNaoExisteException();
        }
        return bicicleta;
    }

    /**
     * Valida o identificador e retorna a estacao correspondente no cadastro.
     *
     * @param estacaoId identificador da estacao
     * @return instancia da estacao encontrada
     * @throws BikeshareException se o identificador for nulo/vazio ou a estacao nao existir
     */
    private Estacao buscarEstacao(String estacaoId) throws BikeshareException {
        if (estacaoId == null || estacaoId.trim().isEmpty()) {
            throw new IdentificacaoEstacaoNulaException();
        }
        Estacao estacao = estacoes.get(estacaoId);
        if (estacao == null) {
            throw new EstacaoNaoExisteException();
        }
        return estacao;
    }

    /**
     * Valida o identificador e retorna o usuario correspondente no cadastro.
     *
     * @param usuarioId identificador do usuario
     * @return instancia do usuario encontrado
     * @throws BikeshareException se o identificador for nulo/vazio ou o usuario nao existir
     */
    private Usuario buscarUsuario(String usuarioId) throws BikeshareException {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new IdentificacaoUsuarioNulaException();
        }
        Usuario usuario = usuarios.get(usuarioId);
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }
        return usuario;
    }

    /**
     * Verifica se o e-mail informado ja esta em uso por outro usuario cadastrado.
     *
     * @param email             e-mail a verificar
     * @param usuarioIdIgnorado identificador de usuario a desconsiderar (ou {@code null} no cadastro)
     * @throws BikeshareException se ja existir outro usuario com o mesmo e-mail
     */
    private void validarEmailDuplicado(String email, String usuarioIdIgnorado) throws BikeshareException {
        if (email == null || email.trim().isEmpty()) {
            return; // Sera validado pelo construtor/alteracao de Usuario (EmailNuloException)
        }
        for (Usuario u : usuarios.values()) {
            if (usuarioIdIgnorado != null && u.getId().equals(usuarioIdIgnorado)) {
                continue;
            }
            if (u.getEmail().equalsIgnoreCase(email.trim())) {
                throw new EmailJaCadastradoException();
            }
        }
    }
}
