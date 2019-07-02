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
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class MedicoTabMinhasOSFragment extends Fragment {
    private OSAdapter osAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medico_tab_minhas_o, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setMedicosAdapter();
        initRecyclerView();
    }

    public void setMedicosAdapter() {
        List<OrdemServico> os = new ArrayList<>();
        osAdapter = new OSAdapter(getContext(),os);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(osAdapter);

    }
}
