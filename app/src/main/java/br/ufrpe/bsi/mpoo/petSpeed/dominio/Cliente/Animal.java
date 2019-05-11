package br.ufrpe.bsi.mpoo.petSpeed.dominio.Cliente;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.dominio.Serviços.OrdemServico;
//import java.awt.image;

public class Animal {

	private long id;

	private String nome;

	private String raca;

	private long idade;

	private String peso;

	private Cliente cliente;


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

	public long getIdade() {
		return idade;
	}

	public void setIdade(long idade) {
		this.idade = idade;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
