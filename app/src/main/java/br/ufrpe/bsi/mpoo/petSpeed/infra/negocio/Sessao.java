package br.ufrpe.bsi.mpoo.petSpeed.infra.negocio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class Sessao {
    public static final Sessao instance = new Sessao();

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final Map<String,Object> values = new HashMap<>();


    public void setUsuario(Usuario usuario){
        setValue("sessao.Usuario",usuario);
    }

    public Usuario getUsuario(){
        return (Usuario)values.get("sessao.Usuario");
    }

    public String getUltimoAcesso(){
        String res = (String)values.get("sessao.ultimoAcesso");
        return res != null ? res : "-";
    }
    @SuppressWarnings("WeakerAccess")
    public void setValue(String key,Object value){
        values.put(key,value);
    }

    public Object getValue(String key){
        return values.get(key);
    }

    public void reset(){
        this.values.clear();
        updateAcesso();
    }

    public void updateAcesso(){
        SharedPreferences prefs = PetSpeedApp.getContext().getSharedPreferences("sessao", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor= prefs.edit();
        String date = DATE_FORMAT.format(new Date());
        String key = "sessao.ultimoAcesso";
        setValue(key,date);
        editor.putString(key,date);
        editor.apply();

    }
}
