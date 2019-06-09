package br.ufrpe.bsi.mpoo.petspeed.animal.dominio;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class Animal {

    private long id;

    private String nome;

    private String raca;

    private double peso;

    private int nascimento;

    private long fkCliente;

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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getNascimento() {
        return nascimento;
    }

    public void setNascimento(int nascimento) {
        this.nascimento = nascimento;
    }

    public List<OrdemServico> getHistorico() {
        return historico;
    }

    public void setHistorico(List<OrdemServico> historico) {
        this.historico = historico;
    }

}
