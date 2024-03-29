package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.ViewSintomasAnimalAcitivity;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;

public class OSAdapter extends RecyclerView.Adapter<OSAdapter.OSViewHolder>{
    private OrdemServicoServices ordemServicoServices = new OrdemServicoServices();
    private List<OrdemServico> ordemServicoList;
    private Context mContext;

    public class OSViewHolder extends RecyclerView.ViewHolder {
        private TextView btSintomas;
        private TextView btActionAceitar;
        private TextView mClienteNome;
        private TextView mClienteEndereco;
        private TextView mAnimNome;
        private TextView mAnimRaca;
        private TextView mPrioridade;


        public OSViewHolder(View view) {
            super(view);
            mClienteNome = view.findViewById(R.id.fragPopUpMedNome);
            mClienteEndereco = view.findViewById(R.id.fragPopUpMedEnd);
            mAnimNome = view.findViewById(R.id.fragPopUpAnimNome);
            mAnimRaca = view.findViewById(R.id.fragPopUpAnimRaca);
            mPrioridade = view.findViewById(R.id.fragPopUpPrioridade);
            btSintomas = view.findViewById(R.id.fragPopSintomas);
            btActionAceitar = view.findViewById(R.id.fragPopUpDate);
        }
    }

    public OSAdapter(Context context ,List<OrdemServico> medicos) {
        this.ordemServicoList = medicos;
        this.mContext = context;
    }

    @NonNull
    @Override
    public OSViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_os_medico, parent, false);

        return new OSViewHolder(itemView);
    }

    @NonNull
    @Override
    public void onBindViewHolder(final OSViewHolder holder, final int position) {
        final OrdemServico os = this.ordemServicoList.get(position);
        holder.mClienteNome.setText("Nome: " + os.getCliente().getDadosPessoais().getNome());
        holder.mPrioridade.setText("Prioridade: " + os.getPrioridade().getDescricao());
        holder.mClienteEndereco.setText("Endereco: "+
                os.getCliente().getDadosPessoais().getEndereco().getLogradouro() + " N° "
                        + os.getCliente().getDadosPessoais().getEndereco().getNumero() + ", "
                        + os.getCliente().getDadosPessoais().getEndereco().getBairro());
        holder.mAnimNome.setText("Nome: " +os.getAnimal().getNome());
        holder.mAnimRaca.setText("Raca: " +os.getAnimal().getRaca());
        if(os.getPrioridade()== OrdemServico.Prioridade.ALTA){
            holder.mPrioridade.setTextColor(Color.parseColor("#FFFF0000"));
        }else{
            holder.mPrioridade.setTextColor(Color.parseColor("#24B403"));
        }

        holder.btSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sessao.instance.setOs(os);
                mContext.startActivity(new Intent(mContext, ViewSintomasAnimalAcitivity.class));

            }
        });

        holder.btActionAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                os.setStatus(OrdemServico.Status.EM_ATENDIMENTO);
                ordemServicoServices.alteraStatusOs(os);
                SessaoAgendamento.instance.setOs(os);
                SessaoAgendamento.instance.setStatus(OrdemServico.Status.EM_ATENDIMENTO);
                holder.btActionAceitar.setText("CONFIRMADA");
                holder.btActionAceitar.setClickable(false);
            }
        });

        if (os.getStatus() == OrdemServico.Status.EM_ATENDIMENTO) {
            holder.btActionAceitar.setText("CONFIRMADA");
            holder.btActionAceitar.setClickable(false);
        } else if(os.getStatus() == OrdemServico.Status.FINALIZADA){
            holder.btActionAceitar.setText("FINALIZADA");
            holder.btActionAceitar.setClickable(false);
        } else if (os.getStatus() == OrdemServico.Status.CANCELADA){
            holder.btActionAceitar.setText("CANCELADA");
            holder.btActionAceitar.setClickable(false);
        }
    }


    @Override
    public int getItemCount() {
        return ordemServicoList.size();
    }
}
