package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.LoginActivity;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.MapsFragment;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeClienteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BuscaMedicosFragment.OnFragmentInteractionListener {

    private TextView mNomeCliente;
    private TextView mEmailCliente;
    private Button mAlternaRaio;
    private Double novoRaio = 5.0;
    private int faseRaio = 1;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout_home_cliente);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        setHeaderTexts(headerView);
        mAlternaRaio = findViewById(R.id.buttonRaio);
        mAlternaRaio.setText("Raio atual: 5km");

        mAlternaRaio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsFragment mapsFrag = (MapsFragment)
                        getSupportFragmentManager().findFragmentById(R.id.fragment);
                if (mapsFrag != null) {
                    alternaFaseRaio(mapsFrag);
                }
            }
        });

        setTexts();
        initMapFragment();

    }

    private void setHeaderTexts(View headerView) {
        CircleImageView imageView = headerView.findViewById(R.id.imageViewCliente);
        convertToBitmap();
        if (bmp != null){
            imageView.setImageBitmap(bmp);
        }
        mNomeCliente = headerView.findViewById(R.id.NomeCliente);
        mEmailCliente = headerView.findViewById(R.id.textViewEmailCliente);
    }

    private void convertToBitmap() {
        try {
            bmp = BitmapFactory.decodeByteArray(Sessao.instance.getCliente().getFoto(),
                    0, Sessao.instance.getCliente().getFoto().length);
        } catch (Exception e){
            Log.d("TAG","Erro sem foto");
        }
    }

    @Override
    public void onFragmentInteraction() {
        //a fazer
    }

    private void alternaFaseRaio(MapsFragment mapsFrag) {
        if (faseRaio == 1) {
            mapsFrag.setNovoRaio(novoRaio);
            mAlternaRaio.setText("Raio atual: 5Km");
            Toast.makeText(HomeClienteActivity.this, "Visualizando medicos em um raio de 5km", Toast.LENGTH_SHORT).show();
            novoRaio = 10.0;
            faseRaio = 2;
        } else if (faseRaio == 2) {
            mapsFrag.setNovoRaio(novoRaio);
            mAlternaRaio.setText("Raio atual: 10Km");
            Toast.makeText(HomeClienteActivity.this, "Visualizando medicos em um raio de 10km", Toast.LENGTH_SHORT).show();
            novoRaio = 20.0;
            faseRaio = 3;
        } else if (faseRaio == 3) {
            mapsFrag.setNovoRaio(novoRaio);
            mAlternaRaio.setText("Raio atual: 20Km");
            Toast.makeText(HomeClienteActivity.this, "Visualizando medicos em um raio de 20km", Toast.LENGTH_SHORT).show();
            novoRaio = 5.0;
            faseRaio = 1;
        }
    }

    public void initMapFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.findFragmentById(R.id.fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment, new MapsFragment(), "MapsFragment");
        transaction.commit();
    }


    public void setTexts() {
        Usuario usuario = Sessao.instance.getUsuario();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getIdClienteByUsuario(usuario.getId());
        ClienteServices clienteServices = new ClienteServices();
        cliente = clienteServices.getClienteCompleto(cliente.getId());
        mEmailCliente.setText(usuario.getEmail());
        mNomeCliente.setText(cliente.getDadosPessoais().getNome());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_home_cliente);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil_cliente) {

            startActivity(new Intent(HomeClienteActivity.this, PerfilClienteActivity.class));

        } else if (id == R.id.nav_meus_pets) {
            startActivity(new Intent(HomeClienteActivity.this, AnimalClienteActivity.class));

        } else if (id == R.id.nav_sair_cliente) {
            ClienteServices clienteServices = new ClienteServices();
            clienteServices.logout();
            startActivity(new Intent(HomeClienteActivity.this, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

        } else if (id == R.id.nav_historico_cliente){
            startActivity(new Intent(HomeClienteActivity.this,HistoricoOsClienteActivity.class));

        } else if (id == R.id.nav_atendimento){
            startActivity(new Intent(HomeClienteActivity.this,StatusOsCliente.class));

        } else if (id == R.id.nav_atendimento_emergencial){
            startActivity(new Intent());
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout_home_cliente);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
