package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petSpeed.R;


public class ViewMedicosFragment extends DialogFragment {
    private static final String TAG = "ViewMedicosFragment";


    private TextView mNome, mAvaliacao, mFone, mRuaNumero, mCompl, mCidadeUF, mActionVoltar, mActionAgendar;

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
}