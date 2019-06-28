package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.ListaMedicosAdapter;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.FiltroBuscaMedicos;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.medico.negocio.MedicoServices;

public class FiltroBrasilBuscaMedicoFragment extends Fragment {

    private ListaMedicosAdapter medicosAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filtro_brasil_busca_medico, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setMedicosAdapter();
        initRecyclerView();
    }

    public void setMedicosAdapter() {
        List<Medico> medicosList;
        String medicoNome = (String) Sessao.instance.getValue(FiltroBuscaMedicos.Tipo.NOME.getDescricao());
        MedicoServices medicoServices = new MedicoServices();
        medicosList = medicoServices.getMedicosByNome(medicoNome);

        if(medicosList.isEmpty()){
            Toast.makeText(getContext(),"Sua busca não retornou resultados.",Toast.LENGTH_SHORT).show();
        }
        medicosAdapter = new ListaMedicosAdapter(getContext(),medicosList);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(medicosAdapter);

    }
}
