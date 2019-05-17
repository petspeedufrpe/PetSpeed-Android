package br.ufrpe.bsi.mpoo.petSpeed.clinica.negocio;


import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.clinica.dominio.Clinica;
import br.ufrpe.bsi.mpoo.petSpeed.clinica.persistencia.ClinicaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.persistencia.MedicoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class ClinicaServices {

	private ClinicaDAO clinicaDAO = new ClinicaDAO();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private MedicoDAO medicoDAO = new MedicoDAO();

	private EnderecoDAO enderecoDAO = new EnderecoDAO();

	public Clinica cadastraClinica(Clinica clinica, Endereco endereco) {
		Long idUser = usuarioDAO.cadastrarUsuario(clinica.getUsuario());
		clinica.getUsuario().setId(idUser);
		clinica.setUsuario(clinica.getUsuario());
		Long idClinica = clinicaDAO.cadastraClinica(clinica);
		clinica.setId(idClinica);
		endereco.setFkClinica(idClinica);
		enderecoDAO.cadastraEndereco(endereco);
		return clinica;

	}

	public boolean isEmailClinicaCadastrada(String email){
		Usuario usuario= usuarioDAO.getUsuario(email);

		return (usuario!=null && usuario.getEmail().length()>0);
	}

	public void login(String email,String senha) throws AppException {
		Usuario usuario = usuarioDAO.getUsuario(email,senha);
		if (usuario == null){
			throw new AppException("Oops! Parece que algo deu errado");
		}
		Sessao.instance.setUsuario(usuario);


	}

	public void deletaClinica(Long idClinica) {

		if(clinicaDAO.getClinicaById(idClinica)!=null){
			clinicaDAO.deletaClinica(clinicaDAO.getClinicaById(idClinica));
		}

	}

	public void alteraAvaliacao() {

	}

	public void alteraEndereco() {

	}

	public void adicionaMedico() {

	}

	public void removeMedico() {

	}

	public void alteraCrmv() {

	}

	public Medico getMedicoById() {
		return null;
	}

	public List<Medico> getAllMedico() {
		return null;
	}

}
