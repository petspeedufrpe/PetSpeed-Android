package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import br.ufrpe.bsi.mpoo.petspeed.infra.gui.RecyclerViewAdapterAnimalCliente;

public class SwipeContoller extends ItemTouchHelper.SimpleCallback {

    private RecyclerVIewTouchHelperListener mListener;

    public SwipeContoller(int dragDirs, int swipeDirs, RecyclerVIewTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.mListener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null){
            View foregroundView  = ((RecyclerViewAdapterAnimalCliente.MyViewHolder)viewHolder).itemView;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        if (mListener != null){
            mListener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView  = ((RecyclerViewAdapterAnimalCliente.MyViewHolder)viewHolder).itemView;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView  = ((RecyclerViewAdapterAnimalCliente.MyViewHolder)viewHolder).itemView;
        getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView  = ((RecyclerViewAdapterAnimalCliente.MyViewHolder)viewHolder).itemView;
        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);

    }
}
