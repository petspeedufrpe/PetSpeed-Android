package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter.AdapterSintomasAnimal;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;

public class SelecionarSintomasActivity extends AppCompatActivity {

    RecyclerView recyclerViewSintomas;
    private List<Sintomas> sintomas;
    private List<Sintomas> checked;
    private AdapterSintomasAnimal adapterSintomasAnimal;
    private FloatingActionButton fabConfirmarSintomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_sintomas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fabConfirmarSintomas = (FloatingActionButton) findViewById(R.id.fab_confirmar_sintomas);
        setTitle("Selecione os Sintomas do Animal");
        checked = new ArrayList<>();
        createAllSintomas();
        setupRecyler();

        fabConfirmarSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessaoAgendamento.instance.setSintomas(checked);
                startActivity(new Intent(SelecionarSintomasActivity.this,HomeClienteActivity.class));
                Toast.makeText(getBaseContext(),SessaoAgendamento.instance.getMedico().getDadosPessoais().getNome(),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void createAllSintomas(){
        sintomas = Arrays.asList(Sintomas.values());
    }

    private void setupRecyler(){

        if (sintomas!= null){
            recyclerViewSintomas = findViewById(R.id.selecionar_sintomas_recycler_view);
            adapterSintomasAnimal = new AdapterSintomasAnimal(this,sintomas,checked);
            recyclerViewSintomas.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewSintomas.setAdapter(adapterSintomasAnimal);
            recyclerViewSintomas.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        }
    }

}
