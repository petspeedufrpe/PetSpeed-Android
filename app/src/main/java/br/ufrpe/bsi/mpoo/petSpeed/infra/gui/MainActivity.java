package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper = new DBHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "BEM VINDO", Toast.LENGTH_LONG).show();
        Usuario usuario = Sessao.instance.getUsuario();
        TextView welcomeView = findViewById(R.id.welcome_view);
        welcomeView.setText(getString(R.string.user_name_act, usuario.getEmail()));
        TextView ultimoAcessoView = findViewById(R.id.ultimo_acesso_view);
        String ultimoAcesso = getString(R.string.las_access_act, Sessao.instance.getUltimoAcesso());
        ultimoAcessoView.setText(ultimoAcesso);

    }
}
