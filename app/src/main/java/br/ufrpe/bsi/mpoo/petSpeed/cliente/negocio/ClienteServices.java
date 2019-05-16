package br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.animal.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
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

	private EnderecoDAO enderecoDAO = new EnderecoDAO();

	private  UsuarioDAO usuarioDAO = new UsuarioDAO();

	private PessoaDAO pessoaDAO = new PessoaDAO();

	private AnimalDAO animalDAO;

	public Cliente cadastraCliente(Cliente cliente,Usuario usuario,Pessoa pessoa) throws AppException {
		if (usuarioDAO.getUsuario(cliente.getUsuario().getEmail()) != null) {
			throw new AppException("Usuário já possui conta de Cliente.");
		} else {

			Long idUser = usuarioDAO.cadastrarUsuario(usuario);
			usuario.setId(idUser);
			Long idPessoa = pessoaDAO.cadastraPessoa(pessoa);
			pessoa.setId(idPessoa);
			Long idCliente = clienteDAO.cadastraCliente(cliente);
			cliente.setId(idCliente);
		}

		return cliente;
	}
	public void deletaCliente(Cliente cliente) throws AppException {
		if (clienteDAO.getClienteById(cliente.getId())!=null){
			clienteDAO.deletaCliente(cliente);
		}

	}
	public Cliente login(String email, String senha) throws AppException {
		Usuario usuario = usuarioDAO.getUsuario(email,senha);
		Cliente cliente = new Cliente();
		if (usuario == null){
			cliente = null;
			throw new AppException("Usuário ou senha inválida.");
		}
		PessoaServices pessoaServices = new PessoaServices();
		cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
		cliente = getClienteCompleto(cliente.getId());

		return cliente;
	}

	public boolean isEmailClienteNaoCadastrado(String email){//retorna true se nao estiver no banco
		Usuario usuario = usuarioDAO.getUsuario(email);
		return (!(usuario!= null && usuario.getEmail().length() >0));
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

	public void alteraSenha(Cliente cliente){
		usuarioDAO.alterarSenha(cliente.getUsuario());
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
