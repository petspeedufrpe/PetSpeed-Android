package br.ufrpe.bsi.mpoo.petspeed.infra.gui;


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
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.FiltroBuscaMedicos;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petspeed.medico.persistencia.MedicoDAO;

public class FiltroByCidadeBuscaMedicoFragment extends Fragment {

    private ListaMedicosAdapter medicosAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtro_by_cidade_busca_medico, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setMedicosAdapter();
        initRecyclerView();
    }

    public void setMedicosAdapter() {
        List<Medico> medicosList;
        String medicoNome = (String)Sessao.instance.getValue(FiltroBuscaMedicos.Tipo.NOME.getDescricao());
        MedicoServices medicoServices = new MedicoServices();
        medicosList = medicoServices.getMedicosByNome(FiltroBuscaMedicos.Cidades.RECIFE);

        medicosAdapter = new ListaMedicosAdapter(medicosList);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(medicosAdapter);

    }
}
