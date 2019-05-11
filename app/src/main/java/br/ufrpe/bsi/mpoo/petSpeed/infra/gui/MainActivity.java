package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
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
		endereco.setCep("123456");
		endereco.setNumero(121);
		endereco.setComplemento("Rua 2");
		EnderecoDAO enderecoDAO= new EnderecoDAO();
		codCurr =enderecoDAO.cadastraEndereco(endereco);
		endereco.setId(codCurr);
		Pessoa pessoa = new Pessoa();
		pessoa.setCpf("012213124");
		pessoa.setNome("Caio castro");
		pessoa.setEndereco(endereco);
		PessoaDAO pessoaDAO = new PessoaDAO();
		codPessoa =pessoaDAO.cadastraPessoa(pessoa);
		pessoa.setId(codPessoa);
		Usuario usuario = new Usuario();
		usuario.setEmail("teste@gmail.com");
		usuario.setSenha("12345");
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		long codUs = usuarioDAO.cadastrarUsuario(usuario);
		usuario.setId(codUs);
		Cliente cliente = new Cliente();
		cliente.setDadosPessoais(pessoa);
		cliente.setDadosUsuario(usuario);
		cliente.setAvaliacao(10);
		ClienteDAO clienteDAO = new ClienteDAO();
		long codCl = clienteDAO.cadastraCliente(cliente);
		cliente.setId(codCl);
		cliente = clienteDAO.getClienteById((long) 1);
		Toast.makeText(MainActivity.this,String.valueOf(cliente.getAvaliacao()),Toast.LENGTH_LONG).show();;
		 **/

		Cliente cliente;
		Usuario usuario;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		cliente = clienteDAO.getClienteById((long) 5);
		//cliente.setDadosUsuario(usuarioDAO.getUsuario("teste@gmail.com"));
		Toast.makeText(MainActivity.this, String.valueOf(cliente.getUsuario().getId()),Toast.LENGTH_LONG).show();
	}
}
