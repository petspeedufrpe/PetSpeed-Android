package br.ufrpe.bsi.mpoo.petSpeed.negocio;

import com.bsi.mpoo.petspeed.dominio.Animal;
import com.bsi.mpoo.petspeed.dominio.OrdemServico;
import com.bsi.mpoo.petspeed.infra.persistencia.AnimalDAO;
import com.bsi.mpoo.petspeed.infra.persistencia.MedicoDAO;
import com.bsi.mpoo.petspeed.infra.persistencia.OrdemServicoDAO;

import java.util.List;

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
