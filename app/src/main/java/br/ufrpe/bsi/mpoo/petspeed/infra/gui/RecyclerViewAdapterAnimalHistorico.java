package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.RecyclerViewClickListener;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class RecyclerViewAdapterAnimalHistorico  extends RecyclerView.Adapter<RecyclerViewAdapterAnimalHistorico.MyViewHolder> {

    private RecyclerViewClickListener listener;
    private Context mContext;
    private List<OrdemServico> mServico;
    private boolean mSelect = false;
    private ArrayList<OrdemServico> selectedItems = new ArrayList<OrdemServico>();
    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            mSelect = true;
            menu.add("Delete");
            return  true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mSelect = false;
            selectedItems.clear();
            notifyDataSetChanged();

        }
    };


    public RecyclerViewAdapterAnimalHistorico(Context mContext, List<OrdemServico> mServico, RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.mServico = mServico;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_animal_cliente, viewGroup, false);
        return new MyViewHolder(view, listener);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.status.setText("Status: " + mServico.get(i).getStatus());
        holder.nomeMedico.setText("Medico Atendimento: " + mServico.get(i).getMedico());
        holder.descricao.setText("Descrição: " + String.valueOf(mServico.get(i).getDescricao()));
        //holder.idadeAnimal.setText("Idade: " + String.valueOf(mServico.get(i).getNascimento()));

        holder.update(mServico.get(i));


    }

    public int getItemCount() {
        return mServico.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private RelativeLayout relativeLayout;
        private TextView status;
        private TextView nomeMedico;
        private TextView descricao;
        private TextView idadeAnimal;

        public MyViewHolder(@NonNull final View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            findTexts();
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }

        private void findTexts() {
            status = itemView.findViewById(R.id.txt_status);
            nomeMedico = itemView.findViewById(R.id.txt_nome_medico);
            descricao = itemView.findViewById(R.id.txt_descricao);
            //idadeAnimal = itemView.findViewById(R.id.txt_idade_animal);


        }

        void  selectItem(OrdemServico ordemServico){
            if (mSelect){
                if (selectedItems.contains(ordemServico)){
                    selectedItems.remove(ordemServico);
                }else{
                    selectedItems.add(ordemServico);
                }
            }
        }

        void update(final OrdemServico ordemServico){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((AppCompatActivity) v.getContext()).startSupportActionMode(actionModeCallbacks);
                    selectItem(ordemServico);
                    return true;
                }
            });
        }

    }
}
