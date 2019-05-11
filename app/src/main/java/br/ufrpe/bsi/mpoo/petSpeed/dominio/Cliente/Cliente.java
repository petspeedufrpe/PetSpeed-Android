package br.ufrpe.bsi.mpoo.petSpeed.dominio.Cliente;

import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.dominio.Endereco.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.dominio.Pessoa.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.dominio.Usuario;

public class Cliente {

	private long id;

	private double avaliacao;

	private Pessoa dadosPessoais;

	//private List<Animal> animais;
	private Animal animais;

	private Usuario dadosUsuario;



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

	public Pessoa getDadosPessoais() {
		return dadosPessoais;
	}

	public void setDadosPessoais(Pessoa dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}

	public Animal getAnimal() {
		return animais;
	}

	public void setAnimal(Animal animais) {
		this.animais = animais;
	}

	public Usuario getDadosUsuario() { return dadosUsuario; }

	public void setDadosUsuario(Usuario dadosUsuario) { this.dadosUsuario = dadosUsuario; }
}
