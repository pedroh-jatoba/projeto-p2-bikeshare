package br.ufal.ic.p2.bikeshare.services;

import java.io.Serializable;

/**
 * Raiz agregadora serializavel do sistema Bikeshare.
 * Mantem e conecta os servicos especializados de dominio para persistencia e orquestracao.
 */
public class SistemaBikeshare implements Serializable {
    private static final long serialVersionUID = 5L;

    private UsuarioService usuarioService;
    private EstacaoService estacaoService;
    private BicicletaService bicicletaService;
    private AluguelService aluguelService;

    /**
     * Inicializa o sistema instanciando todos os servicos de dominio conectados entre si.
     */
    public SistemaBikeshare() {
        this.usuarioService = new UsuarioService();
        this.estacaoService = new EstacaoService();
        this.bicicletaService = new BicicletaService(this.estacaoService);
        this.aluguelService = new AluguelService(this.usuarioService, this.estacaoService, this.bicicletaService);
    }

    /** @return servico responsavel pelos usuarios */
    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    /** @return servico responsavel pelas estacoes */
    public EstacaoService getEstacaoService() {
        return estacaoService;
    }

    /** @return servico responsavel pelas bicicletas */
    public BicicletaService getBicicletaService() {
        return bicicletaService;
    }

    /** @return servico responsavel pelos alugueis */
    public AluguelService getAluguelService() {
        return aluguelService;
    }
}
