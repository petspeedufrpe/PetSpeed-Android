package br.ufrpe.bsi.mpoo.petSpeed.medico.negocio;

import br.ufrpe.bsi.mpoo.petSpeed.clinica.dominio.Clinica;
import br.ufrpe.bsi.mpoo.petSpeed.clinica.persistencia.ClinicaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.persistencia.MedicoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class MedicoServices {

	private MedicoDAO medicoDAO = new MedicoDAO();

	private EnderecoDAO enderecoDAO = new EnderecoDAO();

	private ClinicaDAO clinicaDAO = new ClinicaDAO();

	private UsuarioDAO usuarioDAO = new UsuarioDAO();


	public void cadastraMedico(Medico medico) {


		try{
		if (usuarioDAO.getUsuario(medico.getUsuario().getEmail()) != null) {
		}
		medicoDAO.cadastraMedico(medico);


	}

	public void deletaMedico() {

	}

	public void alteraAvaliacao() {

	}

	public void alteraClinica() {

	}

	public void alteraEndereco() {

	}

	public void alteraCrmv() {

	}

	public Endereco getEnderecoById() {
		return null;
	}

}
