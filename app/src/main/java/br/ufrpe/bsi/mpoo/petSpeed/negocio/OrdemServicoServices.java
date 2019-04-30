package br.ufrpe.bsi.mpoo.petSpeed.negocio;


import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petSpeed.infra.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.persistencia.MedicoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.persistencia.OrdemServicoDAO;

public class OrdemServicoServices {

	private OrdemServicoDAO ordemServicoDAO;

	private MedicoDAO medicoDAO;

	private ClienteDAO clienteDAO;

	private AnimalDAO animalDAO;

	public void cadastraOS() {

	}

	public void deletaOS() {

	}

	public OrdemServico GetOSbyId() {
		return null;
	}

	public List<OrdemServico> getOSbyPrioridade() {
		return null;
	}

	public List<OrdemServico> getAllOS() {
		return null;
	}

}
