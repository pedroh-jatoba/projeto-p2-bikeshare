package br.ufal.ic.p2.bikeshare.models;

/**
 * Representa uma bicicleta convencional (sem bateria) do sistema Bikeshare.
 */
public class BicicletaConvencional extends Bicicleta {
    private static final long serialVersionUID = 1L;

    /**
     * Cria uma bicicleta convencional vinculada a uma estacao.
     *
     * @param id        identificador unico da bicicleta
     * @param estacaoId identificador da estacao inicial
     */
    public BicicletaConvencional(String id, String estacaoId) {
        super(id, estacaoId);
    }

    @Override
    public String getTipo() {
        return "convencional";
    }
}
