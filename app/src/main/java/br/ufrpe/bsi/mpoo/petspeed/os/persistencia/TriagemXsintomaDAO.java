package br.ufrpe.bsi.mpoo.petspeed.os.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;
import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;

public class TriagemXsintomaDAO {
    private DBHelper dbHelper = new DBHelper();

    public void cadastrar(Triagem triagem, List<Sintomas> sintomasList){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_FK_TRIAGEM,triagem.getId());
        for (int i = 0;i<sintomasList.size();i++){
            values.put(DBHelper.COL_FK_SINTOMAS, String.valueOf(sintomasList.get(i)));
            db.insert(DBHelper.TABELA_SINTOMAS_X_TRIAGEM,null,values);
        }

        db.close();

    }

    public List<String> getAllSintomasByIdTriagem(long idTriagem){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM "+DBHelper.TABELA_SINTOMAS_X_TRIAGEM + " WHERE "+
                DBHelper.COL_FK_TRIAGEM + " = ?";
        String[] args = {String.valueOf(idTriagem)};
        Cursor cursor = db.rawQuery(sql,args);
        List<String> sintomas = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                int indexString = cursor.getColumnIndex(DBHelper.COL_FK_SINTOMAS);
                sintomas.add(cursor.getString(indexString));
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return sintomas;
    }
}
