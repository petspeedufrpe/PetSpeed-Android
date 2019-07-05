package br.ufrpe.bsi.mpoo.petspeed.infra.persistencia;


import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petspeed.os.dominio.DiseaseProb;

public class DiseasesService {

    DiseasesDAO diseasesDAO = new DiseasesDAO();

    public void insertAllDiseases(ArrayList<DiseaseProb> diseaseProbs,long idOs) {
        if (!diseaseProbs.isEmpty()) {
            for (int i = 0; i <= diseaseProbs.size(); i++){
                diseasesDAO.cadastrar(diseaseProbs.get(i),idOs);
            }
        }
    }
}
