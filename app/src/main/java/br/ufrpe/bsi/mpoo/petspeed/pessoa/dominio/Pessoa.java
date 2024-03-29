package br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio;

public class Pessoa{

    private long id;

    private String nome;

    private String cpf;

    private Endereco endereco;

    private long idUsuario;

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setFkUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }


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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
