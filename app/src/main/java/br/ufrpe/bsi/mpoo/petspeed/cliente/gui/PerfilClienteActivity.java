package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;

public class PerfilClienteActivity extends AppCompatActivity {
    private TextView mEmail;
    private TextView mTelefone;
    private TextView mCidade;
    private TextView mEndereco;
    private android.support.v7.widget.Toolbar mNomeUsuario;
    private ImageView mFoto;
    private FloatingActionButton editDados;
    private Cliente cliente = Sessao.instance.getCliente();
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);
        getWindow().setStatusBarColor(Color.parseColor("#AFDF59"));
        findTexts();
        convertByteInBitMap();
        showTexts(bitmap);
        editDados = findViewById(R.id.fab_edit);
        editDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilClienteActivity.this, EditDadosClienteActivity.class));
                finish();
            }
        });
    }

    private void convertByteInBitMap() {
        try {
            byte[] imagemEmBits = cliente.getFoto();
            bitmap = BitmapFactory.decodeByteArray(imagemEmBits, 0, imagemEmBits.length);
            mFoto.setImageBitmap(bitmap);

        } catch (Exception e) {
            Log.d("Minha Tag", e.toString());
            Toast.makeText(this, "Você não possui foto", Toast.LENGTH_SHORT).show();
        }
    }


    public void findTexts() {
        editDados = findViewById(R.id.fab_edit);
        mEmail = findViewById(R.id.campo_altera_nome_cliente);
        mTelefone = findViewById(R.id.campo_altera_telefone_cliente);
        mCidade = findViewById(R.id.campo_altera_email_cliente);
        mFoto = findViewById(R.id.campo_imagem);
        mEndereco = findViewById(R.id.lbl_show_endereco);
        mNomeUsuario = findViewById(R.id.toolbar_nome_do_usuario);
    }

    public void showTexts(Bitmap bitmap) {
        mEmail.setText(cliente.getUsuario().getEmail());
        mCidade.setText(cliente.getDadosPessoais().getEndereco().getCidade());
        mEndereco.setText(formatEndereco());
        mNomeUsuario.setTitle(cliente.getDadosPessoais().getNome());
        mTelefone.setText("("+cliente.getTelefone().substring(0,2)+")"+cliente.getTelefone().substring(2));
        mFoto.setImageBitmap(bitmap);
    }

    public StringBuilder formatEndereco() {
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
