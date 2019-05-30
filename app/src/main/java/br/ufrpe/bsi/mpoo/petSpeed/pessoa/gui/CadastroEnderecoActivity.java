package br.ufrpe.bsi.mpoo.petSpeed.pessoa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.infra.gui.FinalizaCadastroActivity;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ApiGeocoder;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.SessaoCadastro;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;

public class CadastroEnderecoActivity extends AppCompatActivity {
    Button mBtnCadastro;
    private EditText mLogradouro, mNumero, mCep, mUf, mBairro, mCidade, mComplemento,mLatitude,mLongitude;
    private String logradouro, numero, cep, uf, bairro, cidade, complemento;
    private Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cadastro_endereco);

        mBtnCadastro = (Button) findViewById(R.id.cad_end);
        mBtnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }


    public void cadastrar() {
        capturaTextos();
        if (isCamposValidos()) {
            Endereco endereco = criarEndereco();
            SessaoCadastro.instance.setEndereco(endereco);
            Intent finalIntent = new Intent(CadastroEnderecoActivity.this, FinalizaCadastroActivity.class);
            startActivity(finalIntent);
        }
    }

    public void findEditTexts() {

        mLogradouro = (EditText) findViewById(R.id.logradouro);
        mNumero = (EditText) findViewById(R.id.numero);
        mCep = (EditText) findViewById(R.id.cep);
        mUf = (EditText) findViewById(R.id.uf);
        mBairro = (EditText) findViewById(R.id.bairro);
        mCidade = (EditText) findViewById(R.id.cidade);
        mComplemento = (EditText) findViewById(R.id.complemento);
        //mLatitude = (EditText) findViewById(R.id.latitude);
        //mLongitude = (EditText) findViewById(R.id.longitude);
    }

    public void capturaTextos() {
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

        if (isCampoVazio(logradouro)) {
            mLogradouro.setError("Campo vazio");
            focusView = mLogradouro;
            res = false;
        } else if (isCampoVazio(numero)) {
            mNumero.setError("Campo vazio");
            focusView = mNumero;
            res = false;
        } else if (isCampoVazio(cep)) {
            mCep.setError("Campo Vazio");
            focusView = mCep;
            res = false;
        } else if (isCampoVazio(uf)) {
            mUf.setError("Campo vazio");
            focusView = mUf;
            res = false;
        } else if (isCampoVazio(bairro)) {
            focusView = mBairro;
            mBairro.setError("Campo vazio");
            res = false;
        } else if (isCampoVazio(cidade)) {
            focusView = mCidade;
            mCidade.setError("Campo Vazio");
            res = false;
        } else if (isCampoVazio(complemento)) {
            focusView = mComplemento;
            mComplemento.setError("Campo Vazio");
        }
        if (!res) {
            focusView.requestFocus();
        }

        return res;
    }

    private boolean isCampoVazio(String valor) {
        boolean resultado = TextUtils.isEmpty(valor) || valor.trim().isEmpty();
        return resultado;
    }

    public Endereco criarEndereco() {
        Endereco endereco = new Endereco();
        try {
            endereco.setNumero(Long.parseLong(numero));
        } catch (Exception e) {
            isCamposValidos();
        }
        endereco.setLogradouro(logradouro);
        endereco.setCep(cep);
        endereco.setUf(uf);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setComplemento(complemento);
        return endereco;
    }


}


