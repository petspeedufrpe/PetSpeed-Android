package br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.animal.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.clinica.dominio.Clinica;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class ClienteServices {

	private ClienteDAO clienteDAO = new ClienteDAO();

	private  UsuarioDAO usuarioDAO = new UsuarioDAO();

	private PessoaDAO pessoaDAO = new PessoaDAO();

	private AnimalDAO animalDAO;


	/**
	 * verificações são feitas antes do metodo cadastrar ser chamado.
	 * cadastra o cliente, sendo o último a ser cadastrado(tem 1 ou mais associações nele)
	 * @param cliente
	 * @param usuario
	 * @return cliente
	 */
	public Cliente cadastraCliente(Cliente cliente,Usuario usuario) {
		Long idUser = usuarioDAO.cadastrarUsuario(usuario);
		usuario.setId(idUser);
		cliente.setUsuario(usuario);
		Long idCliente = clienteDAO.cadastraCliente(cliente);
		cliente.setId(idCliente);
		return cliente;
	}

	public void deletaCliente(Cliente cliente) throws AppException {
		if (clienteDAO.getClienteById(cliente.getId())!=null){
			clienteDAO.deletaCliente(cliente);
		}
	}

	public void login(String email, String senha) throws AppException {
		Usuario usuario = usuarioDAO.getUsuario(email,senha);
		if (usuario == null){
			throw new AppException("Usuário ou senha inválida.");
		}

		else{
			Sessao.instance.setUsuario(usuario);
		}
	}

	//Metodo que verifica se o usuario está ou não cadastrado no banco.
	public boolean isEmailClienteCadastrado(String email){//retorna true se estiver no banco
		Usuario usuario = usuarioDAO.getUsuario(email);
		return ((usuario!= null && usuario.getEmail().length() >0));
	}

	public String getEmailByCliente(Long idCliente){
		Cliente cliente;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		cliente = clienteDAO.getClienteById(idCliente);
		Cursor data = clienteDAO.getIdObjectByCliente(idCliente);
		if (data!= null && data.moveToFirst()){
			int indexUsuario = data.getColumnIndex(DBHelper.COL_CLIENTE_FK_USUARIO);
			long idUsuario = data.getLong(indexUsuario);
			return usuarioDAO.getUsuario(idUsuario).getEmail();
		}
		return null;
	}

	//monta o objeto completo(com todos os atributos setados, usando o cursor para navegar entre os dados do banco.
	public Cliente getClienteCompleto(long idCliente){
		Cliente cliente;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		cliente = clienteDAO.getClienteById(idCliente);
		Cursor data = clienteDAO.getIdObjectByCliente(idCliente);

		if (data!= null && data.moveToFirst()){
			PessoaServices pessoaServices = new PessoaServices();
			int indexPessoa = data.getColumnIndex(DBHelper.COL_CLIENTE_FK_PESSOA);
			int indexUsuario = data.getColumnIndex(DBHelper.COL_CLIENTE_FK_USUARIO);
			int indexAnimal = data.getColumnIndex(DBHelper.COL_ANIMAL_FK_CLIENTE);
			long idAnimal = data.getLong(indexAnimal);
			long idPessoa = data.getLong(indexPessoa);
			long idUsuario = data.getLong(indexUsuario);

			data.close();
			Pessoa pessoa = pessoaServices.getPessoaCompleta(idPessoa);
			cliente.setDadosPessoais(pessoa);
			cliente.setUsuario(usuarioDAO.getUsuario(idUsuario));

			return cliente;
		}
		return null;
	}

	public void alterarDadosCliente(Cliente cliente){
		usuarioDAO.alterarEmail(cliente.getUsuario());
		pessoaDAO.alteraNome(cliente.getDadosPessoais());
		pessoaDAO.alteraCPF(cliente.getDadosPessoais());

	}

	public void logout(){
		Sessao sessao = Sessao.instance;
		sessao.reset();
	}

	public void alteraSenha(Cliente cliente){
		usuarioDAO.alterarSenha(cliente.getUsuario());
	}

	protected void alteraAvaliacao(Cliente cliente) {
		if(cliente.getAvaliacao()>5 || cliente.getAvaliacao() < 0){

		}
		clienteDAO.alteraAvaliacao(cliente);
	}

}
