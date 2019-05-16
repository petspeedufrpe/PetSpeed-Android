package br.ufrpe.bsi.mpoo.petSpeed.medico.dominio;

import java.io.Serializable;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.clinica.dominio.Clinica;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class Medico implements Serializable {

	private long id;

	private double avaliacao;

	private String crmv;

	private Usuario usuario;

	private Pessoa dadosPessoais;

	private List<Clinica> clinicas;


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
}
