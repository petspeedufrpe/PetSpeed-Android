package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;

public class AccountSelectionActivity extends AppCompatActivity {
	private DBHelper dbHelper = new DBHelper();
    private Button butClinica;
    private Button butVet;
    private Button butCliente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_selection);


		butClinica = findViewById(R.id.buttonClinica);
		butVet = findViewById(R.id.buttonVeterinrario);
		butClinica = findViewById(R.id.buttonClinica);

        butClinica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginClinica = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(loginClinica);
            }
        });

        butVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginMedico = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(loginMedico);
            }
        });


	}
}
