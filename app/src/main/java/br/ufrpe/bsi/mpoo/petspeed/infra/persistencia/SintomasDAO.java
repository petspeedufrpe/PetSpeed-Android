package br.ufrpe.bsi.mpoo.petspeed.infra.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;

public class SintomasDAO {

    private static final String SQL_SELECT_FROM = "SELECT * FROM ";
    private static final String SQL_WHERE = " WHERE ";
    private static final String SQL_LIKE = " LIKE ?;";
    private  DBHelper dbHelper = new DBHelper();


    private void cadastrarSintomas(Sintomas sintomas){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_SINTOMAS_DESCRICAO,sintomas.getDescricao());
        db.insert(DBHelper.TABELA_SINTOMAS,null,values);
        db.close();
    }

    public void iterateSintomas(){
        List<Sintomas> sintomasList = Arrays.asList(Sintomas.values());
        for (Sintomas sintomas1:sintomasList){
            cadastrarSintomas(sintomas1);
        }
    }

    public Sintomas getSintomaByName(String name){
        String sql = SQL_SELECT_FROM+DBHelper.TABELA_SINTOMAS+SQL_WHERE+DBHelper.COL_SINTOMAS_DESCRICAO+SQL_LIKE;
        String[] args = {name};
        return this.loadObject(sql,args);
    }

    private Sintomas loadObject(String sql, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,args);
        Sintomas sintomas = null;
        if (cursor.moveToFirst()){
            sintomas = createSintomas(cursor);
        }
        cursor.close();
        db.close();
        return sintomas;
    }

    private Sintomas createSintomas(Cursor cursor) {
        Sintomas sintomas;
        int indexDescricao = cursor.getColumnIndex(DBHelper.COL_SINTOMAS_DESCRICAO);
        String descricao = cursor.getString(indexDescricao);
        sintomas = Sintomas.valueOf(descricao);
        return  sintomas;
    }

}
