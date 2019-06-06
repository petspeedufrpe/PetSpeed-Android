package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;

public class EditDadosClienteActivity extends AppCompatActivity {

    private EditText mNome,mEmail,mTelefone;
    private String nome, email, telefone;
    private android.support.v7.widget.Toolbar toolbar;
    private Cliente cliente = Sessao.instance.getCliente();
    private ClienteServices clienteServices = new ClienteServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dados_cliente);
        toolbar = (Toolbar) findViewById(R.id.toolbar_altera_dados);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_checked,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        alteraDados();
        return super.onOptionsItemSelected(item);
    }

    public void findEditTexts(){

        mNome = (EditText) findViewById(R.id.campo_altera_nome_cliente);
        mTelefone = (EditText) findViewById(R.id.campo_altera_telefone_cliente);
        mEmail = (EditText) findViewById(R.id.campo_altera_email_cliente);
    }

    public void capturaTextos(){
        findEditTexts();
        nome = mNome.getText().toString().trim();
        telefone = mTelefone.getText().toString().trim();
        email = mEmail.getText().toString().trim();
    }

    public boolean validaCampos(){
        capturaTextos();
        boolean result = true;
        View focusView = null;
        if (nome.isEmpty()){
            mNome.setError("Campo vazio");
            focusView = mNome;
            result = false;
        }else if(telefone.isEmpty()){
            mTelefone.setError("Campo vazio");
            focusView = mTelefone;
            result = false;
        } else if (email.isEmpty()){
            mEmail.setError("Campo Vazio");
            focusView = mEmail;
            result = false;
        }

        if (!result){
            focusView.requestFocus();
        }
        return result;
    }

    public void setNovosDados(){
        capturaTextos();
        if (validaCampos()){
            cliente.getDadosPessoais().setNome(nome);
            cliente.getUsuario().setEmail(email);
            //faltando o telefone
        } else {
            Toast.makeText(this,"Dados Alterados com sucesso",Toast.LENGTH_SHORT).show();
        }

    }

    public void alteraDados(){
        setNovosDados();
        if (cliente!= null){
            clienteServices.alterarDadosCliente(cliente);
            Toast.makeText(this,"Dados Alterados com sucesso",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditDadosClienteActivity.this,PerfilClienteActivity.class));
            finish();
        } else {
            Toast.makeText(this,"Verificar Dados",Toast.LENGTH_SHORT).show();
        }
    }
}
