package br.ufrpe.bsi.mpoo.petSpeed.infra.negocio;

import android.view.View;

//Listener para o evento onClick do cardView Animal Cliente
public interface RecyclerViewClickListener {

    void onClick(View v, int position);

}