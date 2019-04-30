package br.ufrpe.bsi.mpoo.petSpeed.dominio;

import java.util.List;

public class Clinica {
    private long id;

    private String email;

    private String senha;

    private double avaliacao;

    private String crmv;

    private Endereco endereco;

    private List<Medico> medicosCredenciados;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Medico> getMedicosCredenciados() {
        return medicosCredenciados;
    }

    public void setMedicosCredenciados(List<Medico> medicosCredenciados) {
        this.medicosCredenciados = medicosCredenciados;
    }
}
