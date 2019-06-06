package br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio;

public class Endereco {

    private long id;

    private String cep;

    private String uf;

    private String cidade;

    private String bairro;

    private String logradouro;

    private long numero;

    private String complemento;

    private long fkClinica;

    private long fkPessoa;

    private Double latitude;

    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }



    public long getFkPessoa() {
        return fkPessoa;
    }

    public void setFkPessoa(long fkPessoa) {
        this.fkPessoa = fkPessoa;
    }

    public long getFkClinica() {
        return fkClinica;
    }

    public void setFkClinica(long fkClinica) {
        this.fkClinica = fkClinica;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

}
