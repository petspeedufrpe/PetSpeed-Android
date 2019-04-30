package br.ufrpe.bsi.mpoo.petSpeed.dominio;

import java.util.List;

public class Cliente {

	private long id;

	private String email;

	private String senha;

	private double avaliacao;

	private Endereco endereco;

	private Pessoa dadosPessoais;

	private List<Animal> animais;



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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Pessoa getDadosPessoais() {
		return dadosPessoais;
	}

	public void setDadosPessoais(Pessoa dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}

	public List<Animal> getAnimais() {
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}
}
