package br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio;

import android.database.Cursor;

import java.util.InputMismatchException;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.animal.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class ClienteServices {
	private final ClienteDAO clienteDAO = new ClienteDAO();
	private final PessoaDAO pessoaDAO = new PessoaDAO();
	private final UsuarioDAO usuarioDAO= new UsuarioDAO();

	private AnimalDAO animalDAO;

	public static boolean validacpf(String CPF) {
		if (CPF.equals("00000000000") ||
				CPF.equals("11111111111") ||
				CPF.equals("22222222222") || CPF.equals("33333333333") ||
				CPF.equals("44444444444") || CPF.equals("55555555555") ||
				CPF.equals("66666666666") || CPF.equals("77777777777") ||
				CPF.equals("88888888888") || CPF.equals("99999999999") ||
				(CPF.length() != 11))
			return(false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i=0; i<9; i++) {
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else dig10 = (char)(r + 48);

			sm = 0;
			peso = 11;
			for(i=0; i<10; i++) {
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else dig11 = (char)(r + 48);

			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return(true);
			else return(false);
		} catch (InputMismatchException erro) {
			return(false);
		}
	}
	public Cliente getClienteCompleto(long idCliente){
		PessoaServices pessoaServices  = new PessoaServices();
		Cliente cliente;
		ClienteDAO clienteDAO = new ClienteDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		PessoaDAO pessoaDAO = new PessoaDAO();
		cliente = clienteDAO.getClienteById(idCliente);
		Cursor data = clienteDAO.getIdObjectByCliente(idCliente);//metodo para recuperar os do cliente com o id n no banco
		if(data!= null && data.moveToFirst()){
			int indexPessoa = data.getColumnIndex(DBHelper.COL_CLIENTE_DADOS_ESSOAIS);//get o indice do id da pessoa
			int indexUsuario = data.getColumnIndex(DBHelper.COL_CLIENTE_FK_USUARIO); //get o indice do id do usuario
			long idPessoa = data.getLong(indexPessoa);
			long idusuario = data.getLong(indexUsuario);
			data.close();
			Pessoa pessoa = pessoaServices.getPessoaCompleta(idPessoa);
			cliente.setDadosPessoais(pessoa);
			cliente.setUsuario(usuarioDAO.getUsuario(idusuario));
			return cliente;

		}
		return null;
	}

	public void cadastraCliente() {

	}

	public void deletaCliente() {

	}

	public Endereco getEnderecoById() {
		return null;
	}

	public Animal getAnimalById() {
		return null;
	}

	public Animal getAnimalByRaca() {
		return null;
	}

	public List<Endereco> getAllEndereco() {
		return null;
	}

	public List<Animal> getAllAnimal() {
		return null;
	}

	public void removeEndereco() {

	}

	public void adicionaEndereco() {

	}

	public Cliente getClienteById() {
		return null;
	}

	public Cliente getClienteByEmail() {
		return null;
	}

	public void alteraEmail() {

	}

	public void alteraSenha() {

	}

	public void alteraAvaliacao() {

	}


}
