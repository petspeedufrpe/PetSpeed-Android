package br.ufrpe.bsi.mpoo.petspeed.medico.dominio;

import java.io.Serializable;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.clinica.dominio.Clinica;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class Medico implements Serializable {

    private long id;

    private double avaliacao;

    private String crmv;

    private Usuario usuario;

    private Pessoa dadosPessoais;

    private String telefone;

    private byte[] foto;

    private List<Clinica> clinicas;

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pessoa getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(Pessoa dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public List<Clinica> getClinicas() {
        return clinicas;
    }

    public void setClinicas(List<Clinica> clinicas) {
        this.clinicas = clinicas;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
