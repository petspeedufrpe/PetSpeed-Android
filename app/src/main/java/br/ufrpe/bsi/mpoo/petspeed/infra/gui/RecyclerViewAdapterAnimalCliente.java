package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.RecyclerViewClickListener;


public class RecyclerViewAdapterAnimalCliente extends RecyclerView.Adapter<RecyclerViewAdapterAnimalCliente.MyViewHolder> {

    private RecyclerViewClickListener listener;
    private Context mContext;
    private List<Animal> mAnimals;
    private AnimalDAO animalDAO = new AnimalDAO();
    private boolean mSelect = false;
    private ArrayList<Animal> selectedItems = new ArrayList<Animal>();
    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            mSelect = true;
            menu.add("Delete");
            return  true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            for (Animal animal:selectedItems){
                animalDAO.deletaAnimal(animal);
                mAnimals.remove(animal);
            }

            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mSelect = false;
            selectedItems.clear();
            notifyDataSetChanged();

        }
    };

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
        view = mInflater.inflate(R.layout.cardview_animal_cliente, viewGroup, false);
        return new MyViewHolder(view, listener);
    }

    private Bitmap getThumbnailFromBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width, height);
        if (max > 512) {
            int thumbWidth = Math.round((512f / max) * width);
            int thumbHeight = Math.round((512f / max) * height);
            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, thumbWidth, thumbHeight);
            bitmap.recycle();
            return thumbnail;
        } else {
            return bitmap;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.nomeAnimal.setText("Nome: " + mAnimals.get(i).getNome());
        holder.racaAnimal.setText("Raça: " + mAnimals.get(i).getRaca());
        holder.pesoAnimal.setText("Peso: " + String.valueOf(mAnimals.get(i).getPeso()) + "Kg");
        holder.idadeAnimal.setText("Idade: " + String.valueOf(mAnimals.get(i).getNascimento()));
        try {
            byte[] imagemEmBits = mAnimals.get(i).getFoto();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagemEmBits, 0, imagemEmBits.length);
            holder.imageAnimal.setImageBitmap(
                    getThumbnailFromBitmap(bitmap));

        } catch (Exception e) {
            Log.d("Minha Tag", e.toString());
        }

        holder.update(mAnimals.get(i));


    }

    @Override
    public int getItemCount() {
        return mAnimals.size();
    }

    public void removeItem(int position) {
        animalDAO.deletaAnimal(mAnimals.get(position));
        mAnimals.remove(position);
        notifyItemRemoved(position);

    }

    public void restoreItem(Animal animal, int position) {
        mAnimals.add(position, animal);
        notifyItemInserted(position);
        animalDAO.cadastraAnimal(animal);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private  RelativeLayout relativeLayout;
        private TextView nomeAnimal;
        private TextView racaAnimal;
        private TextView pesoAnimal;
        private TextView idadeAnimal;
        ImageView imageAnimal;

        public MyViewHolder(@NonNull final View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            findTexts();
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }

        private void findTexts() {
            nomeAnimal = itemView.findViewById(R.id.txt_nome_animal);
            racaAnimal = itemView.findViewById(R.id.txt_raca_animal);
            pesoAnimal = itemView.findViewById(R.id.txt_peso_animal);
            idadeAnimal = itemView.findViewById(R.id.txt_idade_animal);
            imageAnimal = itemView.findViewById(R.id.image_animal);

        }

        void  selectItem(Animal animal){
            if (mSelect){
                if (selectedItems.contains(animal)){
                    selectedItems.remove(animal);
                }else{
                    selectedItems.add(animal);
                }
            }
        }

        void update(final Animal animal){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((AppCompatActivity) v.getContext()).startSupportActionMode(actionModeCallbacks);
                    selectItem(animal);
                    return true;
                }
            });
        }

    }
}

