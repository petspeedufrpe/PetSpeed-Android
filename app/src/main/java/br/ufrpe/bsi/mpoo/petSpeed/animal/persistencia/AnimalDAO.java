package br.ufrpe.bsi.mpoo.petSpeed.animal.persistencia;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.os.dominio.OrdemServico;

public class AnimalDAO {

    private DBHelper dbHelper = new DBHelper();

    public long cadastraAnimal(Animal animal, long idCliente) {
        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ANIMAL_NOME, animal.getNome());
        values.put(DBHelper.COL_ANIMAL_RACA, animal.getRaca());
        values.put(DBHelper.COL_ANIMAL_PESO, animal.getPeso());
        values.put(DBHelper.COL_ANIMAL_IDADE, animal.getIdade());
        values.put(DBHelper.COL_ANIMAL_FK_CLIENTE, idCliente);
        long res = dbWrite.insert(DBHelper.TABELA_ANIMAL, null, values);
        dbWrite.close();
        return res;
    }

    public void cadastraAnimal(Animal animal) {

    }

    public void deletaAnimal(Animal animal) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABELA_ANIMAL, DBHelper.COL_ANIMAL_ID + " = ?", new String[]{String.valueOf(animal.getId())});
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
