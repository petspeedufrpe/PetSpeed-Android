package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
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

		/*Cliente cliente;
		Usuario usuario;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		cliente = clienteDAO.getClienteById((long) 5);
		//cliente.setDadosUsuario(usuarioDAO.getUsuario("teste@gmail.com"));
		Toast.makeText(MainActivity.this, String.valueOf(cliente.getUsuario().getId()),Toast.LENGTH_LONG).show();

		Usuario usuario = new Usuario();
		usuario.setEmail("teste@gmail.com");
		usuario.setSenha("12345");
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		long codUs = usuarioDAO.cadastrarUsuario(usuario);
		usuario.setId(codUs);

		Pessoa pessoa = new Pessoa();
		pessoa.setCpf("012213124");
		pessoa.setNome("testenome");
		PessoaDAO pessoaDAO = new PessoaDAO();
		long codPessoa =pessoaDAO.cadastraPessoa(pessoa);

		Endereco end = new Endereco();
		end.setUf("teste");
		end.setCep("123456");
		end.setNumero(121);
		end.setComplemento("Rua 2");
		end.setBairro("teste");
		end.setLogradouro("teste");
		end.setCidade("teste");
		end.setFkPessoa(codPessoa);
		EnderecoDAO enderecoDAO= new EnderecoDAO();
		long codCurr =enderecoDAO.cadastraEndereco(end);
		end.setId(codCurr);

		Medico medico = new Medico();
		medico.setAvaliacao(5.0);
		medico.setCrmv("teste");
		medico.setDadosPessoais(pessoa);
		medico.setUsuario(usuario);*/



	}
	public void loginClinica(View view){

    }

	public void loginMedico(View view){

	}
	public void loginCliente(View view){

	}
}
