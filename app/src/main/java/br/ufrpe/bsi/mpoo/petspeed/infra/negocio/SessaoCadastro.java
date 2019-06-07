package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

import java.util.HashMap;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.clinica.dominio.Clinica;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class SessaoCadastro {
    public static final SessaoCadastro instance = new SessaoCadastro();
    private final Map<String, Object> values = new HashMap<>();

    public Usuario getUsuario() {
        return (Usuario) values.get("sessao.Usuario");
    }

    public void setUsuario(Usuario usuario) {
        setValue("sessao.Usuario", usuario);
    }

    public Medico getMedico() {
        return (Medico) values.get("sessao.Medico");
    }

    public void setMedico(Medico medico) {
        setValue("sessao.Medico", medico);
    }

    public Endereco getEndereco() {
        return (Endereco) values.get("sessao.Endereco");
    }

    public void setEndereco(Endereco endereco) {
        setValue("sessao.Endereco", endereco);
    }

    public Clinica getClinica() {
        return (Clinica) values.get("sessao.Clinica");
    }

    public void setClinica(Clinica clinica) {
        setValue("sessao.Clinica", clinica);
    }

    public Cliente getCliente() {
        return (Cliente) values.get("sessao.Cliente");
    }

    public void setCliente(Cliente cliente) {
        setValue("sessao.Cliente", cliente);
    }

    public ContasDeUsuario getTipo() {
        return (ContasDeUsuario) values.get("sessao.Tipo");
    }

    public void setTipo(ContasDeUsuario tipo) {
        setValue("sessao.Tipo", tipo);
    }

    @SuppressWarnings(value = "WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void reset() {
        this.values.clear();
    }
}
