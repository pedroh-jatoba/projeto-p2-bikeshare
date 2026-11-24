package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.CpfNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoEhPessoaJuridicaException;

/**
 * Representa um usuario pessoa fisica, identificado por CPF.
 */
public class PessoaFisica extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cpf;

    /**
     * Cria um usuario pessoa fisica validando o CPF obrigatorio.
     *
     * @param id    identificador unico do usuario
     * @param nome  nome completo
     * @param email e-mail de contato
     * @param senha senha de acesso
     * @param plano plano contratado
     * @param cpf   numero de CPF
     * @throws BikeshareException se algum dado obrigatorio for nulo ou invalido
     */
    public PessoaFisica(String id, String nome, String email, String senha, Plano plano, String cpf) throws BikeshareException {
        super(id, nome, email, senha, plano);
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new CpfNuloException();
        }
        this.cpf = cpf;
    }

    @Override
    public String getTipo() {
        return "pessoaFisica";
    }

    @Override
    public String getAtributo(String atributo) throws BikeshareException {
        if ("cpf".equals(atributo)) {
            return this.cpf;
        }
        if ("cnpj".equals(atributo) || "razaoSocial".equals(atributo)) {
            throw new UsuarioNaoEhPessoaJuridicaException();
        }
        return super.getAtributo(atributo);
    }

    /** @return CPF do usuario */
    public String getCpf() { return cpf; }

    /** @param cpf novo CPF do usuario */
    public void setCpf(String cpf) { this.cpf = cpf; }
}
