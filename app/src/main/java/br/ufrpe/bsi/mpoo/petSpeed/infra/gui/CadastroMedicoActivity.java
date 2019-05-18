package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.SessaoCadastro;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class CadastroMedicoActivity extends AppCompatActivity {
    EditText mNome, mCpf, mEmail, mSenha, mcmfSenha, mCrmv;
    String nome, cpf, email, senha, cmfSenha, crmv;
    Button mButtoRegister;
    TextView mTextHome;



    MedicoServices medicoServices = new MedicoServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_medico);

        mTextHome = findViewById(R.id.cdstrMedicoHome);

        mTextHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(CadastroMedicoActivity.this, LoginActivity.class);
                startActivity(homeIntent);

            }
        });

        mButtoRegister = findViewById(R.id.register);

        mButtoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }

        });
    }

    public void cadastrar() {
        capturaTextos();
        Medico medico = criarMedico();
        medico.setUsuario(criarUsuario());
        medico.setDadosPessoais(criarPessoa());
        boolean res = medicoServices.usuarioPossuiMedico(medico);
        if (!res) {
            if(isCamposValidos()){
                Intent registerEnd = new Intent(CadastroMedicoActivity.this, CadastroEnderecoActivity.class);
                SessaoCadastro.instance.setMedico(medico);
                SessaoCadastro.instance.setTipo(ContasDeUsuario.MEDICO);
                startActivity(registerEnd);
            }
        } else {
            Toast.makeText(CadastroMedicoActivity.this, "Ops! Algo parece estar errado. Verifique seus dados.", Toast.LENGTH_SHORT).show();
        }
    }

    public void capturaTextos() {
        findEditTexts();
        nome = mNome.getText().toString().trim();
        cpf = mCpf.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        senha = mSenha.getText().toString().trim();
        cmfSenha = mcmfSenha.getText().toString().trim();
        crmv = mCrmv.getText().toString().trim();
    }

    public void findEditTexts() {
        mCrmv = (EditText) findViewById(R.id.crmv);
        mNome = (EditText) findViewById(R.id.username);
        mCpf = (EditText) findViewById(R.id.cpf);
        mEmail = (EditText) findViewById(R.id.email);
        mSenha = (EditText) findViewById(R.id.passwd);
        mcmfSenha = (EditText) findViewById(R.id.cnfpasswd);
        mTextHome = (TextView) findViewById(R.id.cdstrMedicoHome);

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
        mCrmv.setError(null);

        if (isCampoVazio(nome)) {
            mNome.setError("Campo vazio");
            focusView = mNome;
            res = false;
        } else if (isCampoVazio(cpf)) {
            mCpf.setError("Campo vazio");
            focusView = mCpf;
            res = false;
        } else if (isCampoVazio(crmv)) {
            mCrmv.setError("Campo vazio");
            focusView = mCrmv;
            res = false;
        } else if (!isEmailValido(email)) {
            mEmail.setError("Email inválido");
            focusView = mEmail;
            res = false;
        } else if (isCampoVazio(senha)) {
            mSenha.setError("Campo vazio");
            focusView = mSenha;
            res = false;
        } else if (isCampoVazio(cmfSenha)) {
            focusView = mcmfSenha;
            mcmfSenha.setError("Campo vazio");
            res = false;
        }
        if (!isSenhasIguais(senha, cmfSenha)) {
            Toast.makeText(this, "Senhas Devem ser iguais", Toast.LENGTH_SHORT).show();
            focusView = mcmfSenha;
            res = false;
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

    private boolean isEmailValido(String email) {
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

    private boolean isSenhasIguais(String senha, String cmfSenha) {
        return ((senha.equals(cmfSenha)));
    }

    private Medico criarMedico() {
        Medico medico = new Medico();
        medico.setAvaliacao(5);
        medico.setCrmv(crmv);
        return medico;
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setEmail(email);

        return usuario;
    }

    private Pessoa criarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf(cpf);
        pessoa.setNome(nome);

        return pessoa;
    }

    private void limparCampos() {
        mNome.setText("");
        mSenha.setText("");
        mcmfSenha.setText("");
        mEmail.setText("");
        mCpf.setText("");
        mCrmv.setText("");

    }

}
