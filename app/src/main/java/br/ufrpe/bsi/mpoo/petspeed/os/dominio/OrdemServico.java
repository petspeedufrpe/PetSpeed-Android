package br.ufrpe.bsi.mpoo.petspeed.os.dominio;

import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;

public class OrdemServico {

    private long id;

    private Status status;

    private Medico medico;

    private Triagem triagem;

    private Cliente cliente;

    private Animal animal;

    private String descricao;

    private Prioridade prioridade;

    private long fkMedico;

    private long fkCliente;

    private long fkAnimal;


    public long getFkMedico() {
        return fkMedico;
    }

    public void setFkMedico(long fkMedico) {
        this.fkMedico = fkMedico;
    }

    public long getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(long fkCliente) {
        this.fkCliente = fkCliente;
    }

    public long getFkAnimal() {
        return fkAnimal;
    }

    public void setFkAnimal(long fkAnimal) {
        this.fkAnimal = fkAnimal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Triagem getTriagem() {
        return triagem;
    }

    public void setTriagem(Triagem triagem) {
        this.triagem = triagem;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public enum Status{
        AGUARDANDO_ATENDIMENTO("AGUARDANDO ATENDIMENTO"), EM_ATENDIMENTO("EM ANDAMENTO"), FINALIZADA("FINALIZADA"),
        CANCELADA("CANCELADA");
        private final String descricao;

        Status(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        @Override
        public String toString() {
            return this.descricao;
        }

    }

    public enum Prioridade {

        ALTA("ALTA"),

        BAIXA("BAIXA");

        private final String descricao;

        Prioridade(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        @Override
        public String toString() {
            return this.descricao;
        }


    }
}
