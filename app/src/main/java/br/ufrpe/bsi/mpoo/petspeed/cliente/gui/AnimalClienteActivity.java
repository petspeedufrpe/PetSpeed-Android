package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.gui.CrudAnimalActivity;
import br.ufrpe.bsi.mpoo.petspeed.animal.gui.PerfilPetActivity;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.RecyclerViewAdapterAnimalCliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter.AdapterMeuPet;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.RecyclerVIewTouchHelperListener;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.RecyclerViewClickListener;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SwipeContoller;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class AnimalClienteActivity extends AppCompatActivity implements RecyclerVIewTouchHelperListener {

    private ClienteServices clienteServices = new ClienteServices();
    private List<Animal> animalArrayList = new ArrayList<>();
    private Usuario usuario = Sessao.instance.getUsuario();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
    private RecyclerViewAdapterAnimalCliente adapterAnimalCliente;
    private CoordinatorLayout rootLayout;


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
                finishAndRemoveTask();
            }
        });
    }

    private void preencherArray() {
        //método pertencente à uma funcionalidade que ainda está sendo implementada.
        long idCliente = Sessao.instance.getCliente().getId();
        List<Animal> petsCliente = clienteServices.getAllAnimalByIdCliente(idCliente);
        final AdapterMeuPet adapter = new AdapterMeuPet(this, petsCliente);
        //listaAnimal.setAdapter(adapter);
    }


    public void createAllAnimals() {
        cliente = clienteServices.getClienteCompleto(cliente.getId());
        animalArrayList = clienteServices.getAllAnimalByIdCliente(cliente.getId());
    }


    public void initRecyclerView() {
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent it = new Intent(getBaseContext(), PerfilPetActivity.class);
                PerfilPetActivity.animal = animalArrayList.get(position);
                getBaseContext().startActivity(it);
            }
        };

        if (animalArrayList != null) {
            RecyclerView recyclerView = findViewById(R.id.recycler_view_animal_cliente);
            rootLayout = findViewById(R.id.rootLayout);
            adapterAnimalCliente = new RecyclerViewAdapterAnimalCliente(this, animalArrayList, listener);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapterAnimalCliente);
            ItemTouchHelper.SimpleCallback callback =
                    new SwipeContoller(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);

        }

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {

        String name = animalArrayList.get(viewHolder.getAdapterPosition()).getNome();
        final Animal itemDeletado = animalArrayList.get(viewHolder.getAdapterPosition());
        final int deletIndex = viewHolder.getAdapterPosition();
        adapterAnimalCliente.removeItem(deletIndex);
        SnackBarUndoDelete(name, itemDeletado, deletIndex);

    }

    private void SnackBarUndoDelete(String name, final Animal itemDeletado, final int deletIndex) {
        Snackbar snackbar = Snackbar.make(rootLayout, name + " removed", Snackbar.LENGTH_SHORT);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterAnimalCliente.restoreItem(itemDeletado, deletIndex);

            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
}
