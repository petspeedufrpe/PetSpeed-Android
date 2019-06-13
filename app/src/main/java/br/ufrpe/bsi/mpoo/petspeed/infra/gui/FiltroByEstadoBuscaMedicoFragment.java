package br.ufrpe.bsi.mpoo.petspeed.infra.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petspeed.R;

public class FiltroByEstadoBuscaMedicoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filtro_by_estado_busca_medico, container, false);

        TextView tv = view.findViewById(R.id.text1);
        tv.setText("ABA ESTADO");

        return view;
    }
}
