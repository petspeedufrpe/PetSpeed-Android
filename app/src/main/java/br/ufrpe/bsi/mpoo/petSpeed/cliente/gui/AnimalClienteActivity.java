package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.animal.gui.CrudAnimalActivity;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.infra.gui.RecyclerViewAdapterAnimalCliente;
import br.ufrpe.bsi.mpoo.petSpeed.infra.gui.adapter.AdapterMeuPet;
//import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AnimalClienteService;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ControleMeusPets;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.RecyclerViewClickListener;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class AnimalClienteActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager mLayoutManager;
    private ClienteServices clienteServices = new ClienteServices();
    private ArrayList<Animal> animalArrayList = new ArrayList<>();
    private Usuario usuario = Sessao.instance.getUsuario();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
    private ListView listaAnimal;
    //private AnimalClienteService animalClienteService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createAllAnimals();
        initRecyclerView();
        setTitle("PetSpeed");
        FloatingActionButton fab = findViewById(R.id.fab_add_animal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnimalClienteActivity.this, CrudAnimalActivity.class));
            }
        });
    }


    private void preencherArray() {
        ClienteServices clienteServices = new ClienteServices();
        long idCliente = Sessao.instance.getCliente().getId();
        ArrayList<Animal> petsCliente = clienteServices.getAllAnimalByIdCliente(idCliente);
        final AdapterMeuPet adapter = new AdapterMeuPet(this, petsCliente);
        listaAnimal.setAdapter(adapter);

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
