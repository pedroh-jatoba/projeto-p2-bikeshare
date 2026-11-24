package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.BikeshareException;
import br.ufal.ic.p2.bikeshare.exceptions.CnpjNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.RazaoSocialNulaException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoEhPessoaFisicaException;

/**
 * Representa um usuario pessoa juridica, identificado por CNPJ e razao social.
 */
public class PessoaJuridica extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cnpj;
    private String razaoSocial;

    /**
     * Cria um usuario pessoa juridica validando CNPJ e razao social obrigatorios.
     *
     * @param id          identificador unico do usuario
     * @param nome        nome fantasia ou de exibicao
     * @param email       e-mail de contato
     * @param senha       senha de acesso
     * @param plano       plano contratado
     * @param cnpj        numero de CNPJ
     * @param razaoSocial razao social da empresa
     * @throws BikeshareException se algum dado obrigatorio for nulo ou invalido
     */
    public PessoaJuridica(String id, String nome, String email, String senha, Plano plano, String cnpj, String razaoSocial) throws BikeshareException {
        super(id, nome, email, senha, plano);
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new CnpjNuloException();
        }
        if (razaoSocial == null || razaoSocial.trim().isEmpty()) {
            throw new RazaoSocialNulaException();
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
            throw new UsuarioNaoEhPessoaFisicaException();
        }
        return super.getAtributo(atributo);
    }

    /** @return CNPJ da empresa */
    public String getCnpj() { return cnpj; }

    /** @param cnpj novo CNPJ da empresa */
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    /** @return razao social da empresa */
    public String getRazaoSocial() { return razaoSocial; }

    /** @param razaoSocial nova razao social da empresa */
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
}
