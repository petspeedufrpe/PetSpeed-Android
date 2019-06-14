package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;


import java.util.Dictionary;
import java.util.Hashtable;

public enum  Sintomas {

    Vomito(false),Diarreia(false), Desidratação(false),Febre(false),Letargia(false),
    OlhosVermelhos(false),Tosse(false),DorAbdominal(false),Paralisia(false),ExcessoDeUrina(false),
    MandibulaCaida(false),OlhosAmarelados(false);
    public boolean descricao;

    Sintomas(boolean descricao){this.descricao = descricao;}

    public boolean getDescricao(){return descricao;}
    public void setDescricao(boolean descricao){this.descricao = true;}


    @Override
    public String toString() {
        return String.valueOf(this.name());
    }



}
