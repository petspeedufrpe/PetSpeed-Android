package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.SelecionarAnimalClienteActivity;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;

public class ListaMedicosAdapter extends RecyclerView.Adapter<ListaMedicosAdapter.MedicoViewHolder> {
    private List<Medico> medicos;
    private TextView mActionAgendar;
    private Context mContext;

    public class MedicoViewHolder extends RecyclerView.ViewHolder {
        private TextView mNome;
        private TextView mAvaliacao;
        private TextView mFone;
        private TextView mRuaNumero;
        private TextView mCompl;
        private TextView mCidadeUF;


        public MedicoViewHolder(View view) {
            super(view);
            mNome = view.findViewById(R.id.fragPopUpMedNome);
            mAvaliacao = view.findViewById(R.id.fragPopUpMedAval);
            mFone = view.findViewById(R.id.fragPopSintomas);
            mRuaNumero = view.findViewById(R.id.fragPopUpPrioridade);
            mCompl = view.findViewById(R.id.fragPopUpMedCompl);
            mCidadeUF = view.findViewById(R.id.fragPopUpStatus);
            mActionAgendar = view.findViewById(R.id.fragPopUpDate);
            mActionAgendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        SessaoAgendamento.instance.setMedico(medicos.get(position));
                        mContext.startActivity(new Intent(mContext,SelecionarAnimalClienteActivity.class));
                    }
                }
            });
        }
    }

    public ListaMedicosAdapter(Context context, List<Medico> medicos) {
        this.medicos = medicos;
        this.mContext = context;
    }

    @Override
    public MedicoViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_medico, parent, false);
        mActionAgendar = itemView.findViewById(R.id.fragPopUpDate);
        mActionAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SelecionarAnimalClienteActivity.class);
                mContext.startActivity(intent);
            }
        });


        return new MedicoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedicoViewHolder holder, int position) {
        Medico medico = medicos.get(position);
        holder.mNome.setText(medico.getDadosPessoais().getNome());
        holder.mAvaliacao.setText("Avaliação: " + medico.getAvaliacao());
        holder.mFone.setText("Telefone: " + medico.getTelefone());
        holder.mRuaNumero.setText(
                medico.getDadosPessoais().getEndereco().getLogradouro() + " N° "
                        + medico.getDadosPessoais().getEndereco().getNumero() + ", "
                        + medico.getDadosPessoais().getEndereco().getBairro());
        holder.mCompl.setText(String.valueOf(medico.getDadosPessoais().getEndereco().getComplemento()));
        holder.mCidadeUF.setText(
                "Cidade: " + medico.getDadosPessoais().getEndereco().getCidade() + " - "
                        + medico.getDadosPessoais().getEndereco().getUf());

    }

    @Override
    public int getItemCount() {
        return medicos.size();
    }
}
