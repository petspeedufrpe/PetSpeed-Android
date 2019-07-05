package br.ufrpe.bsi.mpoo.petspeed.infra.persistencia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petspeed.os.dominio.DiseaseProb;

public class DiseasesDAO {

    private DBHelper dbHelper = new DBHelper();
    public void cadastrar(DiseaseProb diseaseProb,long idOs){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COL_NOME,diseaseProb.getNome());
        contentValues.put(DBHelper.COL_PROB,diseaseProb.getProb());
        contentValues.put(DBHelper.COL_DISEASE_FK_OS,idOs);
        db.insert(DBHelper.TABELA_DISEASES,null,contentValues);
        db.close();
    }
}
