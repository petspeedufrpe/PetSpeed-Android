package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;


import java.util.Dictionary;
import java.util.Hashtable;

public enum  Sintomas {

    Vomito("Vomito"),Diarreia("Diarreia"), Desidratacao("Desidratação"),Febre("Febre"),Letargia("Letargia"),
    OlhosVermelhos("Olhos Vermelhos"),Tosse("Tosse"),DorAbdominal("Dor Abdominal"),Paralisia("Paralisia"),ExcessoDeUrina("Excesso de Urina"),
    MandibulaCaida("Mandibula Caida"),OlhosAmarelados("Olhos Amarelados");
    public String descricao;

    Sintomas(String descricao){this.descricao = descricao;}

    public String getDescricao(){return descricao;}
    public void setDescricao(String descricao){this.descricao = descricao;}


    @Override
    public String toString() {
        return String.valueOf(this.name());
    }



}
