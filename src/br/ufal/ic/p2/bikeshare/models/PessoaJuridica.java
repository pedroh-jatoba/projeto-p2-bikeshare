package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;

public class PessoaJuridica extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cnpj;
    private String razaoSocial;

    public PessoaJuridica(String id, String nome, String email, String senha, Plano plano, String cnpj, String razaoSocial) throws BikeshareException {
        super(id, nome, email, senha, plano);
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new BikeshareException("CNPJ nao pode ser nulo.");
        }
        if (razaoSocial == null || razaoSocial.trim().isEmpty()) {
            throw new BikeshareException("Razao social nao pode ser nula.");
        }
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String getTipo() {
        return "pessoaJuridica";
    }

    @Override
    public String getAtributo(String atributo) throws BikeshareException {
        if ("cnpj".equals(atributo)) {
            return this.cnpj;
        }
        if ("razaoSocial".equals(atributo)) {
            return this.razaoSocial;
        }
        if ("cpf".equals(atributo)) {
            throw new BikeshareException("Usuario nao eh pessoa fisica.");
        }
        return super.getAtributo(atributo);
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
}
