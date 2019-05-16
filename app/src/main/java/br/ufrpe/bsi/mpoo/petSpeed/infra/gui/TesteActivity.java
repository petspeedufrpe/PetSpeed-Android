package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.negocio.UsuarioServices;

public class TesteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);


		Usuario usuario = new Usuario();
		usuario.setEmail("asgsdfsdfasdgagregebr");
		usuario.setSenha("WEasdasdGWEG");
		UsuarioServices uServices = new UsuarioServices();
		try {
			long codUs = uServices.cadastrarUsuario(usuario);
			usuario.setId(codUs);
			Toast.makeText(TesteActivity.this, "Cadastro usuario bem sucedido",Toast.LENGTH_LONG).show();
		}catch (AppException e){
			Toast.makeText(TesteActivity.this, "Email já cadastrado",Toast.LENGTH_LONG).show();
		}

		Pessoa pessoa = new Pessoa();
		pessoa.setCpf("012213124");
		pessoa.setNome("MEDICO SERVICE");
		PessoaDAO pessoaDAO = new PessoaDAO();
		long codPessoa = pessoaDAO.cadastraPessoa(pessoa);

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

		pessoa.setEndereco(end);

		Medico medico = new Medico();
		medico.setAvaliacao(4);
		medico.setCrmv("teste");
		medico.setDadosPessoais(pessoa);
		medico.setUsuario(usuario);


       	MedicoServices mservices = new MedicoServices();
        try {
            long id = mservices.cadastraMedico(medico);
            medico.setId(id);
			Toast.makeText(TesteActivity.this, "Cadastro medico bem sucedido",Toast.LENGTH_LONG).show();
		}catch(AppException e){
			Toast.makeText(TesteActivity.this, String.valueOf(e),Toast.LENGTH_LONG).show();
        }
    }
}
