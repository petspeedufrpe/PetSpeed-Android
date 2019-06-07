package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

/**
 * Created by gabri on 22/01/2018.
 */

public enum ContasDeUsuario {
    MEDICO("Medico"), CLIENTE("Cliente"), CLINICA("Clinica");
    private final String descricao;

    ContasDeUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
