package br.ufrpe.bsi.mpoo.petSpeed.os.negocio;


import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petSpeed.animal.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.medico.persistencia.MedicoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.os.persistencia.OrdemServicoDAO;

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
