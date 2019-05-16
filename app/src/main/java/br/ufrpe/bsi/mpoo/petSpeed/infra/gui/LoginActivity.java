package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.ufrpe.bsi.mpoo.petSpeed.R;

public class LoginActivity extends AppCompatActivity {
	private String[] tipoUsuario = new String[]{"Clínica", "Veterinário", "Cliente"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

	}
}
