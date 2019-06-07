package br.ufrpe.bsi.mpoo.petspeed.animal.dominio;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
//import java.awt.image;

public class Animal {

    private long id;

    private String nome;

    private String raca;

    private float peso;

    private int idade;

    private long fkCliente;
    //private BufferedImage foto;

    private List<OrdemServico> historico;

    public long getFkCliente() { return fkCliente; }

    public void setFkCliente(long fkCliente) { this.fkCliente = fkCliente; }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<OrdemServico> getHistorico() {
        return historico;
    }

    public void setHistorico(List<OrdemServico> historico) {
        this.historico = historico;
    }

}
