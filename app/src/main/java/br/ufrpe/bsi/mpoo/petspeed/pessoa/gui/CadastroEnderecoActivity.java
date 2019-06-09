package br.ufrpe.bsi.mpoo.petspeed.pessoa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.FinalizaCadastroActivity;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoCadastro;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Endereco;

public class CadastroEnderecoActivity extends AppCompatActivity {
    public static final String ERR_MSG_CAMPO_VAZIO = "Campo vazio";
    Button mBtnCadastro;
    private EditText mLogradouro, mNumero, mCep, mUf, mBairro, mCidade, mComplemento;
    private String logradouro, numero, cep, uf, bairro, cidade, complemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cadastro_endereco);

        mBtnCadastro = findViewById(R.id.cad_end);
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

        mLogradouro = findViewById(R.id.logradouro);
        mNumero = findViewById(R.id.numero);
        mCep = findViewById(R.id.cep);
        mUf = findViewById(R.id.uf);
        mBairro = findViewById(R.id.bairro);
        mCidade = findViewById(R.id.cidade);
        mComplemento = findViewById(R.id.complemento);
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
            mLogradouro.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mLogradouro;
            res = false;
        } else if (isCampoVazio(numero)) {
            mNumero.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mNumero;
            res = false;
        } else if (isCampoVazio(cep)) {
            mCep.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mCep;
            res = false;
        } else if (isCampoVazio(uf)) {
            mUf.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mUf;
            res = false;
        } else if (isCampoVazio(bairro)) {
            focusView = mBairro;
            mBairro.setError(ERR_MSG_CAMPO_VAZIO);
            res = false;
        } else if (isCampoVazio(cidade)) {
            focusView = mCidade;
            mCidade.setError(ERR_MSG_CAMPO_VAZIO);
            res = false;
        } else if (isCampoVazio(complemento)) {
            focusView = mComplemento;
            mComplemento.setError(ERR_MSG_CAMPO_VAZIO);
        }
        if (!res) {
            focusView.requestFocus();
        }

        return res;
    }

    private boolean isCampoVazio(String valor) {
        return TextUtils.isEmpty(valor) || valor.trim().isEmpty();
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


