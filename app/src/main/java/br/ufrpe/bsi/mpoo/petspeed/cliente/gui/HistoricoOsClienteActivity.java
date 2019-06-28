package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter.OsAdapterCliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;

public class HistoricoOsClienteActivity extends AppCompatActivity {
    private OrdemServicoServices ordemServicoServices = new OrdemServicoServices();
    private List<OrdemServico> ordemServicos = new ArrayList<>();
    private OsAdapterCliente osAdapterCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_os_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Historico de Atendimento");
        createAllOsCliente();
        initRecyclerView();

    }

    private void createAllOsCliente(){
        ordemServicos = ordemServicoServices.getAllOsByCliente(Sessao.instance.getCliente());
    }

    private void initRecyclerView(){
        if (ordemServicos != null){
            RecyclerView recyclerView = findViewById(R.id.recycler_view_historico_os);
            osAdapterCliente = new OsAdapterCliente(getBaseContext(),ordemServicos);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(osAdapterCliente);
        } else {
            Toast.makeText(this,"Você não Possui nenhum atendimento",Toast.LENGTH_SHORT).show();
        }

    }

}
