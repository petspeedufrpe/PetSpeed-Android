package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.usuario.persistencia.UsuarioDAO;

public class MudarSenhaAcitivity extends AppCompatActivity {

    private Cliente cliente = Sessao.instance.getCliente();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private EditText mSenhaAntiga;
    private EditText mNovaSenha;
    private EditText mConfirmarNovaSenha;
    private String senhaAntiga;
    private String novaSenha;
    private String confirmaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mudar_senha_acitivity);
        Button mButtoCnfSenha = (Button) findViewById(R.id.btn_cnf_alterar_senha);
        mButtoCnfSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarSenha();
            }
        });
    }

    public void findEditTexts() {

        mSenhaAntiga = (EditText) findViewById(R.id.txt_senha_antiga_alterar_senha);
        mNovaSenha = (EditText) findViewById(R.id.txt_nova_senha_alterar_senha);
        mConfirmarNovaSenha = (EditText) findViewById(R.id.txt_cnf_nova_senha_alterar_senha);

    }

    public void capturaTextos() {
        findEditTexts();
        senhaAntiga = mSenhaAntiga.getText().toString().trim();
        novaSenha = mNovaSenha.getText().toString().trim();
        confirmaSenha = mConfirmarNovaSenha.getText().toString().trim();
    }

    public boolean isSenhaAntigaEquals(String senhaAntiga) {
        String senhaCliente = cliente.getUsuario().getSenha();
        return senhaAntiga.equals(senhaCliente);
    }

    public boolean isSenhaIguais(String novaSenha, String confirmaSenha) {

        return novaSenha.equals(confirmaSenha);
    }


    public boolean validaCampos() {
        capturaTextos();
        boolean result = true;
        View focusView = null;
        if (senhaAntiga.isEmpty()) {
            mSenhaAntiga.setError("Campo vazio");
            focusView = mSenhaAntiga;
            result = false;
        } else if (novaSenha.isEmpty()) {
            mNovaSenha.setError("Campo vazio");
            focusView = mNovaSenha;
            result = false;
        } else if (confirmaSenha.isEmpty()) {
            mConfirmarNovaSenha.setError("Campo Vazio");
            focusView = mConfirmarNovaSenha;
            result = false;
        }

        if (!isSenhaIguais(novaSenha, confirmaSenha)) {
            Toast.makeText(this, "Senhas devem ser iguais", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isSenhaAntigaEquals(senhaAntiga)) {
            Toast.makeText(this, "Senha antiga incorreta", Toast.LENGTH_SHORT).show();
        }
        if (!result) {

            focusView.requestFocus();

        }
        return result;
    }

    public void alterarSenha() {
        if (validaCampos()) {
            cliente.getUsuario().setSenha(novaSenha);
            usuarioDAO.alterarSenha(cliente.getUsuario());
            startActivity(new Intent(MudarSenhaAcitivity.this, PerfilClienteActivity.class));
            Toast.makeText(this, "Senha Alterada com Sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Verifique os dados", Toast.LENGTH_SHORT).show();
        }
    }
}
