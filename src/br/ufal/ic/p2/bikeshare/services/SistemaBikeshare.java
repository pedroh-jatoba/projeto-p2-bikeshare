package br.ufal.ic.p2.bikeshare.services;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.models.PessoaFisica;
import br.ufal.ic.p2.bikeshare.models.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.models.Plano;
import br.ufal.ic.p2.bikeshare.models.Usuario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SistemaBikeshare implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Usuario> usuarios;
    private int contadorUsuarios;

    public SistemaBikeshare() {
        this.usuarios = new HashMap<>();
        this.contadorUsuarios = 1;
    }

    // Cadastro Pessoa Fisica - Plano padrao (avulso)
    public String criarUsuario(String nome, String email, String senha, String tipo, String cpf) throws BikeshareException {
        return criarUsuario(nome, email, senha, tipo, cpf, "avulso", null);
    }

    // Cadastro Pessoa Fisica - Com plano e mensalidade
    public String criarUsuario(String nome, String email, String senha, String tipo, String cpf,
                               String planoStr, String mensalidadeStr) throws BikeshareException {
        validarEmailDuplicado(email);

        if (!"pessoaFisica".equals(tipo)) {
            throw new BikeshareException("Tipo invalido.");
        }

        Plano plano = new Plano(planoStr, mensalidadeStr);
        String id = "U" + contadorUsuarios;

        Usuario novoUsuario = new PessoaFisica(id, nome, email, senha, plano, cpf);

        usuarios.put(id, novoUsuario);
        contadorUsuarios++;
        return id;
    }

    // Cadastro Pessoa Juridica - Plano padrao (avulso)
    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial) throws BikeshareException {
        return criarUsuario(nome, email, senha, tipo, cnpj, razaoSocial, "avulso", null);
    }

    // Cadastro Pessoa Juridica - Com plano e mensalidade
    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj,
                               String razaoSocial, String planoStr, String mensalidadeStr) throws BikeshareException {
        validarEmailDuplicado(email);

        if (!"pessoaJuridica".equals(tipo)) {
            throw new BikeshareException("Tipo invalido.");
        }

        Plano plano = new Plano(planoStr, mensalidadeStr);
        String id = "U" + contadorUsuarios;

        Usuario novoUsuario = new PessoaJuridica(id, nome, email, senha, plano, cnpj, razaoSocial);

        usuarios.put(id, novoUsuario);
        contadorUsuarios++;
        return id;
    }


    // Consulta de atributo do usuario
    public String getAtributoUsuario(String usuarioId, String atributo) throws BikeshareException {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new BikeshareException("Identificacao do usuario nao pode ser nula.");
        }
        Usuario usuario = usuarios.get(usuarioId);
        if (usuario == null) {
            throw new BikeshareException("Usuario nao existe.");
        }
        return usuario.getAtributo(atributo);
    }

    private void validarEmailDuplicado(String email) throws BikeshareException {
        if (email == null || email.trim().isEmpty()) {
            return; // Sera validado pelo construtor de Usuario ("Email nao pode ser nulo.")
        }
        for (Usuario u : usuarios.values()) {
            if (u.getEmail().equalsIgnoreCase(email.trim())) {
                throw new BikeshareException("Email ja cadastrado.");
            }
        }
    }
}
