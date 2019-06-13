package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

import android.support.v7.widget.RecyclerView;

public interface RecyclerVIewTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder,int direction,int position);
}
