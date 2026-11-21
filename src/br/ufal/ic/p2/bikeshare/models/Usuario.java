package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import java.io.Serializable;

public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String estado;          // "ativo", "inadimplente"
    private String metodoPagamento; // "nenhum" por padrão
    private Plano plano;

    public Usuario(String id, String nome, String email, String senha, Plano plano) throws BikeshareException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new BikeshareException("Nome nao pode ser nulo.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new BikeshareException("Email nao pode ser nulo.");
        }
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            throw new BikeshareException("Email invalido.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new BikeshareException("Senha nao pode ser nula.");
        }

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.estado = "ativo";
        this.metodoPagamento = "nenhum";
        this.plano = plano;
    }


    public abstract String getTipo();

    public String getAtributo(String atributo) throws BikeshareException {
        switch (atributo) {
            case "nome":
                return this.nome;
            case "email":
                return this.email;
            case "tipo":
                return this.getTipo();
            case "estado":
                return this.estado;
            case "plano":
                return this.plano.getTipo();
            case "metodoPagamento":
                return this.metodoPagamento;
            case "mensalidade":
                if (this.plano.getMensalidade() == null) {
                    throw new BikeshareException("Plano nao possui mensalidade.");
                }
                return this.plano.getMensalidadeFormatada();
            default:
                throw new BikeshareException("Atributo nao existe.");
        }
    }

    // Getters e Setters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    public Plano getPlano() { return plano; }
    public void setPlano(Plano plano) { this.plano = plano; }
}
