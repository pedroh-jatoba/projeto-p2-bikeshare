package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.EmailInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.EmailNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.NomeNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.PlanoInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.PlanoSemMensalidadeException;
import br.ufal.ic.p2.bikeshare.exceptions.SenhaNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaCartaoException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaPixException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoUsaSaldoPreCarregadoException;

import java.io.Serializable;
import java.util.Locale;

/**
 * Representa um usuario generico do sistema Bikeshare.
 * Especializado em {@link PessoaFisica} e {@link PessoaJuridica}.
 */
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 2L;

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String estado;                      // "ativo", "inadimplente"
    private MetodoPagamento metodoPagamento;    // null representa "nenhum"
    private SaldoPreCarregado saldoPreCarregado; // preserva o saldo entre trocas de metodo
    private Plano plano;
    private String aluguelAtivo;
    private double totalPendencias;

    /**
     * Cria um usuario validando seus dados obrigatorios basicos.
     *
     * @param id    identificador unico do usuario
     * @param nome  nome do usuario
     * @param email e-mail do usuario
     * @param senha senha de acesso
     * @param plano plano contratado pelo usuario
     * @throws BikeshareException se algum dado obrigatorio for nulo ou invalido
     */
    public Usuario(String id, String nome, String email, String senha, Plano plano) throws BikeshareException {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.estado = "ativo";
        this.metodoPagamento = null;
        this.saldoPreCarregado = null;
        this.plano = plano;
        this.aluguelAtivo = null;
        this.totalPendencias = 0.0;
    }

    /**
     * Valida se o nome informado nao e nulo nem composto apenas por espacos.
     *
     * @param nome nome a validar
     * @throws NomeNuloException se o nome for nulo ou vazio
     */
    public static void validarNome(String nome) throws NomeNuloException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeNuloException();
        }
    }

    /**
     * Valida se o e-mail informado nao e nulo e possui formato basico valido.
     *
     * @param email e-mail a validar
     * @throws BikeshareException se o e-mail for nulo/vazio ou invalido
     */
    public static void validarEmail(String email) throws BikeshareException {
        if (email == null || email.trim().isEmpty()) {
            throw new EmailNuloException();
        }
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            throw new EmailInvalidoException();
        }
    }

    /**
     * Valida se a senha informada nao e nula nem composta apenas por espacos.
     *
     * @param senha senha a validar
     * @throws SenhaNulaException se a senha for nula ou vazia
     */
    public static void validarSenha(String senha) throws SenhaNulaException {
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaNulaException();
        }
    }

    /**
     * Retorna o tipo concreto do usuario ({@code pessoaFisica} ou {@code pessoaJuridica}).
     *
     * @return tipo do usuario
     */
    public abstract String getTipo();

    /**
     * Consulta o valor de um atributo do usuario pelo seu nome.
     *
     * @param atributo nome do atributo consultado
     * @return representacao textual do valor do atributo
     * @throws BikeshareException se o atributo nao existir ou nao for aplicavel ao usuario
     */
    public String getAtributo(String atributo) throws BikeshareException {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "nome":
                return this.nome;
            case "email":
                return this.email;
            case "senha":
                return this.senha;
            case "tipo":
                return this.getTipo();
            case "estado":
                return this.estado;
            case "plano":
                return this.plano.getTipo();
            case "metodoPagamento":
                return this.metodoPagamento == null ? "nenhum" : this.metodoPagamento.getTipo();
            case "aluguelAtivo":
                return this.aluguelAtivo == null ? "nenhum" : this.aluguelAtivo;
            case "totalPendencias":
                return String.format(new Locale("pt", "BR"), "%.2f", this.totalPendencias);
            case "mensalidade":
                if (this.plano.getMensalidade() == null) {
                    throw new PlanoSemMensalidadeException();
                }
                return this.plano.getMensalidadeFormatada();
            case "numeroCartao":
            case "validadeCartao":
                if (this.metodoPagamento == null) {
                    throw new UsuarioNaoUsaCartaoException();
                }
                return this.metodoPagamento.getAtributo(atributo);
            case "chavePix":
                if (this.metodoPagamento == null) {
                    throw new UsuarioNaoUsaPixException();
                }
                return this.metodoPagamento.getAtributo(atributo);
            case "saldo":
                if (this.metodoPagamento == null) {
                    throw new UsuarioNaoUsaSaldoPreCarregadoException();
                }
                return this.metodoPagamento.getAtributo(atributo);
            default:
                throw new AtributoNaoExisteException();
        }
    }

    /**
     * Altera o nome do usuario apos validar o novo valor.
     *
     * @param novoNome novo nome do usuario
     * @throws NomeNuloException se o nome for nulo ou vazio
     */
    public void alterarNome(String novoNome) throws NomeNuloException {
        validarNome(novoNome);
        this.nome = novoNome;
    }

    /**
     * Altera o e-mail do usuario apos validar o novo valor.
     *
     * @param novoEmail novo e-mail do usuario
     * @throws BikeshareException se o e-mail for nulo ou invalido
     */
    public void alterarEmail(String novoEmail) throws BikeshareException {
        validarEmail(novoEmail);
        this.email = novoEmail;
    }

    /**
     * Altera a senha do usuario apos validar o novo valor.
     *
     * @param novaSenha nova senha do usuario
     * @throws SenhaNulaException se a senha for nula ou vazia
     */
    public void alterarSenha(String novaSenha) throws SenhaNulaException {
        validarSenha(novaSenha);
        this.senha = novaSenha;
    }

    /**
     * Altera o plano do usuario.
     * Escolher o mesmo plano ja ativo preserva a mensalidade contratada;
     * migrar de {@code avulso} para {@code assinante} aplica a mensalidade padrao ({@code 30,00}).
     *
     * @param novoPlano tipo do plano desejado ({@code avulso} ou {@code assinante})
     * @throws BikeshareException se o tipo de plano for invalido
     */
    public void alterarPlano(String novoPlano) throws BikeshareException {
        if (novoPlano == null || (!novoPlano.equals("avulso") && !novoPlano.equals("assinante"))) {
            throw new PlanoInvalidoException();
        }
        if (novoPlano.equals(this.plano.getTipo())) {
            return;
        }
        if ("avulso".equals(novoPlano)) {
            this.plano = new Plano("avulso", null);
        } else {
            this.plano = new Plano("assinante", Plano.MENSALIDADE_PADRAO_ASSINANTE);
        }
    }

    /**
     * Define o metodo de pagamento ativo do usuario (ex.: {@link Cartao} ou {@link Pix}).
     *
     * @param novoMetodo instancia do metodo de pagamento
     */
    public void definirMetodoPagamento(MetodoPagamento novoMetodo) {
        this.metodoPagamento = novoMetodo;
    }

    /**
     * Ativa o metodo de pagamento {@link SaldoPreCarregado}, preservando o saldo ja existente
     * caso o usuario tenha utilizado saldo pre-carregado anteriormente.
     */
    public void ativarSaldoPreCarregado() {
        if (this.saldoPreCarregado == null) {
            this.saldoPreCarregado = new SaldoPreCarregado();
        }
        this.metodoPagamento = this.saldoPreCarregado;
    }

    /**
     * Realiza uma recarga acumulativa no saldo pre-carregado do usuario.
     *
     * @param valorStr valor textual da recarga
     * @throws BikeshareException se o usuario nao usar saldo pre-carregado ou se o valor for invalido
     */
    public void recarregarSaldo(String valorStr) throws BikeshareException {
        if (!(this.metodoPagamento instanceof SaldoPreCarregado)) {
            throw new UsuarioNaoUsaSaldoPreCarregadoException();
        }
        ((SaldoPreCarregado) this.metodoPagamento).adicionarSaldo(valorStr);
    }

    /** @return identificador unico do usuario */
    public String getId() { return id; }

    /** @return nome do usuario */
    public String getNome() { return nome; }

    /** @return e-mail do usuario */
    public String getEmail() { return email; }

    /** @return senha do usuario */
    public String getSenha() { return senha; }

    /** @return estado atual do usuario ({@code ativo} ou {@code inadimplente}) */
    public String getEstado() { return estado; }

    /** @param estado novo estado do usuario */
    public void setEstado(String estado) { this.estado = estado; }

    /** @return metodo de pagamento ativo, ou {@code null} se nenhum estiver configurado */
    public MetodoPagamento getMetodoPagamento() { return metodoPagamento; }

    /** @return plano contratado pelo usuario */
    public Plano getPlano() { return plano; }

    /** @return identificador do aluguel ativo, ou {@code null} se nao houver */
    public String getAluguelAtivo() { return aluguelAtivo; }

    /** @param aluguelAtivo identificador do aluguel ativo */
    public void setAluguelAtivo(String aluguelAtivo) { this.aluguelAtivo = aluguelAtivo; }

    /** @return valor acumulado de pendencias financeiras */
    public double getTotalPendencias() { return totalPendencias; }

    /** @param totalPendencias novo total de pendencias financeiras */
    public void setTotalPendencias(double totalPendencias) { this.totalPendencias = totalPendencias; }
}
