package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class OSAdapter extends RecyclerView.Adapter<OSAdapter.OSViewHolder>{
    private List<OrdemServico> OSs;

    private TextView btActionAceitar;
    private TextView btSintomas;
    public class OSViewHolder extends RecyclerView.ViewHolder {
        private TextView mClienteNome;
        private TextView mClienteEndereco;
        private TextView mAnimNome;
        private TextView mAnimRaca;
        private TextView mPrioridade;



        public OSViewHolder(View view) {
            super(view);
            mClienteNome = view.findViewById(R.id.fragPopUpClieNome);
            mClienteEndereco = view.findViewById(R.id.fragPopUpClieEnd);
            mAnimNome = view.findViewById(R.id.fragPopUpAnimNome);
            mAnimRaca = view.findViewById(R.id.fragPopUpAnimRaca);
            mPrioridade = view.findViewById(R.id.fragPopUpPrioridade);
            btSintomas = view.findViewById(R.id.fragPopSintomas);
            btActionAceitar = view.findViewById(R.id.fragPopUpMedAceitar);
        }
    }

    public OSAdapter(List<OrdemServico> medicos) {
        this.OSs = medicos;
    }

    @Override
    public OSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_os_medico, parent, false);
        btActionAceitar = itemView.findViewById(R.id.fragPopUpMedAceitar);
        btActionAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //a fazer
            }
        });
        btSintomas = itemView.findViewById(R.id.fragPopSintomas);
        btSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //a fazer
            }
        });



        return new OSViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OSViewHolder holder, int position) {
        OrdemServico os = this.OSs.get(position);
        holder.mClienteNome.setText("Nome: " + os.getCliente().getDadosPessoais().getNome());
        holder.mPrioridade.setText("Prioridade: " + os.getPrioridade().getDescricao());
        holder.mClienteEndereco.setText("Endereco: "+
                os.getCliente().getDadosPessoais().getEndereco().getLogradouro() + " N° "
                        + os.getCliente().getDadosPessoais().getEndereco().getNumero() + ", "
                        + os.getCliente().getDadosPessoais().getEndereco().getBairro());
        holder.mAnimNome.setText("Nome: " +os.getAnimal().getNome());
        holder.mAnimRaca.setText("Raca: " +os.getAnimal().getRaca());

    }

    @Override
    public int getItemCount() {
        return OSs.size();
    }
}
