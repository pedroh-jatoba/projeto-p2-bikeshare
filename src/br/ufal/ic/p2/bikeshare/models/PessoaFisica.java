package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;

public class PessoaFisica extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cpf;

    public PessoaFisica(String id, String nome, String email, String senha, Plano plano, String cpf) throws BikeshareException {
        super(id, nome, email, senha, plano);
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new BikeshareException("CPF nao pode ser nulo.");
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
            throw new BikeshareException("Usuario nao eh pessoa juridica.");
        }
        return super.getAtributo(atributo);
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
