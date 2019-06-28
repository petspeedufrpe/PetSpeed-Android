package br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class AdapterHistoricoAnimais extends ArrayAdapter<OrdemServico> {
    private final Context contexto;
    private final List<OrdemServico> listaHistorico = new ArrayList<>();

    public AdapterHistoricoAnimais(Context contexto, List<OrdemServico> arrayServicos) {
        super(contexto, R.layout.activity_meus_pets, arrayServicos);

        this.contexto = contexto;
        this.listaHistorico.addAll(arrayServicos);
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_meus_pets, parent, false);

        TextView atendimento_status = rowView.findViewById(R.id.campo_status);
        TextView atendimento_medico = rowView.findViewById(R.id.campo_medico);
        TextView atendimento_descricao = rowView.findViewById(R.id.campo_descricao);


        //atendimento_status.setText(listaHistorico.get(position).getStatus());
        //atendimento_medico.setText(listaHistorico.get(position).getMedico());
        atendimento_descricao.setText(listaHistorico.get(position).getDescricao());

        return rowView;
    }
}
