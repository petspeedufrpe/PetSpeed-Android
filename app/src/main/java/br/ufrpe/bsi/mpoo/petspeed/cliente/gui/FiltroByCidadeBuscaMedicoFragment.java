package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.ListaMedicosAdapter;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.FiltroBuscaMedicos;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.medico.negocio.MedicoServices;

public class FiltroByCidadeBuscaMedicoFragment extends Fragment {

    private ListaMedicosAdapter medicosAdapter;
    FiltroBuscaMedicos.Cidades cidadeSelecionada;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtro_by_cidade_busca_medico, container, false);
        Spinner contaSpinner = view.findViewById(R.id.spinner_cidade);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item,
                FiltroBuscaMedicos.Cidades.values());
        contaSpinner.setAdapter(spinnerAdapter);
        contaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                cidadeSelecionada = FiltroBuscaMedicos.Cidades.values()[position];
                setMedicosAdapter(cidadeSelecionada);
                initRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(),"Selecione uma cidade para visualizar os resultados.",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void setMedicosAdapter(FiltroBuscaMedicos.Cidades cidade) {
        List<Medico> medicosList;
        String medicoNome = (String)Sessao.instance.getValue(FiltroBuscaMedicos.Tipo.NOME.getDescricao());
        MedicoServices medicoServices = new MedicoServices();
        medicosList = medicoServices.getMedicosByNome(cidade,medicoNome);

        if(medicosList.isEmpty()){
            Toast.makeText(getContext(),"Sua busca não retornou resultados.",Toast.LENGTH_SHORT).show();
        }
        medicosAdapter = new ListaMedicosAdapter(medicosList);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(medicosAdapter);

    }
}
