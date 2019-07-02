package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter.AdapterSintomasOs;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemXsintomaDAO;

public class ViewSintomasAnimalAcitivity extends AppCompatActivity {

    RecyclerView recyclerViewSintomas;
    private List<Sintomas> sintomas;
    private Context mContext = getBaseContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sintomas_animal_acitivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createAllSintomas();
        initRecylerView();
    }

    private void initRecylerView() {
        if (sintomas!= null){
            recyclerViewSintomas = findViewById(R.id.recyclerViewSintomasOs);
            AdapterSintomasOs adapterSintomasOs = new AdapterSintomasOs(this, sintomas);
            recyclerViewSintomas.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
            recyclerViewSintomas.setAdapter(adapterSintomasOs);
        }
    }

    private void createAllSintomas() {
        OrdemServico ordemServico = Sessao.instance.getOs();
        TriagemXsintomaDAO triagemXsintomaDAO = new TriagemXsintomaDAO();
        sintomas = triagemXsintomaDAO.getAllSintomasByIdTriagem(ordemServico.getTriagem().getId());
        if (sintomas.isEmpty()) {
            sintomas = SessaoAgendamento.instance.getSintomas();
        }
    }

    private void changeViewByTypeOfAccount(){

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Sessao.instance.setOs(null);
    }
}
