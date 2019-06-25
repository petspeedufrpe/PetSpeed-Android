package br.ufrpe.bsi.mpoo.petspeed.medico.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.LoginActivity;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Criptografia;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoCadastro;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.gui.CadastroEnderecoActivity;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class CadastroMedicoActivity extends AppCompatActivity {
    private static final String ERR_MSG_CAMPO_VAZIO = "Campo vazio";
    private EditText mNome;
    private EditText mCpf;
    private EditText mEmail;
    private EditText mSenha;
    private EditText mcmfSenha;
    private EditText mCrmv;
    private EditText mTelefone;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String cmfSenha;
    private String crmv;
    private String telefone;
    private TextView mTextHome;


    MedicoServices medicoServices = new MedicoServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cadastro_medico);

        mTextHome = findViewById(R.id.cdstrMedicoHome);

        mTextHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(CadastroMedicoActivity.this, LoginActivity.class);
                startActivity(homeIntent);

            }
        });

        Button mButtoRegister = findViewById(R.id.register);

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
            if (isCamposValidos()) {
                Intent registerEnd = new Intent(CadastroMedicoActivity.this, CadastroEnderecoActivity.class);
                SessaoCadastro.instance.setMedico(medico);
                SessaoCadastro.instance.setTipo(ContasDeUsuario.MEDICO);
                startActivity(registerEnd);
            }
        } else {
            Toast.makeText(CadastroMedicoActivity.this, "Ops! Algo parece estar errado. Verifique seus dados.", Toast.LENGTH_SHORT).show();
            limparCampos();
        }
    }

    public void capturaTextos() {
        findEditTexts();
        nome = mNome.getText().toString().trim();
        telefone = mTelefone.getText().toString().trim();
        cpf = mCpf.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        senha = mSenha.getText().toString().trim();
        cmfSenha = mcmfSenha.getText().toString().trim();
        crmv = mCrmv.getText().toString().trim();
    }

    public void findEditTexts() {
        mCrmv = findViewById(R.id.crmv);
        mNome = findViewById(R.id.username);
        mTelefone = findViewById(R.id.medicoTelefone);
        mCpf = findViewById(R.id.cpf);
        mEmail = findViewById(R.id.email);
        mSenha = findViewById(R.id.passwd);
        mcmfSenha = findViewById(R.id.cnfpasswd);
        mTextHome = findViewById(R.id.cdstrMedicoHome);

    }

    private boolean isCamposValidos() {
        View focusView = null;
        boolean res = true;
        //reseta os erros
        mNome.setError(null);
        mTelefone.setError(null);
        mCpf.setError(null);
        mSenha.setError(null);
        mEmail.setError(null);
        mcmfSenha.setError(null);
        mCrmv.setError(null);

        if (isCampoVazio(nome)) {
            mNome.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mNome;
            res = false;
        }else if (isCampoVazio(telefone)) {
            mTelefone.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mTelefone;
            res = false;
        }else if (telefone.length()!=10) {
            mTelefone.setError("Telefone inválido");
            focusView = mTelefone;
            res = false;
        } else if (isCampoVazio(cpf)) {
            mCpf.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mCpf;
            res = false;
        } else if (isCampoVazio(crmv)) {
            mCrmv.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mCrmv;
            res = false;
        } else if (!isEmailValido(email)) {
            mEmail.setError("Email inválido");
            focusView = mEmail;
            res = false;
        } else if (isCampoVazio(senha)) {
            mSenha.setError(ERR_MSG_CAMPO_VAZIO);
            focusView = mSenha;
            res = false;
        } else if (isCampoVazio(cmfSenha)) {
            focusView = mcmfSenha;
            mcmfSenha.setError(ERR_MSG_CAMPO_VAZIO);
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
        return TextUtils.isEmpty(valor) || valor.trim().isEmpty();
    }

    private boolean isEmailValido(String email) {
        return (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isSenhasIguais(String senha, String cmfSenha) {
        return (senha.equals(cmfSenha));
    }

    private Medico criarMedico() {
        Medico medico = new Medico();
        medico.setAvaliacao(5);
        medico.setCrmv(crmv);
        medico.setTelefone(telefone);
        return medico;
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setSenha(Criptografia.criptografar(senha));
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
        mTelefone.setText("");
        mSenha.setText("");
        mcmfSenha.setText("");
        mEmail.setText("");
        mCpf.setText("");
        mCrmv.setText("");

    }

}
