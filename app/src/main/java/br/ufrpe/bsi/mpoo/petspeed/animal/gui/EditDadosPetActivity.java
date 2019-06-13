package br.ufrpe.bsi.mpoo.petspeed.animal.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;

public class EditDadosPetActivity extends AppCompatActivity {

    public static Animal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dados_pet);
    }
}
