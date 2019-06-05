package br.ufrpe.bsi.mpoo.petSpeed.infra.negocio;

import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class ControleMeusPets {
    private long id;
    private Animal animal;
    private Cliente cliente;
    private Usuario usuario;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
