package br.ufrpe.bsi.mpoo.petSpeed.animal.dominio;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.os.dominio.OrdemServico;
//import java.awt.image;

public class Animal {

	private long id;

	private String nome;

	private String raca;

	private float peso;

	private int idade;


	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}



	//private BufferedImage foto;

	private List<OrdemServico> historico;

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
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
