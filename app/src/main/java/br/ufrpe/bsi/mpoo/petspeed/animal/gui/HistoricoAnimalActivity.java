package br.ufrpe.bsi.mpoo.petspeed.animal.gui;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.RecyclerViewAdapterAnimalHistorico;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.RecyclerViewClickListener;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;


public class HistoricoAnimalActivity extends AppCompatActivity  {
    public static Animal animal;
    private OrdemServicoServices ordemServicoServices = new OrdemServicoServices();
    private RecyclerViewAdapterAnimalHistorico adapterAnimalHistorico;
    private List<OrdemServico> osArrayList = new ArrayList<>();
    private CoordinatorLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_animal);
        setTitle("PetSpeed");
        listaOs();
        initRecyclerView();
        FloatingActionButton fab = findViewById(R.id.fab_add_animal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(HistoricoAnimalActivity.this, <criarAtendimento>.class));
                finishAndRemoveTask();
            }
        });

    }



    public void listaOs() {
        osArrayList = ordemServicoServices.getOSbyAnimal(animal);
    }

    public void initRecyclerView() {
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        };

        if (osArrayList.size() == 0) {
            Toast.makeText(HistoricoAnimalActivity.this, "Nenhum antedimento registrado.", Toast.LENGTH_LONG).show();

        }

        else{
            RecyclerView recyclerView = findViewById(R.id.recycler_view_animal_historico);
            rootLayout = findViewById(R.id.rootLayout);
            adapterAnimalHistorico = new RecyclerViewAdapterAnimalHistorico(this, osArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapterAnimalHistorico);
            //ItemTouchHelper.SimpleCallback callback =
            //      new SwipeContoller(0, ItemTouchHelper.LEFT, this);
            // new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);
        }

    }


}

