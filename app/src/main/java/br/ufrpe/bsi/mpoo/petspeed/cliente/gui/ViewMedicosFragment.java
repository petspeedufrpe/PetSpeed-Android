package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;


public class ViewMedicosFragment extends DialogFragment {

    private TextView mNome;
    private TextView mAvaliacao;
    private TextView mFone;
    private TextView mRuaNumero;
    private TextView mCompl;
    private TextView mCidadeUF;
    private TextView mActionVoltar;
    private TextView mActionAgendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_medicos, container, false);

        findViews(view);

        if (Sessao.instance.getValue(ContasDeUsuario.MEDICO.getDescricao()) != null) {
            mNome.setTextColor(Color.parseColor("#357A01"));
            showMedico();
            mActionAgendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeClienteActivity hCliente = (HomeClienteActivity) getActivity();
                    startActivity(new Intent(hCliente.getBaseContext(), SelecionarAnimalClienteActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                }
            });

        } else {
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
                SessaoAgendamento.instance.reset();
                getDialog().dismiss();
            }
        });


            return view;
    }

    private void findViews(View view) {
        mNome = view.findViewById(R.id.fragPopUpMedNome);
        mAvaliacao = view.findViewById(R.id.fragPopUpMedAval);
        mFone = view.findViewById(R.id.fragPopSintomas);
        mRuaNumero = view.findViewById(R.id.fragPopUpPrioridade);
        mCompl = view.findViewById(R.id.fragPopUpMedCompl);
        mCidadeUF = view.findViewById(R.id.fragPopUpStatus);
        mActionVoltar = view.findViewById(R.id.fragPopUpMedVoltar);
        mActionAgendar = view.findViewById(R.id.fragPopUpMedBtnAgendar);
    }

    private void showMedico() {
        Medico medico = (Medico) Sessao.instance.getValue(ContasDeUsuario.MEDICO.getDescricao());
        SessaoAgendamento.instance.setMedico(medico);
        mNome.setText(medico.getDadosPessoais().getNome());
        mAvaliacao.setText("Avaliação: " + medico.getAvaliacao());
        mFone.setText("Telefone: " + medico.getTelefone());
        mRuaNumero.setText(
                medico.getDadosPessoais().getEndereco().getLogradouro() + " N° "
                        + medico.getDadosPessoais().getEndereco().getNumero() + ", "
                        + medico.getDadosPessoais().getEndereco().getBairro());
        mCompl.setText(String.valueOf(medico.getDadosPessoais().getEndereco().getComplemento()));
        mCidadeUF.setText(
                "Cidade: " + medico.getDadosPessoais().getEndereco().getCidade() + " - "
                        + medico.getDadosPessoais().getEndereco().getUf());

        Sessao.instance.setValue(ContasDeUsuario.MEDICO.getDescricao(), null);
    }

    private void showCliente() {
        mNome.setText(Sessao.instance.getCliente().getDadosPessoais().getNome());
        mAvaliacao.setText("Avaliação: " + Sessao.instance.getCliente().getAvaliacao());
        mFone.setText("Telefone: " + Sessao.instance.getCliente().getTelefone());
        mRuaNumero.setText(
                Sessao.instance.getCliente().getDadosPessoais().getEndereco().getLogradouro() + " N° "
                        + Sessao.instance.getCliente().getDadosPessoais().getEndereco().getNumero() + ", "
                        + Sessao.instance.getCliente().getDadosPessoais().getEndereco().getBairro());
        mCompl.setText(String.valueOf(Sessao.instance.getCliente().getDadosPessoais().getEndereco().getComplemento()));
        mCidadeUF.setText(
                "Cidade: " + Sessao.instance.getCliente().getDadosPessoais().getEndereco().getCidade() + " - "
                        + Sessao.instance.getCliente().getDadosPessoais().getEndereco().getUf());
    }
}