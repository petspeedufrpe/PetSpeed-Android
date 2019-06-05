package br.ufrpe.bsi.mpoo.petSpeed.infra.gui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ControleMeusPets;


public class AdapterMeuPet extends ArrayAdapter<ControleMeusPets> {

    private final Context contexto;
    private final ArrayList<ControleMeusPets> listaAnimal = new ArrayList<>();


    public AdapterMeuPet(Context contexto, ArrayList<ControleMeusPets> arrayMeusPets, Context contexto1){
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
        TextView pesoPet = (TextView) rowView.findViewById(R.id.campo_peso);
        ImageView fotoPet = (ImageView) rowView.findViewById(R.id.campo_fotoPet);

        nomePet.setText(listaAnimal.get(position).getAnimal().getNome());
        racaPet.setText(listaAnimal.get(position).getAnimal().getRaca());
        idadePet.setText(listaAnimal.get(position).getAnimal().getIdade());
        pesoPet.setText((int) listaAnimal.get(position).getAnimal().getPeso());
        //fotoPet.setImageResource(listaAnimal.get(position).getFoto);

        return rowView;
    }


}
