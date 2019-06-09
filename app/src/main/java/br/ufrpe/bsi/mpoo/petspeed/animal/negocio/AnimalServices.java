package br.ufrpe.bsi.mpoo.petspeed.animal.negocio;

import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.persistencia.AnimalDAO;

public class AnimalServices {

    private AnimalDAO animalDAO = new AnimalDAO();


    public long cadastraAnimal(Animal animal) {
        if (animal != null) {
            return animalDAO.cadastraAnimal(animal);
        }
        return -1;
    }

    public void deletaAnimal(Animal animal) {
        if (animal != null) {
            animalDAO.deletaAnimal(animal);
        }
    }
}
