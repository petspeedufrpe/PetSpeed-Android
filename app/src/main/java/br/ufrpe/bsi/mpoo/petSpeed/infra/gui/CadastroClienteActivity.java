package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

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
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class CadastroClienteActivity extends AppCompatActivity {

    EditText mNome,mCpf,mEmail,mSenha,mcmfSenha;
    String nome,cpf,email,senha,cmfSenha;
    Button mButtoRegister;
    TextView mTextLogin;
    ClienteServices clienteServices = new ClienteServices();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        mButtoRegister = findViewById(R.id.register);

        mButtoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { cadastrar(); }

        });
    }

    public Cliente cadastrar(){
        boolean res = false;
        String message = new String();
        capturaTextos();
        if(!isCamposValidos()){
            res = false;
        }
        Cliente cliente = criarCliente();
        cliente.setUsuario(criarUsuario());
        cliente.setDadosPessoais(criarPessoa());
            res = clienteServices.isEmailClienteNaoCadastrado(cliente.getUsuario().getEmail());
            Toast.makeText(CadastroClienteActivity.this,Boolean.toString(res),Toast.LENGTH_LONG).show();
        if (res == true){//cliente nao esta no banco
            Intent registerEnd = new Intent(CadastroClienteActivity.this, CadastroEnderecoActivity.class);
            registerEnd.putExtra("cliente",cliente);
            startActivity(registerEnd);
        }else{
            Toast.makeText(CadastroClienteActivity.this,message,Toast.LENGTH_SHORT).show();
            limparCampos();

        }

     return cliente;

    }

    public void capturaTextos(){
        findEditTexts();
        nome = mNome.getText().toString().trim();
        cpf = mCpf.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        senha = mSenha.getText().toString().trim();
        cmfSenha = mcmfSenha.getText().toString().trim();
    }

    public void findEditTexts(){

        mNome = (EditText) findViewById(R.id.username);
        mCpf = (EditText) findViewById(R.id.cpf);
        mEmail = (EditText) findViewById(R.id.email);
        mSenha = (EditText) findViewById(R.id.passwd);
        mcmfSenha = (EditText)findViewById(R.id.cnfpasswd);
        mTextLogin = (TextView) findViewById(R.id.login);

    }

    private boolean isCamposValidos() {
        View focusView = null;
        boolean res = true;
        //reseta os erros
        mNome.setError(null);
        mCpf.setError(null);
        mSenha.setError(null);
        mEmail.setError(null);
        mcmfSenha.setError(null);

        if (isCampoVazio(nome)){
            mNome.setError("Campo vazio");
            focusView = mNome;
            res = false;
        }  else if (isCampoVazio(cpf)) {
            mCpf.setError("Campo vazio");
            focusView = mCpf;
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

    private Cliente criarCliente(){
        Cliente cliente = new Cliente();
        cliente.setAvaliacao(5);
        return cliente;
    }

    private Usuario criarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setEmail(email);

        return usuario;
    }

    private Pessoa criarPessoa(){
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf(cpf);
        pessoa.setNome(nome);

        return pessoa;
    }

    private void limparCampos(){
        mNome.setText("");
        mSenha.setText("");
        mcmfSenha.setText("");
        mEmail.setText("");
        mCpf.setText("");

        }

}

