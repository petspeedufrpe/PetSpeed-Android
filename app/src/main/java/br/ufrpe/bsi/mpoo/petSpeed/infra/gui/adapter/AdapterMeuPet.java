package br.ufrpe.bsi.mpoo.petSpeed.infra.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;


public class AdapterMeuPet extends ArrayAdapter<Animal> {

    private final Context contexto;
    private final ArrayList<Animal> listaAnimal = new ArrayList<>();


    public AdapterMeuPet(Context contexto, ArrayList<Animal> arrayMeusPets, Context contexto1){
        super(contexto, R.layout.activity_meus_pets, arrayMeusPets);

        this.contexto = contexto;
        this.listaAnimal.addAll(arrayMeusPets);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_meus_pets,parent,false);

        TextView nomePet = (TextView) rowView.findViewById(R.id.campo_nome);
        TextView racaPet = (TextView) rowView.findViewById(R.id.campo_raca);
        TextView idadePet = (TextView) rowView.findViewById(R.id.campo_idade);
        TextView pesoPet = (TextView) rowView.findViewById(R.id.campo_altera_email_cliente);
        ImageView fotoPet = (ImageView) rowView.findViewById(R.id.campo_fotoPet);

        nomePet.setText(listaAnimal.get(position).getNome());
        racaPet.setText(listaAnimal.get(position).getRaca());
        idadePet.setText(listaAnimal.get(position).getNascimento());
        pesoPet.setText((int) listaAnimal.get(position).getPeso());
        //fotoPet.setImageResource(listaAnimal.get(position).getFoto);

        return rowView;
    }


}
