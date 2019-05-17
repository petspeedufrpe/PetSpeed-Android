package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.clinica.dominio.Clinica;
import br.ufrpe.bsi.mpoo.petSpeed.clinica.negocio.ClinicaServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class activity_register_clinica extends AppCompatActivity {

    EditText mNome,mCrmv,mCnpj,mEmail,mSenha,mcmfSenha;
    String nome,crmv,cnpj,email,senha,cmfSenha;
    Button mButtonRegister;
    TextView mTextLogin;
    ClinicaServices clinicaServices = new ClinicaServices();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_clinica);

        mButtonRegister = (Button) findViewById(R.id.registerClinica);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

    }


    public Clinica cadastrar(){
        boolean res = false;
        String message = new String();
        capturaTextos();
        if(!isCamposValidos()){
            return null;
        }else {

            Clinica clinica = criarClinica();
            clinica.setUsuario(criarUsuario());
            res = clinicaServices.isEmailClinicaCadastrada(clinica.getUsuario().getEmail());//retorna true para cadastrado;
            Toast.makeText(activity_register_clinica.this, Boolean.toString(res), Toast.LENGTH_LONG).show();
            if (res ==false) {//cliente nao esta no banco
                Intent registerEnd = new Intent(activity_register_clinica.this, activity_register_endereco.class);
                Bundle accountBundle = new Bundle();
                accountBundle.putSerializable("clinica",clinica);
                accountBundle.putString("tipo","clinica");
                registerEnd.putExtra("bundle",accountBundle);
                startActivity(registerEnd);
            } else {
                Toast.makeText(activity_register_clinica.this, message, Toast.LENGTH_SHORT).show();
                limparCampos();

            }
            return clinica;
        }

    }

    public void capturaTextos(){
        findEditTexts();
        nome = mNome.getText().toString().trim();
        cnpj= mCnpj.getText().toString().trim();
        crmv = mCrmv.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        senha = mSenha.getText().toString().trim();
        cmfSenha = mcmfSenha.getText().toString().trim();
    }

    public void findEditTexts(){

        mNome = (EditText) findViewById(R.id.usernameClinica);
        mCnpj = (EditText) findViewById(R.id.cnpj);
        mCrmv = (EditText) findViewById(R.id.crmvClinica);
        mEmail = (EditText) findViewById(R.id.emailClinica);
        mSenha = (EditText) findViewById(R.id.passwdClinica);
        mcmfSenha = (EditText)findViewById(R.id.cnfpasswdClinica);
        mTextLogin = (TextView) findViewById(R.id.loginClinica);

    }

    private boolean isCamposValidos() {
        View focusView = null;
        boolean res = true;
        //reseta os erros
        mNome.setError(null);
        mCnpj.setError(null);
        mCrmv.setError(null);
        mSenha.setError(null);
        mEmail.setError(null);
        mcmfSenha.setError(null);

        if (isCampoVazio(nome)){
            mNome.setError("Campo vazio");
            focusView = mNome;
            res = false;
        }  else if (isCampoVazio(cnpj)) {
            mCnpj.setError("Campo vazio");
            focusView = mCnpj;
            res = false;
        } else if (isCampoVazio(crmv)){
            mCrmv.setError("Campo Inválido");
            focusView = mCrmv;
            res = false;
        } else if (!isEmailValido(email)) {
            mEmail.setError("Email inválido");
            focusView = mEmail;
            res = false;
        } else if (isCampoVazio(senha)){
            mSenha.setError("Campo vazio");
            focusView = mSenha;
            res = false;
        } else if (isCampoVazio(cmfSenha)) {
            focusView = mcmfSenha;
            mcmfSenha.setError("Campo vazio");
            res = false;
        } if(!isSenhasIguais(senha, cmfSenha) ){
            Toast.makeText(this, "Senhas Devem ser iguais", Toast.LENGTH_SHORT).show();
            focusView = mcmfSenha;
            res = false;
        }
        if(!res){
            focusView.requestFocus();
        }

        return res;
    }


    private boolean isCampoVazio(String valor){
        boolean resultado = TextUtils.isEmpty(valor) || valor.trim().isEmpty();
        return resultado;
    }

    private boolean isEmailValido(String email){
        boolean resultado = (!isCampoVazio(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

    private boolean isSenhasIguais(String senha,String cmfSenha){
        return ((senha.equals(cmfSenha)));
    }

    private Clinica criarClinica(){
        Clinica clinica = new Clinica();
        clinica.setRazaoSocial(cnpj);
        clinica.setNome(nome);
        clinica.setCrmv(crmv);
        clinica.setAvaliacao(5);

        return clinica;
    }

    private Usuario criarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setEmail(email);

        return usuario;
    }


    private void limparCampos(){
        mNome.setText("");
        mSenha.setText("");
        mcmfSenha.setText("");
        mEmail.setText("");
        mCnpj.setText("");
        mCrmv.setText("");
    }

}

