package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ContasDeUsuario;
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

        if(Sessao.instance.getValue(ContasDeUsuario.MEDICO.getDescricao())!=null){
            mNome.setTextColor(Color.parseColor("#357A01"));
            showMedico();
            mActionAgendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Todo
                }
            });

        }else{
            mNome.setTextColor(Color.parseColor("#FF0299AC"));
            mActionAgendar.setText("MEU PERFIL");
            mActionAgendar.setTextColor(Color.parseColor("#FF0299AC"));
            showCliente();
            mActionAgendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeClienteActivity hCliente = (HomeClienteActivity) getActivity();
                    startActivity(new Intent(hCliente.getBaseContext(), PerfilClienteActivity.class));
                    getDialog().dismiss();
                }
            });

        }



        mActionVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    private void showMedico() {
        medico = (Medico) Sessao.instance.getValue(ContasDeUsuario.MEDICO.getDescricao());
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

        Sessao.instance.setValue(ContasDeUsuario.MEDICO.getDescricao(), null);
    }

    private void showCliente() {
        mNome.setText(Sessao.instance.getCliente().getDadosPessoais().getNome());
        mAvaliacao.setText("Avaliação: " + String.valueOf(Sessao.instance.getCliente().getAvaliacao()));
        mFone.setText("Telefone: " + Sessao.instance.getCliente().getTelefone());
        mRuaNumero.setText(
                Sessao.instance.getCliente().getDadosPessoais().getEndereco().getLogradouro() + " N° "
                        + String.valueOf(Sessao.instance.getCliente().getDadosPessoais().getEndereco().getNumero()) + ", "
                        + Sessao.instance.getCliente().getDadosPessoais().getEndereco().getBairro());
        mCompl.setText(String.valueOf(Sessao.instance.getCliente().getDadosPessoais().getEndereco().getComplemento()));
        mCidadeUF.setText(
                "Cidade: " + String.valueOf(Sessao.instance.getCliente().getDadosPessoais().getEndereco().getCidade()) + " - "
                        + String.valueOf(Sessao.instance.getCliente().getDadosPessoais().getEndereco().getUf()));
    }
}