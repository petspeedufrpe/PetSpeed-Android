package br.ufrpe.bsi.mpoo.petspeed.animal.negocio;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petspeed.medico.persistencia.MedicoDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.OrdemServicoDAO;

public class AnimalServices {

    private AnimalDAO animalDAO = new AnimalDAO();

    private OrdemServicoDAO ordemServicoDAO;

    private MedicoDAO medicoDAO;

    public long cadastraAnimal(Animal animal) {
        if (animal != null){
            long res = animalDAO.cadastraAnimal(animal);
            return res;

        }
        return -1;
    }

    public void deletaAnimal(Animal animal) {
        if (animal != null){
            animalDAO.deletaAnimal(animal);
        }
    }

    public void alteraFoto() {

    }

    public void adicionaOS() {

    }

    public Animal getAnimalById() {
        return null;
    }

    public OrdemServico getHistoricoById() {
        return null;
    }

    public List<OrdemServico> getHistoricoByMedico() {
        return null;
    }

    public List<OrdemServico> getAllHistorico() {
        return null;
    }

}
