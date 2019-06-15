package br.ufrpe.bsi.mpoo.petspeed.animal.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;

public class PerfilPetActivity extends AppCompatActivity {

    public static Animal animal;
    private Cliente cliente = Sessao.instance.getCliente();
    private TextView mNome, mRaca, mPeso;
    private ImageView mFoto;
    private FloatingActionButton editDados;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_pet);
        findTexts();
        showTexts(bitmap);
        editDados = findViewById(R.id.fab_edit);
        editDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PerfilPetActivity.this, EditDadosPetActivity.class);
                EditDadosPetActivity.animal = animal;
                startActivity(it);
                //finish();
            }
        });
        convertByteInBitMap();
    }


    public void findTexts() {
        mNome = findViewById(R.id.campo_altera_nome_animal);
        mRaca = findViewById(R.id.campo_altera_raca_animal);
        mPeso = findViewById(R.id.campo_altera_peso_animal);
        mFoto = findViewById(R.id.campo_imagem);
    }

    public void showTexts(Bitmap bitmap) {
        if (animal.getFkCliente() == cliente.getId()) {
            mNome.setText(animal.getNome());
            mRaca.setText(animal.getRaca());
            mPeso.setText(String.valueOf(animal.getPeso()));
            mFoto.setImageBitmap(bitmap);
        }
    }

    public void convertByteInBitMap() {
        try {
            byte[] imagemEmBits = animal.getFoto();
            bitmap = BitmapFactory.decodeByteArray(imagemEmBits, 0, imagemEmBits.length);
            mFoto.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.d("Minha tag", editDados.toString());
            Toast.makeText(this, "Voce nao possui foto", Toast.LENGTH_SHORT).show();
        }

    }
}
