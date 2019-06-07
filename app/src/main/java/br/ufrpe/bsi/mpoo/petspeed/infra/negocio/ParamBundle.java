package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

public enum ParamBundle {
    TIPO("tipo"), CLIENTE("cliente"), MEDICO("medico"),
    CLINICA("clinica"), OBJETO("objeto"), ENDERECO("endereco");

    private final String descricao;

    ParamBundle(String descricao) {
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
