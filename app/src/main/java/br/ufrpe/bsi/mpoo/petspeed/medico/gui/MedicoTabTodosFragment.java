package br.ufrpe.bsi.mpoo.petspeed.medico.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.OSAdapter;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;

public class MedicoTabTodosFragment extends Fragment {

    private OSAdapter osAdapter;
    private List<OrdemServico> os;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medico_tab_todos, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setMedicosAdapter();
        initRecyclerView();
    }

    public void setMedicosAdapter() {
        createAllOs();
        osAdapter = new OSAdapter(os);
    }

    public void createAllOs(){
        OrdemServicoServices ordemServicoServices= new OrdemServicoServices();
        os = ordemServicoServices.getOsbyIdMedico(Sessao.instance.getMedico().getId());

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(osAdapter);

    }
}
