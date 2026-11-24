package br.ufal.ic.p2.bikeshare.exceptions;

/**
 * Lancada quando se tenta adicionar ou devolver uma bicicleta em uma estacao sem vagas livres.
 */
public class EstacaoSemVagasDisponiveisException extends BikeshareException {
    public EstacaoSemVagasDisponiveisException() {
        super("Estacao sem vagas disponiveis.");
    }
}
