package br.ufrpe.bsi.mpoo.petSpeed.os.dominio;

import java.util.List;

public class Triagem {

    private List<String> sintomas;

    private String outros;


    public List<String> getSintomas() {
        return sintomas;
    }

    public void setSintomas(List<String> sintomas) {
        this.sintomas = sintomas;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }
}
