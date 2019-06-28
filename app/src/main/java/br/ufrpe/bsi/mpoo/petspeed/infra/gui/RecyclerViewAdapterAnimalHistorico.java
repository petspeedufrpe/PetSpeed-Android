package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class RecyclerViewAdapterAnimalHistorico  extends RecyclerView.Adapter<RecyclerViewAdapterAnimalHistorico.MyViewHolder> {

    private Context mContext;
    private List<OrdemServico> mServico;


    public RecyclerViewAdapterAnimalHistorico(Context mContext, List<OrdemServico> mServico) {
        this.mContext = mContext;
        this.mServico = mServico;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_animal_historico, viewGroup, false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.status.setText("Status: " + mServico.get(i).getStatus().getDescricao().replace("_"," "));
        holder.nomeMedico.setText("Medico Atendimento: " + mServico.get(i).getMedico().getDadosPessoais().getNome());
        holder.descricao.setText("Descrição: " + String.valueOf(mServico.get(i).getDescricao()));
        //holder.idadeAnimal.setText("Idade: " + String.valueOf(mServico.get(i).getNascimento()));



    }

    public int getItemCount() {
        return mServico.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;
        private TextView status;
        private TextView nomeMedico;
        private TextView descricao;
        private TextView idadeAnimal;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            findTexts();
        }

        private void findTexts() {
            status = itemView.findViewById(R.id.txt_status);
            nomeMedico = itemView.findViewById(R.id.txt_nome_medico);
            descricao = itemView.findViewById(R.id.txt_descricao);
            //idadeAnimal = itemView.findViewById(R.id.txt_idade_animal);
        }
    }
}
