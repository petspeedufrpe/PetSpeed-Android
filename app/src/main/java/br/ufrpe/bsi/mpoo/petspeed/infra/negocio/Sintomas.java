package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum  Sintomas {

    SINTOMA1(false),SINTOMA2(false);
    public boolean descricao;

    Sintomas(boolean descricao){this.descricao = descricao;}

    public boolean getDescricao(){return descricao;}
    public void setDescricao(boolean descricao){this.descricao = true;}




    public List<Sintomas> createSintomas(){

        return Arrays.asList(Sintomas.values());}
    @Override
    public String toString() {
        return String.valueOf(this.descricao);
    }



}
