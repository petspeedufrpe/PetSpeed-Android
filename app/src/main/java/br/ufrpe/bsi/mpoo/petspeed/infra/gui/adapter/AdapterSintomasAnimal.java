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
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;

public class AdapterSintomasAnimal extends RecyclerView.Adapter<AdapterSintomasAnimal.MyViewHolder> {

    private Context mContext;
    private List<Sintomas> sintomas;

    public AdapterSintomasAnimal(Context context,List<Sintomas> sintomas){
        this.mContext = context;
        this.sintomas = sintomas;
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
        myViewHolder.title.setText(String.valueOf(sintomas.get(i).name()));

        myViewHolder.checkBox.setChecked(sintomas.get(i).getDescricao());
        myViewHolder.checkBox.setTag(i);
        myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) myViewHolder.checkBox.getTag();
                Toast.makeText(mContext,(sintomas.get(pos).name())+"Clicked",Toast.LENGTH_SHORT).show();

                if (sintomas.get(pos).getDescricao()){
                    sintomas.get(pos).setDescricao(false);
                } else{
                    sintomas.get(pos).setDescricao(true);
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
