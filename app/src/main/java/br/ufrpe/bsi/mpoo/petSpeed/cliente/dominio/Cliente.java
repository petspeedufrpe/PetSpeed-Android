package br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio;

import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class Cliente {

	private long id;

	private double avaliacao;

	private Pessoa dadosPessoais;

	private List<Animal> animais;

	private Usuario usuario;

	private long idUsuario;

	private long idPessoa;


	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
