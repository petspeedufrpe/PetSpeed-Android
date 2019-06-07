package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;

public class PerfilClienteActivity extends AppCompatActivity {
    private TextView mEmail,mTelefone,mCidade,mEndereco;
    private android.support.v7.widget.Toolbar mNomeUsuario;
    private ImageView mFoto;
    private String email,nome,telefone,cidade;
    private FloatingActionButton editDados;
    private Cliente cliente = Sessao.instance.getCliente();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);
        findTexts();
        showTexts();
        editDados = (FloatingActionButton) findViewById(R.id.fab_edit);
        editDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilClienteActivity.this,EditDadosClienteActivity.class));
                finish();
            }
        });
    }

    public void setTexts(){
        findTexts();
        email = mEmail.getText().toString().trim();
        telefone = mTelefone.getText().toString().trim();
        cidade = mCidade.getText().toString().trim();
    }

    public void findTexts(){
        editDados = (FloatingActionButton) findViewById(R.id.fab_edit);
        mEmail = (TextView) findViewById(R.id.campo_altera_nome_cliente);
        mTelefone = (TextView) findViewById(R.id.campo_altera_telefone_cliente);
        mCidade = (TextView) findViewById(R.id.campo_altera_email_cliente);
        mFoto = (ImageView) findViewById(R.id.campo_imagem);
        mEndereco = (TextView) findViewById(R.id.lbl_show_endereco);
        mNomeUsuario = (Toolbar) findViewById(R.id.toolbar_nome_do_usuario);


    }

    public void showTexts(){
        mEmail.setText(cliente.getUsuario().getEmail());
        mCidade.setText(cliente.getDadosPessoais().getEndereco().getCidade());
        mEndereco.setText(formatEndereco());
        mNomeUsuario.setTitle(cliente.getDadosPessoais().getNome());
    }

    public StringBuilder formatEndereco(){
        StringBuilder endFormatted = new StringBuilder();
        String rua = cliente.getDadosPessoais().getEndereco().getLogradouro();
        int numero = (int) cliente.getDadosPessoais().getEndereco().getNumero();
        String compl = cliente.getDadosPessoais().getEndereco().getComplemento();
        String bairro = cliente.getDadosPessoais().getEndereco().getBairro();
        String cidade = cliente.getDadosPessoais().getEndereco().getCidade();
        String uf = cliente.getDadosPessoais().getEndereco().getUf();
        endFormatted.append(rua);
        endFormatted.append(", ");
        endFormatted.append(numero);
        endFormatted.append(", ");
        endFormatted.append(compl);
        endFormatted.append(", ");
        endFormatted.append(bairro);
        endFormatted.append(", ");
        endFormatted.append(cidade);
        endFormatted.append(", ");
        endFormatted.append(uf);

        return endFormatted;
    }
}
