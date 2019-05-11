package br.ufrpe.bsi.mpoo.petSpeed.persistencia;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.dominio.Cliente.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.dominio.Serviços.OrdemServico;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;

public class AnimalDAO {

	private DBHelper dbHelper= new DBHelper();

	public long cadastraAnimal(Animal animal) {
        long res;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_IDADE_ANIMAL, animal.getIdade());
        values.put(DBHelper.COL_NOME_ANIMAL, animal.getNome());
        values.put(DBHelper.COL_PESO_ANIMAL, animal.getPeso());
        values.put(DBHelper.COL_RACA_ANIMAL, animal.getRaca());
        res = db.insert(DBHelper.TABELA_ANIMAL, null, values);
        db.close();

        return res;
    }

	public void deletaAnimal(Animal animal) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(DBHelper.TABELA_ANIMAL,DBHelper.COL_ID_ANIMAL + " = ?",new String[] {String.valueOf(animal.getId())});
		db.close();

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
