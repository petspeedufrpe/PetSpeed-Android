package br.ufrpe.bsi.mpoo.petSpeed.animal.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import br.ufrpe.bsi.mpoo.petSpeed.R;

public class CrudAnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crud_animal);
    }
}
