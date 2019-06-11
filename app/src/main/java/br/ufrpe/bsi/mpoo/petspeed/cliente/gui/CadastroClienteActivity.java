package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

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
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.LoginActivity;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoCadastro;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.gui.CadastroEnderecoActivity;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class CadastroClienteActivity extends AppCompatActivity {

    private static final String CAMPO_VAZIO = "Campo vazio";
    private EditText mNome;
    private EditText mEmail;
    private EditText mSenha;
    private EditText mcmfSenha;
    private EditText mTelefone;
    private EditText mCpf;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String cmfSenha;
    private String telefone;
    private TextView mTextBkHome;
    private ClienteServices clienteServices = new ClienteServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cadastro_cliente);

        mTextBkHome = findViewById(R.id.ActCadastroTxVwBackHome);

        mTextBkHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(CadastroClienteActivity.this, LoginActivity.class);
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

    private void cadastrar() {
        capturaTextos();
        Cliente cliente = criarCliente();
        cliente.setUsuario(criarUsuario());
        cliente.setDadosPessoais(criarPessoa());
        boolean res = clienteServices.usuarioPossuiCliente(cliente);
        if (!res) {
            if (isCamposValidos()) {
                SessaoCadastro.instance.setCliente(cliente);
                SessaoCadastro.instance.setTipo(ContasDeUsuario.CLIENTE);
                Intent registerEnd = new Intent(CadastroClienteActivity.this, CadastroEnderecoActivity.class);
                startActivity(registerEnd);
            }
        } else {
            Toast.makeText(CadastroClienteActivity.this, "Por favor, verifique os campos.", Toast.LENGTH_SHORT).show();
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
    }

    public void findEditTexts() {

        mNome = findViewById(R.id.username);
        mTelefone = findViewById(R.id.clienteTelefone);
        mCpf = findViewById(R.id.cpf);
        mEmail = findViewById(R.id.email);
        mSenha = findViewById(R.id.passwd);
        mcmfSenha = findViewById(R.id.cnfpasswd);
        mTextBkHome = findViewById(R.id.ActCadastroTxVwBackHome);

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

        if (isCampoVazio(nome)) {
            mNome.setError(CAMPO_VAZIO);
            focusView = mNome;
            res = false;
        } else if (isCampoVazio(telefone)) {
            mTelefone.setError(CAMPO_VAZIO);
            focusView = mTelefone;
            res = false;
        } if (telefone.length()!=10) {
            mTelefone.setError("telefone inválido");
            focusView = mTelefone;
            res = false;
        } else if (isCampoVazio(cpf)) {
            mCpf.setError(CAMPO_VAZIO);
            focusView = mCpf;
            res = false;
        } else if (!isEmailValido(email)) {
            mEmail.setError("Email inválido");
            focusView = mEmail;
            res = false;
        } else if (isCampoVazio(senha)) {
            mSenha.setError(CAMPO_VAZIO);
            focusView = mSenha;
            res = false;
        } else if (isCampoVazio(cmfSenha)) {
            focusView = mcmfSenha;
            mcmfSenha.setError(CAMPO_VAZIO);
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

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setAvaliacao(5);
        cliente.setTelefone(telefone);
        return cliente;
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
        mTelefone.setText("");
        mSenha.setText("");
        mcmfSenha.setText("");
        mEmail.setText("");
        mCpf.setText("");

    }

}
