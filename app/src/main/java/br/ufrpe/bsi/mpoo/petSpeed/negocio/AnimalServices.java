package br.ufrpe.bsi.mpoo.petSpeed.negocio;

import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petSpeed.infra.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.persistencia.MedicoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.persistencia.OrdemServicoDAO;

public class AnimalServices {

	private AnimalDAO animalDAO;

	private OrdemServicoDAO ordemServicoDAO;

	private MedicoDAO medicoDAO;

	public void cadastraAnimal() {

	}

	public void deletaAnimal() {

	}

	public void alteraFoto() {

	}

	public void adicionaOS() {

	}

	public Animal getAnimalById() {
		return null;
	}

	public OrdemServico getHistoricoById() {
		return null;
	}

	public List<OrdemServico> getHistoricoByMedico() {
		return null;
	}

	public List<OrdemServico> getAllHistorico() {
		return null;
	}

}
