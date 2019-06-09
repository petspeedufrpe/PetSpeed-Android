package br.ufrpe.bsi.mpoo.petspeed.os.negocio;


import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Prioridade;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.OrdemServicoDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemDAO;

public class OrdemServicoServices {

    private OrdemServicoDAO ordemServicoDAO;

    private TriagemDAO triagemDAO;

    public void cadastraOS(OrdemServico os, Triagem triagem) {
        long idTriagem = triagemDAO.cadastraTriagem(triagem);
        os.getTriagem().setId(idTriagem);
        ordemServicoDAO.cadastraOS(os);
    }

    public void deletaOS(OrdemServico os) {
        ordemServicoDAO.deletaOS(os.getId());
    }

    public OrdemServico getOSbyId(long id) {
        return ordemServicoDAO.getOSbyId(id);
    }

    public List<OrdemServico> getOSbyPrioridade(Prioridade prioridade) {
        return ordemServicoDAO.getOsByProridade(prioridade);
    }

    public List<OrdemServico> getOSbyAnimal(Animal animal) {
        return ordemServicoDAO.getOsByAnimal(animal.getId());
    }

}
