package br.ufrpe.bsi.mpoo.petspeed.infra.negocio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico.Prioridade;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico.Status;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;

public class SessaoAgendamento {

    public static final SessaoAgendamento instance = new SessaoAgendamento();
    private final Map<String, Object> values = new HashMap<>();


    public Medico getMedico() {
        return (Medico) values.get("sessao.Medico");
    }

    public void setMedico(Medico medico) {
        setValue("sessao.Medico", medico);
    }

    public Cliente getCliente() {
        return (Cliente) values.get("sessao.Cliente");
    }

    public void setCliente(Cliente cliente) {
        setValue("sessao.Cliente", cliente);
    }

    public Animal getAnimal(){ return (Animal) values.get("sessao.Animal");}

    public void setAnimal(Animal animal){ setValue("sessao.Animal",animal);}

    public List<Sintomas> getSintomas(){return (List<Sintomas>) values.get("sessao.Sintomas");}

    public void setSintomas(List<Sintomas> sintomas){ setValue("sessao.Sintomas",sintomas);}

    public Triagem getTriagem(){ return (Triagem) values.get("sessao.Triagem"); }

    public void setTriagem(Triagem triagem){setValue("sessao.Triagem",triagem);}

    public void setOs(OrdemServico os){setValue("sessao.OS",os);}

    public OrdemServico getOs(){ return (OrdemServico) values.get("sessao.OS");}

    public Prioridade getPrioridade(){return (Prioridade)values.get("sessao.Prioridade");}

    public void setPrioridade(Prioridade prioridade){setValue("sessao.Prioridade",prioridade);}

    public Status getStatus(){ return (Status)(values.get("sessao.Status"));}

    public void setStatus(Status status){ setValue("sessao.Status",status);}

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
