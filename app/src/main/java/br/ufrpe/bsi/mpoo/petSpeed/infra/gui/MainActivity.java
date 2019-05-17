package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.negocio.UsuarioServices;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class MainActivity extends AppCompatActivity {
	private DBHelper dbHelper = new DBHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toast.makeText(MainActivity.this,"BEM VINDO",Toast.LENGTH_LONG).show();
		Usuario usuario = Sessao.instance.getUsuario();
		TextView welcomeView = findViewById(R.id.welcome_view);
		welcomeView.setText(getString(R.string.user_name_act, usuario.getEmail()));
		TextView ultimoAcessoView = findViewById(R.id.ultimo_acesso_view);
		String ultimoAcesso = getString(R.string.las_access_act, Sessao.instance.getUltimoAcesso());
		ultimoAcessoView.setText(ultimoAcesso);

	}
}
