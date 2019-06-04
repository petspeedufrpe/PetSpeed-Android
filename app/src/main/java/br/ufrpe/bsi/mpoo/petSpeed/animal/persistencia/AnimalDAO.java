package br.ufrpe.bsi.mpoo.petSpeed.animal.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.os.dominio.OrdemServico;

public class AnimalDAO {

    private DBHelper dbHelper = new DBHelper();

    public long cadastraAnimal(Animal animal) {
        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ANIMAL_NOME, animal.getNome());
        values.put(DBHelper.COL_ANIMAL_RACA, animal.getRaca());
        values.put(DBHelper.COL_ANIMAL_PESO, animal.getPeso());
        values.put(DBHelper.COL_ANIMAL_IDADE, animal.getIdade());
        values.put(DBHelper.COL_ANIMAL_FK_CLIENTE, animal.getFkCliente());
        long res = dbWrite.insert(DBHelper.TABELA_ANIMAL, null, values);
        dbWrite.close();
        return res;
    }


    public void deletaAnimal(Animal animal) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABELA_ANIMAL, DBHelper.COL_ANIMAL_ID + " = ?", new String[]{String.valueOf(animal.getId())});
        db.close();

    }

    public Animal loadObject(String sql, String[] args){
        Animal animal = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,args);
        if (cursor.moveToFirst()){
            animal = createAnimal(cursor);
        }
        cursor.close();
        db.close();

        return animal;
    }

    private Animal createAnimal(Cursor cursor) {
        Animal animal = new Animal();
        int indexId = cursor.getColumnIndex(DBHelper.COL_ANIMAL_ID);
        int indexIdade = cursor.getColumnIndex(DBHelper.COL_ANIMAL_IDADE);
        int indexPeso = cursor.getColumnIndex(DBHelper.COL_ANIMAL_PESO);
        int indexRaca = cursor.getColumnIndex(DBHelper.COL_ANIMAL_RACA);
        int indexNome = cursor.getColumnIndex(DBHelper.COL_ANIMAL_NOME);
        int indexFkCliente = cursor.getColumnIndex(DBHelper.COL_ANIMAL_FK_CLIENTE);
        animal.setId(cursor.getLong(indexId));
        animal.setIdade(cursor.getInt(indexIdade));
        animal.setPeso(cursor.getInt(indexPeso));
        animal.setRaca(cursor.getString(indexRaca));
        animal.setNome(cursor.getString(indexNome));
        animal.setFkCliente(cursor.getLong(indexFkCliente));

        return animal;
    }

    public void alteraFoto() {

    }


    public Animal getAnimalById(long idAnimal) {
        return null;
    }

    public Animal getAnimalByIdCliente(long idCliente) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM "+ DBHelper.TABELA_ANIMAL+" WHERE "+ DBHelper.COL_ANIMAL_FK_CLIENTE
                +" = ?";
        String[] args = {String.valueOf(idCliente)};
        return this.loadObject(sql,args);
    }

    public void alteraNome(Animal animal){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ANIMAL_NOME,animal.getNome());
        db.update(DBHelper.TABELA_ANIMAL,values, DBHelper.COL_ANIMAL_ID+ " = ?",new String[]{String.valueOf(animal.getId())});
        db.close();
    }
    public void alteraPeso(Animal animal){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ANIMAL_NOME,animal.getPeso());
        db.update(DBHelper.TABELA_ANIMAL,values, DBHelper.COL_ANIMAL_ID+ " = ?",new String[]{String.valueOf(animal.getId())});
        db.close();
    }
    public void alteraRaca(Animal animal){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ANIMAL_NOME,animal.getRaca());
        db.update(DBHelper.TABELA_ANIMAL,values, DBHelper.COL_ANIMAL_ID+ " = ?",new String[]{String.valueOf(animal.getId())});
        db.close();
    }
    public void alteraIdade(Animal animal){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ANIMAL_NOME,animal.getIdade());
        db.update(DBHelper.TABELA_ANIMAL,values, DBHelper.COL_ANIMAL_ID+ " = ?",new String[]{String.valueOf(animal.getId())});
        db.close();
    }

    public ArrayList<Animal> getAllAnimalByIdCliente(long idCliente){
        ArrayList<Animal> animalArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM "+ DBHelper.TABELA_ANIMAL+" WHERE "+ DBHelper.COL_ANIMAL_FK_CLIENTE+ " = ?";
        String[] args = {String.valueOf(idCliente)};
        Cursor cursor = db.rawQuery(sql,args);
        Animal animal = null;
        if(cursor.moveToFirst()){
            do {
                animal = createAnimal(cursor);
                animalArrayList.add(animal);
            }while (cursor.moveToNext());
            return animalArrayList;
        }
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
