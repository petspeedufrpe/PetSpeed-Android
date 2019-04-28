package com.bsi.mpoo.petspeed.dominio;
import java.util.List;
//import java.awt.image;

public class Animal {

	private long id;

	private String nome;

	private String raca;

	//private BufferedImage foto;

	private List<OrdemServico> historico;



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

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public List<OrdemServico> getHistorico() {
		return historico;
	}

	public void setHistorico(List<OrdemServico> historico) {
		this.historico = historico;
	}
}
