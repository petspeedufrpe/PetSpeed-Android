package br.ufrpe.bsi.mpoo.petspeed.os.dominio;

public class Triagem {

    private long id;

    private String sintomas;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String outros;


    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }

    //Sintomas tem uma tabela que guarda o id da triagem
    //todos os sintomas que tem aquele id da triagem, eu recupero numa lista de sintomas dentro da triagem
}
