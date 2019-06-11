package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.RecyclerViewClickListener;


//Classe para renderizar os n Animais da ArrayList para n CardViews no Layout
public class RecyclerViewAdapterAnimalCliente extends RecyclerView.Adapter<RecyclerViewAdapterAnimalCliente.MyViewHolder> {

    private RecyclerViewClickListener listener;
    private Context mContext;
    private List<Animal> mAnimals;


    public RecyclerViewAdapterAnimalCliente(Context mContext, List<Animal> mAnimals, RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.mAnimals = mAnimals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_animal_cliente,viewGroup,false);
        return new MyViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.nomeAnimal.setText(mAnimals.get(i).getNome());
        myViewHolder.racaAnimal.setText(mAnimals.get(i).getRaca());
        myViewHolder.pesoAnimal.setText(String.valueOf(mAnimals.get(i).getPeso()));
        myViewHolder.idadeAnimal.setText(String.valueOf(mAnimals.get(i).getNascimento()));
    }

    @Override
    public int getItemCount() {
        return mAnimals.size();
    }


    //Classe que seta o evento Onclick(recuperando a posicao do cardView)
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;

        private TextView nomeAnimal;
        private TextView racaAnimal;
        private TextView pesoAnimal;
        private TextView idadeAnimal;
        ImageView imageAnimal;

        public MyViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            findTexts();
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v,getAdapterPosition());
        }

        public void findTexts(){
            nomeAnimal = itemView.findViewById(R.id.txt_nome_animal);
            racaAnimal = itemView.findViewById(R.id.txt_raca_animal);
            pesoAnimal = itemView.findViewById(R.id.txt_peso_animal);
            idadeAnimal = itemView.findViewById(R.id.txt_idade_animal);
            imageAnimal = itemView.findViewById(R.id.image_animal);

        }

    }


}

