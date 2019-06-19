package br.ufrpe.bsi.mpoo.petspeed.os.negocio;


import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.OrdemServicoDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemDAO;

public class OrdemServicoServices {

    private OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();

    private TriagemDAO triagemDAO = new TriagemDAO();

    public long cadastraOS(OrdemServico os, Triagem triagem) {
        int idTriagem = (int) triagemDAO.cadastraTriagem(triagem);
        os.getTriagem().setId(idTriagem);
        return ordemServicoDAO.cadastraOS(os);
    }

    public void deletaOS(OrdemServico os) {
        ordemServicoDAO.deletaOS(os.getId());
    }

    public OrdemServico getOSbyId(long id) {
        return ordemServicoDAO.getOSbyId(id);
    }

    public List<OrdemServico> getOSbyPrioridade(OrdemServico.Prioridade prioridade) {
        return ordemServicoDAO.getOsByProridade(prioridade);
    }

    public List<OrdemServico> getOsbyIdMedico(long idMedico){
        return ordemServicoDAO.getOsByIdMedico(idMedico);
    }

    public OrdemServico getOsByIdMedico(Medico medico){
        OrdemServico ordemServico =  ordemServicoDAO.getOsByIdMedico(medico);

        if (ordemServico != null){
            return ordemServico;
        }
        return null;
    }

    public void alteraStatusOs(OrdemServico ordemServico){
        ordemServicoDAO.alterarStatus(ordemServico);
    }

    public List<OrdemServico> getOSbyAnimal(Animal animal) {
        return ordemServicoDAO.getOsByAnimal(animal.getId());
    }

}
