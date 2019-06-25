package br.ufrpe.bsi.mpoo.petspeed.medico.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.OSAdapter;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemXsintomaDAO;

public class MedicoTabTodosFragment extends Fragment {

    private OSAdapter osAdapter;
    private List<OrdemServico> os;
    private Triagem triagem;
    private TriagemDAO triagemDAO = new TriagemDAO();
    private TriagemXsintomaDAO triagemXsintomaDAO = new TriagemXsintomaDAO();
    private TextView view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medico_tab_todos, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        view = getActivity().findViewById(R.id.osVazio);
        setMedicosAdapter();
        initRecyclerView();
    }

    public void setMedicosAdapter() {
        if (createAllOs()){
            osAdapter = new OSAdapter(os);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public boolean createAllOs(){
        boolean result;
        OrdemServicoServices ordemServicoServices= new OrdemServicoServices();
        try{
            os = ordemServicoServices.getOsbyIdMedico(Sessao.instance.getMedico().getId());
            triagem = triagemDAO.getTriagembyId(os.get(0).getId());
            List<String> strings = triagemXsintomaDAO.getAllSintomasByIdTriagem(triagem.getId());
            Sintomas sintomas = Sintomas.valueOf(strings.get(0));
            result = true;
        }catch (Exception e){
            return false;
        }
        return result;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(osAdapter);

    }
}
