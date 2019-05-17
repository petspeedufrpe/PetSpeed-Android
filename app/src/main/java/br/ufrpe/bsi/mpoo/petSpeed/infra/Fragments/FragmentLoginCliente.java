package br.ufrpe.bsi.mpoo.petSpeed.infra.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.gui.MainActivity;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class FragmentLoginCliente extends Fragment {



    private EditText mSenha;
    private EditText mEmail;
    private String email,senha;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance){//lembrar de trocar os EditTexts pelos ids
        View v = inflater.inflate(R.layout.activity_login,container,false);                   //das telas deste fragment
        mSenha = v.findViewById(R.id.passwd);
        mEmail = v.findViewById(R.id.email);
        btnLogin = v.findViewById(R.id.loginActLoginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });


        return v;
    }

    private void logar(){
        String message = new String();
        Cliente cliente = null;
        capturaTexos();
        if(!isCamposValidos()){
            return;
        }
        ClienteServices clienteServices = new ClienteServices();
        try {
            cliente = clienteServices.login(email,senha);
        } catch (AppException e) {
            e.printStackTrace();
            message = e.getMessage();
        }
        if(cliente!= null){
            Toast.makeText(getContext(),"Usuário logado com sucesso",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCamposValidos(){
        View focusView = null;
        mEmail.setError(null);
        mSenha.setError(null);
        boolean res = true;
        if(email.isEmpty()|| Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Email inválido");
            focusView = mEmail;
            res = false;
        }else if (senha.isEmpty()){
            mSenha.setError("Senha inválida");
            focusView = mSenha;
            res = false;
        }

        return res;
    }

    private void capturaTexos(){
        email = mEmail.getText().toString().trim();
        senha = mSenha.getText().toString().trim();
    }

    private void goHome(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        finishActivity();
    }

    private void finishActivity(){
        if(getActivity()!= null){
            getActivity().finish();
        }
    }
}