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
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;


public class AdapterMeuPet extends ArrayAdapter<Animal> {

    private final Context contexto;
    private final List<Animal> listaAnimal = new ArrayList<>();


    public AdapterMeuPet(Context contexto, List<Animal> arrayMeusPets){
        super(contexto, R.layout.activity_meus_pets, arrayMeusPets);

        this.contexto = contexto;
        this.listaAnimal.addAll(arrayMeusPets);
    }



    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_meus_pets,parent,false);

        TextView nomePet = (TextView) rowView.findViewById(R.id.campo_nome);
        TextView racaPet = (TextView) rowView.findViewById(R.id.campo_raca);
        TextView idadePet = (TextView) rowView.findViewById(R.id.campo_idade);
        TextView pesoPet = (TextView) rowView.findViewById(R.id.campo_peso);

        nomePet.setText(listaAnimal.get(position).getNome());
        racaPet.setText(listaAnimal.get(position).getRaca());
        idadePet.setText(listaAnimal.get(position).getNascimento());
        pesoPet.setText((int) listaAnimal.get(position).getPeso());

        return rowView;
    }


}
