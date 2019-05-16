package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.gui.ListaClinicasActivity;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class activity_register_endereco extends AppCompatActivity {

    ClienteServices clienteServices = new ClienteServices();

    EditText mLogradouro,mNumero,mCep,mUf,mBairro,mCidade,mComplemento;
    String logradouro,numero,cep,uf,bairro,cidade,complemento;
    Button mBtnCadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_endereco);
        mBtnCadastro = (Button) findViewById(R.id.cad_end);
        mBtnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }


    public Cliente getClienteByAct(){
        Intent registerEnd = getIntent();
        Cliente cliente = (Cliente) registerEnd.getExtras().getSerializable("cliente");
        return cliente;
    }

    public boolean cadastrar(){
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        String message = new String();
        boolean res = false;
        capturaTextos();
        Cliente cliente = getClienteByAct();;
        if (!isCamposValidos()){
            res = false;
            return res;
        }
        Endereco endereco = criarEndereco();
        endereco.setFkPessoa(cliente.getDadosPessoais().getId());
        cliente.getDadosPessoais().setEndereco(endereco);
        try {
            clienteServices.cadastraCliente(cliente, cliente.getUsuario(), cliente.getDadosPessoais());
            enderecoDAO.cadastraEndereco(endereco);
            res = true;
        } catch (AppException e) {
            e.printStackTrace();
            message = e.getMessage();
            res = false;
        }

        if(res){
            Toast.makeText(activity_register_endereco.this,"Usuario Cadastrado Com sucesso",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity_register_endereco.this,ListaClinicasActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(activity_register_endereco.this,message,Toast.LENGTH_SHORT).show();

        }
        return res;
    }
    public void findEditTexts(){

        mLogradouro = (EditText) findViewById(R.id.logradouro);
        mNumero = (EditText) findViewById(R.id.numero);
        mCep = (EditText) findViewById(R.id.cep);
        mUf = (EditText) findViewById(R.id.uf);
        mBairro = (EditText) findViewById(R.id.bairro);
        mCidade = (EditText) findViewById(R.id.cidade);
        mComplemento = (EditText) findViewById(R.id.complemento);
    }

    public void capturaTextos(){
        findEditTexts();
        logradouro = mLogradouro.getText().toString().trim();
        numero = mNumero.getText().toString().trim();
        cep = mCep.getText().toString().trim();
        uf = mUf.getText().toString().trim();
        bairro = mBairro.getText().toString().trim();
        cidade = mCidade.getText().toString().trim();
        complemento = mComplemento.getText().toString().trim();
    }

    private boolean isCamposValidos() {
        View focusView = null;
        boolean res = true;
        //reseta os erros
        mLogradouro.setError(null);
        mNumero.setError(null);
        mCep.setError(null);
        mUf.setError(null);
        mBairro.setError(null);
        mCidade.setError(null);
        mComplemento.setError(null);



        if (isCampoVazio(logradouro)){
            mLogradouro.setError("Campo vazio");
            focusView = mLogradouro;
            res = false;
        }  else if (isCampoVazio(numero)) {
            mNumero.setError("Campo vazio");
            focusView = mNumero;
            res = false;
        } else if (isCampoVazio(cep)) {
            mCep.setError("Campo Vazio");
            focusView = mCep;
            res = false;
        } else if (isCampoVazio(uf)){
            mUf.setError("Campo vazio");
            focusView = mUf;
            res = false;
        } else if (isCampoVazio(bairro)) {
            focusView = mBairro;
            mBairro.setError("Campo vazio");
            res = false;
        } else if(isCampoVazio(cidade)){
            focusView = mCidade;
            mCidade.setError("Campo Vazio");
            res = false;
        } else if(isCampoVazio(complemento)){
            focusView =mComplemento;
            mComplemento.setError("Campo Vazio");
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

    public Endereco criarEndereco(){
        Endereco endereco = new Endereco();
        endereco.setLogradouro(logradouro);
        endereco.setNumero(Long.parseLong(numero));
        endereco.setCep(cep);
        endereco.setUf(uf);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        return endereco;
    }

}


