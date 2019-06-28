package br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.iCheckedChangeListener;

public class AdapterSintomasAnimal extends RecyclerView.Adapter<AdapterSintomasAnimal.MyViewHolder> {

    private Context mContext;
    private List<Sintomas> sintomas;
    private List<Sintomas> checked;
    private List<Boolean> auxCheckedState = new ArrayList<>();

    public AdapterSintomasAnimal(Context context,List<Sintomas> sintomas,List<Sintomas> checked){
        this.mContext = context;
        this.sintomas = sintomas;
        this.checked = checked;
    }


    @NonNull
    @Override
    public AdapterSintomasAnimal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        fillCheckedState();
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.relative_layout_selecionar_sintomas,viewGroup,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterSintomasAnimal.MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(String.valueOf(sintomas.get(i).getDescricao()));
        final Sintomas mSintomas = sintomas.get(i);
        if (auxCheckedState.get(i)){
            myViewHolder.checkBox.setChecked(true);
            checked.add(mSintomas);
        }else{
            myViewHolder.checkBox.setChecked(false);
            checked.remove(mSintomas);
        }
        myViewHolder.setICheckedChangeListener(new iCheckedChangeListener() {
            @Override
            public void onItemChecked(int position, boolean value) {
                auxCheckedState.set(position,value);
                if (auxCheckedState.get(position).equals(true)){
                    checked.add(sintomas.get(position));
                } else{
                    checked.remove(sintomas.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sintomas != null ? sintomas.size():0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private CheckBox checkBox;
        private iCheckedChangeListener checkedChangeListener;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title =  itemView.findViewById(R.id.txt_sintoma_animal);
            checkBox =  itemView.findViewById(R.id.check_sintoma_animal);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkedChangeListener.onItemChecked(getAdapterPosition(),isChecked);
                }
            });
        }

        void setICheckedChangeListener(iCheckedChangeListener checkedChangeListener){
            this.checkedChangeListener = checkedChangeListener;
        }
    }

    private void fillCheckedState(){
        for (int i=0;i<=sintomas.size();i++){
            auxCheckedState.add(false);
        }
    }
}
