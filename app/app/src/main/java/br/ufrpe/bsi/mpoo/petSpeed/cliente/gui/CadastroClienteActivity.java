package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

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

import java.util.HashMap;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.gui.LoginActivity;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.SessaoCadastro;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.gui.CadastroEnderecoActivity;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class CadastroClienteActivity extends AppCompatActivity {

    private final Map<String, Object> values = new HashMap<>();
    EditText mNome, mCpf, mEmail, mSenha, mcmfSenha;
    String nome, cpf, email, senha, cmfSenha;
    Button mButtoRegister;
    TextView mTextBkHome;
    ClienteServices clienteServices = new ClienteServices();

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

        mButtoRegister = findViewById(R.id.register);

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
        cpf = mCpf.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        senha = mSenha.getText().toString().trim();
        cmfSenha = mcmfSenha.getText().toString().trim();
    }

    public void findEditTexts() {

        mNome = (EditText) findViewById(R.id.username);
        mCpf = (EditText) findViewById(R.id.cpf);
        mEmail = (EditText) findViewById(R.id.email);
        mSenha = (EditText) findViewById(R.id.passwd);
        mcmfSenha = (EditText) findViewById(R.id.cnfpasswd);
        mTextBkHome = (TextView) findViewById(R.id.ActCadastroTxVwBackHome);

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

        if (isCampoVazio(nome)) {
            mNome.setError("Campo vazio");
            focusView = mNome;
            res = false;
        } else if (isCampoVazio(cpf)) {
            mCpf.setError("Campo vazio");
            focusView = mCpf;
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

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setAvaliacao(5);
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
        mSenha.setText("");
        mcmfSenha.setText("");
        mEmail.setText("");
        mCpf.setText("");

    }

}
