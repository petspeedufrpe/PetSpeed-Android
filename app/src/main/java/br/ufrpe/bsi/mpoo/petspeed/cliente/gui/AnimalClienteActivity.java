package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.gui.CrudAnimalActivity;
import br.ufrpe.bsi.mpoo.petspeed.animal.gui.EditDadosPetActivity;
import br.ufrpe.bsi.mpoo.petspeed.animal.gui.PerfilPetActivity;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.RecyclerViewAdapterAnimalCliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter.AdapterMeuPet;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.RecyclerViewClickListener;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class AnimalClienteActivity extends AppCompatActivity {

    private ClienteServices clienteServices = new ClienteServices();
    private List<Animal> animalArrayList = new ArrayList<>();
    private Usuario usuario = Sessao.instance.getUsuario();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
    private ListView listaAnimal;
    private final Context mContext;

    public AnimalClienteActivity(Context mContext) {
        this.mContext = mContext;
    }


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
        //método pertencente à uma funcionalidade que ainda está sendo implementada.
        long idCliente = Sessao.instance.getCliente().getId();
        List<Animal> petsCliente = clienteServices.getAllAnimalByIdCliente(idCliente);
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
                Intent it = new Intent(mContext, PerfilPetActivity.class);
                PerfilPetActivity.animal = animalArrayList.get(position);
                mContext.startActivity(it);
            }

        };
        RecyclerView recyclerView = findViewById(R.id.recycler_view_animal_cliente);
        if (animalArrayList != null){
            RecyclerViewAdapterAnimalCliente adapterAnimalCliente = new RecyclerViewAdapterAnimalCliente(this,animalArrayList,listener);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.hasFixedSize();
            recyclerView.setAdapter(adapterAnimalCliente);
        }


    }
}
