package br.ufrpe.bsi.mpoo.petSpeed.dominio.Pessoa;

import br.ufrpe.bsi.mpoo.petSpeed.dominio.Endereco.Endereco;

public class Pessoa {

	private long id;

	private String nome;

	private String cpf;

	private Endereco endereco;


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

	public Endereco getEndereco(){ return this.endereco; }

	public void setEndereco(Endereco endereco){ this.endereco = endereco; }
}
