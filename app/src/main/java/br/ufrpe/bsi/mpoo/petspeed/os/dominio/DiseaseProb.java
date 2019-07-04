package br.ufrpe.bsi.mpoo.petspeed.os.dominio;


public class DiseaseProb{
    private double probability;
    private String nome;

    public double getProb() {
        return probability;
    }

    public void setProb(double id) {
        this.probability = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        String s = "%1$s: %2$.1f%%";
        s = String.format(s, this.nome, this.probability * 100);

        return s;
    }

}