package br.ufrpe.bsi.mpoo.petspeed.os.dominio;

public enum Prioridade {

    ALTA("Alta"),

    BAIXA("Baixa");

    private final String descricao;

    Prioridade(String descricao) {
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
