package br.ufrpe.bsi.mpoo.petspeed.clinica.dominio;

import java.io.Serializable;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class Clinica implements Serializable {

    private long id;

    private String nome;

    private String razaoSocial;

    private double avaliacao;

    private String crmv;

    private Usuario usuario;

    private List<Endereco> enderecos;

    private List<Medico> medicosCredenciados;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
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

    public List<Medico> getMedicosCredenciados() {
        return medicosCredenciados;
    }

    public void setMedicosCredenciados(List<Medico> medicosCredenciados) {
        this.medicosCredenciados = medicosCredenciados;
    }
}
