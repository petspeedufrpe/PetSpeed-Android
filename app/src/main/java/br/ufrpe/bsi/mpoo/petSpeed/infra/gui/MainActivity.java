package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;

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
		Toast.makeText(MainActivity.this, String.valueOf(cliente.getUsuario().getId()),Toast.LENGTH_LONG).show();*/
		/**
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
		medico.setUsuario(usuario);

        MedicoServices services = new MedicoServices();
        try {
            services.cadastraMedico(medico);
        }catch(AppException e){
            Toast.makeText(MainActivity.this, String.valueOf(e)).show();

        }**/
		/**UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = new Usuario();
		usuario.setEmail("caio.farias@gmail.com");
		usuario.setSenha("123456");
		usuario.setId(usuarioDAO.cadastrarUsuario(usuario));

		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Caio Farias");
		pessoa.setCpf("123827382");
		Long codPessoa = pessoaDAO.cadastraPessoa(pessoa);
		pessoa.setId(codPessoa);

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

		Endereco endereco;
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		endereco = enderecoDAO.getEnderecoById((long)1);
		//Toast.makeText(MainActivity.this,endereco.getCidade(),Toast.LENGTH_LONG).show();

		Pessoa pessoa;
		PessoaDAO pessoaDAO = new PessoaDAO();
		pessoa = pessoaDAO.getPessoaById((long)1);
		//Toast.makeText(MainActivity.this,pessoa.getNome(),Toast.LENGTH_LONG).show();

		Usuario usuario;
		UsuarioDAO usuarioDAO= new UsuarioDAO();

		usuario = usuarioDAO.getUsuario("caio.farias@gmail.com");
		Toast.makeText(MainActivity.this,String.valueOf(usuario.getId()),Toast.LENGTH_LONG).show();



		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = new Usuario();
		usuario.setEmail("gabriel.alves@gmail.com");
		usuario.setSenha("1234567");
		Long idUser= usuarioDAO.cadastrarUsuario(usuario);
		usuario.setId(idUser);

		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Gabriel Alves");
		pessoa.setCpf("123827890");
		Long codPessoa = pessoaDAO.cadastraPessoa(pessoa);
		pessoa.setId(codPessoa);

		Endereco end = new Endereco();
		end.setUf("PE");
		end.setCep("12345");
		end.setNumero(11);
		end.setComplemento("Rua 3");
		end.setBairro("Ali");
		end.setLogradouro("Aquele");
		end.setCidade("Recife");
		end.setFkPessoa(codPessoa);
		EnderecoDAO enderecoDAO= new EnderecoDAO();
		long codCurr =enderecoDAO.cadastraEndereco(end);
		end.setId(codCurr);


		Cliente cliente = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();
		cliente.setUsuario(usuario);
		cliente.setAvaliacao(10);
		cliente.setDadosPessoais(pessoa);
		cliente.setIdPessoa(codPessoa);
		cliente.setIdUsuario(idUser);
		long codCliente = clienteDAO.cadastraCliente(cliente);
		cliente.setId(codCliente);



		Pessoa pessoa;
		PessoaDAO p = new PessoaDAO();
		pessoa=p.getPessoaById((long)1);
		Cliente cliente;
		ClienteDAO clienteDAO= new ClienteDAO();
		cliente = clienteDAO.getClienteByEmail("gabriel.alves@gmail.com");
		Toast.makeText(MainActivity.this,pessoa.getNome(),Toast
		.LENGTH_LONG).show();

		**/
		startActivity(new Intent(MainActivity.this,LoginActivity.class));
		//ClienteServices clienteServices = new ClienteServices();

		//Toast.makeText(MainActivity.this,Boolean.toString(clienteServices.isEmailClienteCadastrado("teste@gmail.com")),Toast.LENGTH_LONG).show();
	}
}
