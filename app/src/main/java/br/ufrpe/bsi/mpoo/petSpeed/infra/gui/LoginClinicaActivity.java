package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;

public class LoginClinicaActivity extends AppCompatActivity {

	private EditText mEmail;
	private EditText mSenha;
	private Button loginbtn;
	private Button sairbtn;
	private String email;
	private String senha;

	private ClienteServices clienteServices = new ClienteServices();
	private PessoaServices pessoaServices = new PessoaServices();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		sairbtn = (Button) findViewById(R.id.loginActLoginBtn);
		sairbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent registerIntent = new Intent(LoginActivity.this, CadastroClienteActivity.class);
				startActivity(registerIntent);

			}
		});


		mEmail = (EditText) findViewById(R.id.LoginEmailTxBx);
		mSenha = (EditText) findViewById(R.id.LoginPswrdTxBx);
		loginbtn = (Button) findViewById(R.id.loginActloginBtn);

		logar();
		loginbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(it);
			}
		});
	}

	private void logar(){
		capturaTextos();
		if(!camposValidos()){
			return;
		}

		boolean result = false;
		try {
			clienteServices.login(email,senha);
			if (result){
				startActivity(new Intent(LoginActivity.this,MainActivity.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void capturaTextos(){
		email = mEmail.getText().toString().trim();
		senha = mSenha.getText().toString().trim();
	}

	private boolean camposValidos(){
		boolean result = true;
		String email = mEmail.getText().toString();
		String senha = mSenha.getText().toString();
		View focusView = null;
		//validando senha
		if (TextUtils.isEmpty(senha) ||!validaSenha(senha) ){
			mSenha.setError("Senha inválida");
			focusView = mSenha;
			result = false;
		}

		if (TextUtils.isEmpty(email)){
			mEmail.setError("Campo Obrigatório");
			focusView = mEmail;
			result = false;
		}else if (!validaEmail(email)){
			mEmail.setError("Email inválido");
			focusView = mEmail;
			result = false;
		}

		if (!result){
			focusView.requestFocus();
		}
		return result;
	}

	private boolean validaSenha(String senha){
		return senha.length() >2;
	}

	private boolean validaEmail(String email){
		return email.contains("@");
	}
}
