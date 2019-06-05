package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petSpeed.R;

public class PerfilClienteActivity extends AppCompatActivity {
    private TextView mEmail,mNome,mTelefone,mCidade;
    private ImageView mFoto;
    private String email,nome,telefone,cidade;
    private FloatingActionButton editDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);
        setTexts();
        editDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LEVA PARA ACT DE EDITAR DADOS DO CLIENTE;
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
        mEmail = (TextView) findViewById(R.id.campo_email);
        mTelefone = (TextView) findViewById(R.id.campo_telefone);
        mCidade = (TextView) findViewById(R.id.campo_peso);
        mFoto = (ImageView) findViewById(R.id.campo_imagem);

    }
}
