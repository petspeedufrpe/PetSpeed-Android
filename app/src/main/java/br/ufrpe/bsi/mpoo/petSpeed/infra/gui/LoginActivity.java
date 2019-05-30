package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.gui.CadastroClienteActivity;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.gui.HomeClienteDrawer;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petSpeed.medico.gui.CadastroMedicoActivity;
import br.ufrpe.bsi.mpoo.petSpeed.medico.gui.HomeMedicoActivity;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mSenha;
    private Button loginbtn;
    private Button cadastrarBtn;
    private String email;
    private String senha;
    private ContasDeUsuario contaSelecionada;
    private boolean contaSetada = false;


    private ClienteServices clienteServices = new ClienteServices();
    private MedicoServices medicoServices = new MedicoServices();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.LoginEmailTxBx);
        mSenha = (EditText) findViewById(R.id.LoginPswrdTxBx);
        loginbtn = (Button) findViewById(R.id.loginActLoginBtn);
        cadastrarBtn = (Button) findViewById(R.id.LoginActCadastrarBtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });



        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastraUsuario();
            }
        });

        Spinner contaSpinner = findViewById(R.id.usuario_login);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
                ContasDeUsuario.values());
        contaSpinner.setAdapter(spinnerAdapter);
        contaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                contaSelecionada = ContasDeUsuario.values()[position];
                StringBuilder tipoConta = new StringBuilder();
                tipoConta.append("Fazer login como: ");
                tipoConta.append(contaSelecionada.getDescricao());
                Toast.makeText(LoginActivity.this, tipoConta, Toast.LENGTH_LONG).show();
                contaSetada = true;
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                contaSetada = false;
            }
        });

    }

    private void cadastraUsuario() {
        if (contaSetada) {
            if (contaSelecionada == ContasDeUsuario.MEDICO) {
                Intent registerIntent = new Intent(LoginActivity.this, CadastroMedicoActivity.class);
                startActivity(registerIntent);
            } else if (contaSelecionada == ContasDeUsuario.CLIENTE) {
                Intent registerIntent = new Intent(LoginActivity.this, CadastroClienteActivity.class);
                startActivity(registerIntent);
            } else {
                Toast.makeText(LoginActivity.this, "Selecione o tipo de conta a cadastrar.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void logar() {
        capturaTextos();
        if (!camposValidos()) {
            return;
        }

        boolean result = true;

        try {
            if(contaSelecionada == ContasDeUsuario.MEDICO) {
                if(medicoServices.usuarioPossuiMedico(email)){
                    medicoServices.login(email, senha);
                    home();
                }else{
                    Toast.makeText(LoginActivity.this,"Credenciais inválidas.",Toast.LENGTH_SHORT).show();
                    result = !result;
                }
            }else if(contaSelecionada == ContasDeUsuario.CLIENTE){
                if(clienteServices.usuarioPossuiCliente(email)){
                    clienteServices.login(email, senha);
                    home();
                }else{
                    Toast.makeText(LoginActivity.this,"Credenciais inválidas.",Toast.LENGTH_SHORT).show();
                    result = !result;
                }
            }
        } catch (Exception e) {
            result = false;
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (result) {
            home();
        }
    }

    private void home(){
        if(contaSelecionada == ContasDeUsuario.MEDICO) {
            startActivity(new Intent(LoginActivity.this, HomeMedicoActivity.class));

        }else if(contaSelecionada == ContasDeUsuario.CLIENTE){
            startActivity(new Intent(LoginActivity.this, HomeClienteDrawer.class));
        }

    }

    private void capturaTextos() {
        email = mEmail.getText().toString().trim();
        senha = mSenha.getText().toString().trim();
    }

    private boolean camposValidos() {
        boolean result = true;
        String email = mEmail.getText().toString();
        String senha = mSenha.getText().toString();
        View focusView = null;
        //validando senha
        if (TextUtils.isEmpty(senha) || !validaSenha(senha)) {
            mSenha.setError("Senha inválida");
            focusView = mSenha;
            result = false;
        }

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Campo Obrigatório");
            focusView = mEmail;
            result = false;
        } else if (!validaEmail(email)) {
            mEmail.setError("Email inválido");
            focusView = mEmail;
            result = false;
        }

        if (!result) {
            focusView.requestFocus();
        }
        return result;
    }

    private boolean validaSenha(String senha) {
        return senha.length() > 2;
    }

    private boolean validaEmail(String email) {
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

    private boolean isCampoVazio(String valor) {
        boolean resultado = TextUtils.isEmpty(valor) || valor.trim().isEmpty();
        return resultado;
    }
}
