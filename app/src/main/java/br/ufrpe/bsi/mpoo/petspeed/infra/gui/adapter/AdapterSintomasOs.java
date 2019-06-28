package br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;

public class AdapterSintomasOs extends RecyclerView.Adapter<AdapterSintomasOs.MyViewHolder> {
    private Context mContext;
    private List<Sintomas> list;

    public AdapterSintomasOs(Context context,List<Sintomas> sintomas){
        this.mContext = context;
        this.list = sintomas;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.view_sintoma,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mNameSintoma.setText(list.get(i).getDescricao());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size():0;    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameSintoma;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameSintoma = itemView.findViewById(R.id.name_sintoma);
        }
    }
}
