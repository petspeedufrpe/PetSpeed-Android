package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper = new DBHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
