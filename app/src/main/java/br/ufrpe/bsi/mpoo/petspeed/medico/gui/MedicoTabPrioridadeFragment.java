package br.ufrpe.bsi.mpoo.petspeed.medico.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.OSAdapter;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;

public class MedicoTabPrioridadeFragment extends Fragment {
    private OSAdapter osAdapter;
    private List<OrdemServico> os;
    private TextView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medico_tab_prioridade, container, false);
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
            osAdapter = new OSAdapter(getContext(),os);
        } else {
            view.setVisibility(View.VISIBLE);
        }

    }

    public boolean createAllOs(){
        boolean result;
        OrdemServicoServices ordemServicoServices= new OrdemServicoServices();
        try{
            os = ordemServicoServices.getOSbyPrioridade(Sessao.instance.getMedico().getId(),OrdemServico.Prioridade.ALTA);
            List<OrdemServico> osBaixa = ordemServicoServices.getOSbyPrioridade(Sessao.instance.getMedico().getId(), OrdemServico.Prioridade.BAIXA);
            os.addAll(osBaixa);
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
