package br.ufrpe.bsi.mpoo.petspeed.infra.gui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.ViewSintomasAnimalAcitivity;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class OsAdapterCliente extends RecyclerView.Adapter<OsAdapterCliente.OsAdapterClienteViewHolder> {
    private List<OrdemServico> ordemServicos;
    private Context mContext;
    @NonNull
    @Override
    public OsAdapterClienteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_os_cliente,viewGroup,false);
        return new OsAdapterClienteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OsAdapterClienteViewHolder viewHolder, int i) {
        OrdemServico os = this.ordemServicos.get(i);
        String avaFormatted = String.valueOf(os.getMedico().getAvaliacao()).replace(".",",");
        viewHolder.mNomeMedico.setText("Nome: "+os.getMedico().getDadosPessoais().getNome());
        viewHolder.mClienteEndereco.setText("Endereco: "+
                os.getCliente().getDadosPessoais().getEndereco().getLogradouro() + " N° "
                + os.getCliente().getDadosPessoais().getEndereco().getNumero() + ", "
                + os.getCliente().getDadosPessoais().getEndereco().getBairro());
        viewHolder.avaliacaoMedico.setText("Avaliação: "+avaFormatted);
        viewHolder.mAnimNome.setText("Nome: "+os.getAnimal().getNome());
        viewHolder.mAnimRaca.setText("Raça: "+os.getAnimal().getRaca());
        viewHolder.mPrioridade.setText("Prioridade: "+os.getPrioridade().getDescricao());
        viewHolder.data.setText(getDateFormatted(os));
        if(os.getPrioridade()== OrdemServico.Prioridade.ALTA){

            viewHolder.mPrioridade.setTextColor(Color.parseColor("#FFFF0000"));
        }else{

            viewHolder.mPrioridade.setTextColor(Color.parseColor("#24B403"));
        }


    }

    private String getDateFormatted(OrdemServico ordemServico){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(ordemServico.getData());
    }

    @Override
    public int getItemCount() {
        return ordemServicos.size();
    }

    public OsAdapterCliente(Context context,List<OrdemServico> medicos) {
        this.ordemServicos = medicos;
        this.mContext = context;
    }

    public class OsAdapterClienteViewHolder extends RecyclerView.ViewHolder {
        private TextView mNomeMedico;
        private TextView mClienteEndereco;
        private TextView mAnimNome;
        private TextView mAnimRaca;
        private TextView mPrioridade;
        private TextView avaliacaoMedico;
        private TextView mSitomas;
        private TextView data;


        public OsAdapterClienteViewHolder(@NonNull final View itemView) {
            super(itemView);
            mNomeMedico = itemView.findViewById(R.id.fragPopUpMedNome);
            mClienteEndereco = itemView.findViewById(R.id.fragPopUpMedEnd);
            avaliacaoMedico = itemView.findViewById(R.id.fragPopUpAval);
            mAnimNome = itemView.findViewById(R.id.fragPopUpAnimNome);
            mAnimRaca = itemView.findViewById(R.id.fragPopUpAnimRaca);
            mPrioridade = itemView.findViewById(R.id.fragPopUpPrioridade);
            data = itemView.findViewById(R.id.fragPopUpDate);
            mSitomas = itemView.findViewById(R.id.fragPopSintomas);

            mSitomas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sessao.instance.setOs(ordemServicos.get(getAdapterPosition()));
                    mContext.startActivity(new Intent(mContext, ViewSintomasAnimalAcitivity.class));
                }
            });
        }

    }
}
