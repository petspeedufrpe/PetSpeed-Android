package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class Sessao {
    public static final Sessao instance = new Sessao();

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final Map<String, Object> values = new HashMap<>();

    public Cliente getCliente() {return (Cliente)values.get("sessao.Cliente");}

    public Usuario getUsuario() {
        return (Usuario) values.get("sessao.Usuario");
    }

    public Medico getMedico(){return (Medico)values.get("sessao.Medico");}

    public void setUsuario(Usuario usuario) {
        setValue("sessao.Usuario", usuario);
    }

    public void setCliente(Cliente cliente){
        setValue("sessao.Cliente",cliente);
    }

    public void setMedico(Medico medico){
        setValue("sessao.Medico",medico);
    }

    public String getUltimoAcesso() {
        String res = (String) values.get("sessao.ultimoAcesso");
        return res != null ? res : "-";
    }

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void reset() {
        this.values.clear();
        updateAcesso();
    }

    public void updateAcesso() {
        SharedPreferences prefs = PetSpeedApp.getContext().getSharedPreferences("sessao", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String date = dateFormat.format(new Date());
        String key = "sessao.ultimoAcesso";
        setValue(key, date);
        editor.putString(key, date);
        editor.apply();

    }
}
