package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class MainActivity extends AppCompatActivity {
	private DBHelper dbHelper = new DBHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/**
		long codCurr;
		long codPessoa;
		Endereco endereco = new Endereco();
		endereco.setCep("5001230");
		endereco.setNumero(126);
		endereco.setComplemento("Rua 4");
		endereco.setLogradouro("logradoro 2");
		endereco.setCidade("Recife");
		endereco.setUf("PE");
		endereco.setBairro("Espinheiro");
		EnderecoDAO enderecoDAO= new EnderecoDAO();
		codCurr =enderecoDAO.cadastraEndereco(endereco);
		endereco.setId(codCurr);

		Pessoa pessoa = new Pessoa();
		pessoa.setCpf("8890128421908");
		pessoa.setNome("Gabriel Alves");
		pessoa.setEndereco(endereco);
		PessoaDAO pessoaDAO = new PessoaDAO();
		codPessoa =pessoaDAO.cadastraPessoa(pessoa);
		pessoa.setId(codPessoa);
		Usuario usuario = new Usuario();
		usuario.setEmail("teste2@gmail.com");
		usuario.setSenha("1234567");
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		long codUs = usuarioDAO.cadastrarUsuario(usuario);
		usuario.setId(codUs);
		Cliente cliente = new Cliente();
		cliente.setDadosPessoais(pessoa);
		cliente.setUsuario(usuario);
		cliente.setAvaliacao(5);
		ClienteDAO clienteDAO = new ClienteDAO();
		long codCl = clienteDAO.cadastraCliente(cliente);

		Cliente cliente;
		Usuario usuario;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		cliente = clienteDAO.getClienteById((long) 5);
		//cliente.setDadosUsuario(usuarioDAO.getUsuario("teste@gmail.com"));
		Toast.makeText(MainActivity.this, String.valueOf(cliente.getUsuario().getId()),Toast.LENGTH_LONG).show();
		 **/

		Cliente cliente1 = getClienteCompleto(2);

		Toast.makeText(MainActivity.this,cliente1.getDadosPessoais().getEndereco().getBairro(),Toast.LENGTH_LONG).show();
	}
	// esse metodo fica no service
	public Pessoa getPessoaCompleta(long idPessoa){
		Pessoa pessoa;
		PessoaDAO pessoaDAO = new PessoaDAO();
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		pessoa = pessoaDAO.getPessoaById(idPessoa);
		Cursor data = pessoaDAO.getIdEnderecoByPessoa(idPessoa);
		if (data != null && data.moveToFirst()) {
			int indexEnd = data.getColumnIndex(DBHelper.COL_PESSOA_ENDERECO);
			long idEnd = data.getLong(indexEnd);
			data.close();
			pessoa.setEndereco(enderecoDAO.getEnderecoById((int)idEnd));

			return pessoa;
		}
		return null;
	}
	//esse metodo fica no service!!!!
	public Cliente getClienteCompleto(long idCliente){
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
			Pessoa pessoa = getPessoaCompleta(idPessoa);
			cliente.setDadosPessoais(pessoa);
			cliente.setUsuario(usuarioDAO.getUsuario(idusuario));
			return cliente;

		}
		return null;
	}
}
