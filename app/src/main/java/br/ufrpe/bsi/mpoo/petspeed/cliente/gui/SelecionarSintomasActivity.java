package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.Arrays;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter.AdapterSintomasAnimal;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;

public class SelecionarSintomasActivity extends AppCompatActivity {

    RecyclerView recyclerViewSintomas;
    private List<Sintomas> sintomas;
    private AdapterSintomasAnimal adapterSintomasAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_sintomas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createAllSintomas();
        setupRecyler();

    }


    public void createAllSintomas(){
        sintomas = Arrays.asList(Sintomas.values());
    }

    private void setupRecyler(){

        if (sintomas!= null){
            recyclerViewSintomas = findViewById(R.id.selecionar_sintomas_recycler_view);
            adapterSintomasAnimal = new AdapterSintomasAnimal(this,sintomas);
            recyclerViewSintomas.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewSintomas.setAdapter(adapterSintomasAnimal);

            recyclerViewSintomas.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        }
    }

}
