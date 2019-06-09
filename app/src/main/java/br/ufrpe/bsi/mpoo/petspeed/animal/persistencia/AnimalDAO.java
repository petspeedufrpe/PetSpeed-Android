package br.ufrpe.bsi.mpoo.petspeed.animal.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.DBHelper;

public class AnimalDAO {

    public static final String SELECT_ALL_FROM = "SELECT * FROM ";
    public static final String WHERE = " WHERE ";
    private DBHelper dbHelper = new DBHelper();

    public long cadastraAnimal(Animal animal) {

        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int anoNascimento = anoAtual - animal.getNascimento();
        animal.setNascimento(anoNascimento);

        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ANIMAL_NOME, animal.getNome());
        values.put(DBHelper.COL_ANIMAL_RACA, animal.getRaca());
        values.put(DBHelper.COL_ANIMAL_PESO, animal.getPeso());
        values.put(DBHelper.COL_ANIMAL_IDADE, animal.getNascimento());
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

        int anoNascimento = cursor.getInt(indexIdade);
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int idade = anoAtual - anoNascimento;

        animal.setNascimento(idade);
        animal.setId(cursor.getLong(indexId));
        animal.setPeso(cursor.getInt(indexPeso));
        animal.setRaca(cursor.getString(indexRaca));
        animal.setNome(cursor.getString(indexNome));
        animal.setFkCliente(cursor.getLong(indexFkCliente));

        return animal;
    }


    public Animal getAnimalById(long idAnimal) {
        String sql = SELECT_ALL_FROM +DBHelper.TABELA_ANIMAL+ WHERE +DBHelper.COL_ANIMAL_ID
                +" = ?";
        String[] args = {String.valueOf(idAnimal)};
        return this.loadObject(sql,args);
    }

    public Animal getAnimalByIdCliente(long idCliente) {
        String sql = SELECT_ALL_FROM + DBHelper.TABELA_ANIMAL+ WHERE + DBHelper.COL_ANIMAL_FK_CLIENTE
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
        values.put(DBHelper.COL_ANIMAL_NOME,animal.getNascimento());
        db.update(DBHelper.TABELA_ANIMAL,values, DBHelper.COL_ANIMAL_ID+ " = ?",new String[]{String.valueOf(animal.getId())});
        db.close();
    }

    public List<Animal> getAllAnimalByIdCliente(long idCliente){
        List<Animal> animalArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = SELECT_ALL_FROM + DBHelper.TABELA_ANIMAL+ WHERE + DBHelper.COL_ANIMAL_FK_CLIENTE+ " = ?";
        String[] args = {String.valueOf(idCliente)};
        Cursor cursor = db.rawQuery(sql,args);
        Animal animal = null;
        if(cursor.moveToFirst()){
            do {
                animal = createAnimal(cursor);
                animalArrayList.add(animal);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();

            return animalArrayList;
        }
        cursor.close();
        db.close();
        return animalArrayList;
    }

}
