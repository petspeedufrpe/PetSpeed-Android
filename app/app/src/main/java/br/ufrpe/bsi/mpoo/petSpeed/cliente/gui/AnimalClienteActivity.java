package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.gui.CrudAnimalActivity;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petspeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.RecyclerViewAdapterAnimalCliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.RecyclerViewClickListener;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class AnimalClienteActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager mLayoutManager;
    private ClienteServices clienteServices = new ClienteServices();
    private ArrayList<Animal> animalArrayList = new ArrayList<>();
    private Usuario usuario = Sessao.instance.getUsuario();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createAllAnimals();
        initRecyclerView();
        FloatingActionButton fab = findViewById(R.id.fab_add_animal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnimalClienteActivity.this, CrudAnimalActivity.class));
            }
        });
    }

    public void createAllAnimals(){
        cliente = clienteServices.getClienteCompleto(cliente.getId());
        animalArrayList = clienteServices.getAllAnimalByIdCliente(cliente.getId());
    }


    public void initRecyclerView(){
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        };
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_animal_cliente);
        if (animalArrayList != null){
            RecyclerViewAdapterAnimalCliente adapterAnimalCliente = new RecyclerViewAdapterAnimalCliente(this,animalArrayList,listener);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.hasFixedSize();
            recyclerView.setAdapter(adapterAnimalCliente);
        }


    }
}
