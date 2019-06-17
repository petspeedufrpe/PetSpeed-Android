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

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;

public class AdapterSintomasAnimal extends RecyclerView.Adapter<AdapterSintomasAnimal.MyViewHolder> {

    private Context mContext;
    private List<Sintomas> sintomas;
    private List<Sintomas> checked;

    public AdapterSintomasAnimal(Context context,List<Sintomas> sintomas,List<Sintomas> checked){
        this.mContext = context;
        this.sintomas = sintomas;
        this.checked = checked;
    }


    @NonNull
    @Override
    public AdapterSintomasAnimal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.relative_layout_selecionar_sintomas,viewGroup,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterSintomasAnimal.MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(String.valueOf(sintomas.get(i).getDescricao()));
        final Sintomas mSintomas = sintomas.get(i);
        myViewHolder.checkBox.setOnCheckedChangeListener(null);
        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checked.add(mSintomas);
                }else{
                    checked.remove(mSintomas);
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

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title =  itemView.findViewById(R.id.txt_sintoma_animal);
            checkBox =  itemView.findViewById(R.id.check_sintoma_animal);
        }
    }
}
