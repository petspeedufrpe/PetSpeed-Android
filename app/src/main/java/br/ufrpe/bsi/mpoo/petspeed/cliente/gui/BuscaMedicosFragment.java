package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.FiltroBuscaMedicos;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;

public class BuscaMedicosFragment extends Fragment {

    EditText txBxNomeMedico;
    private String nomeMedico;

    private OnFragmentInteractionListener mListener;

    public BuscaMedicosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     */
    // TODO: Rename and change types and number of parameters
    public static BuscaMedicosFragment newInstance() {
        BuscaMedicosFragment fragment = new BuscaMedicosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busca_medicos, container, false);
        txBxNomeMedico = view.findViewById(R.id.txBxNomeMedico);
        txBxNomeMedico.setCursorVisible(false);
        txBxNomeMedico.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txBxNomeMedico.setCursorVisible(true);
                }else{
                    txBxNomeMedico.setCursorVisible(false);
                }
            }
        });
        txBxNomeMedico.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
                    nomeMedico = txBxNomeMedico.getText().toString().trim();
                    Sessao.instance.setValue(FiltroBuscaMedicos.Tipo.NOME.getDescricao(),nomeMedico);
                    FiltroMedicosTabViewFragment viewPinMedico = new FiltroMedicosTabViewFragment();
                    viewPinMedico.show(getChildFragmentManager(), "ViewMedicosFragment");
                    txBxNomeMedico.setText("");
                    return true;
                }
                else{
                    return false;
                }
            }
        });


        return view;
    }



    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            try {
                throw new AppException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            } catch (AppException e) {
                Log.d("TAGMEDICOSFRAGMENT",e.getMessage());
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
