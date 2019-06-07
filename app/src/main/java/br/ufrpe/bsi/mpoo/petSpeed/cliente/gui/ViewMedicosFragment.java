package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.FragmentDataTransferInterface;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;


public class ViewMedicosFragment extends DialogFragment {
    private static final String TAG = "ViewMedicosFragment";


    private TextView mNome, mAvaliacao, mFone, mRuaNumero, mCompl, mCidadeUF, mActionVoltar, mActionAgendar;
    private Medico medico;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_medicos, container, false);

        mNome = view.findViewById(R.id.fragPopUpMedNome);
        mAvaliacao = view.findViewById(R.id.fragPopUpMedAval);
        mFone = view.findViewById(R.id.fragPopUpMedFone);
        mRuaNumero = view.findViewById(R.id.fragPopUpMedRuaNumero);
        mCompl = view.findViewById(R.id.fragPopUpMedCompl);
        mCidadeUF = view.findViewById(R.id.fragPopUpMedCidadeUF);
        mActionVoltar = view.findViewById(R.id.fragPopUpMedVoltar);
        mActionAgendar = view.findViewById(R.id.fragPopUpMedBtnAgendar);

        medico = (Medico)Sessao.instance.getValue(ContasDeUsuario.MEDICO.getDescricao());
        setTextFields(medico);
        Sessao.instance.setMedico(null);

        mActionAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo
            }
        });


        mActionVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    public void setTextFields(Medico medico) {
        mNome.setText(medico.getDadosPessoais().getNome());
        mAvaliacao.setText("Avaliação: " + String.valueOf(medico.getAvaliacao()));
        mFone.setText("Telefone: " + medico.getTelefone());
        mRuaNumero.setText(
                medico.getDadosPessoais().getEndereco().getLogradouro() + " N° "
                + String.valueOf(medico.getDadosPessoais().getEndereco().getNumero()) + ", "
                + medico.getDadosPessoais().getEndereco().getBairro());
        mCompl.setText(String.valueOf(medico.getDadosPessoais().getEndereco().getComplemento()));
        mCidadeUF.setText(
                "Cidade: " + String.valueOf(medico.getDadosPessoais().getEndereco().getCidade()) + " - "
                + String.valueOf(medico.getDadosPessoais().getEndereco().getUf()));
    }
}