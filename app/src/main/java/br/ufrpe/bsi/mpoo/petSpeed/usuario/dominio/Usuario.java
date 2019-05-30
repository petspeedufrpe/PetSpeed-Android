package br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio;


import java.io.Serializable;

public class Usuario implements Serializable {

    private long id;

    private String email;

    private String senha;


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
}
