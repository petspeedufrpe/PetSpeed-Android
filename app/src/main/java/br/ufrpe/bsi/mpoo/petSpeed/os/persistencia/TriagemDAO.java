package br.ufrpe.bsi.mpoo.petSpeed.os.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.os.dominio.Triagem;

public class TriagemDAO {
    private DBHelper dbHelper = new DBHelper();

    public long cadastraTriagem(Triagem triagem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_TRIAGEM_ID, triagem.getId());
        values.put(DBHelper.COL_TRIAGEM_SINTOMAS, triagem.getSintomas());
        values.put(DBHelper.COL_TRIAGEM_OUTROS, triagem.getOutros());
        long id = db.insert(DBHelper.TABELA_TRIAGEM, null, values);
        db.close();
        return id;
    }

    public void deletaTriagem(long id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABELA_TRIAGEM,DBHelper.COL_TRIAGEM_ID+" = ?",new String[]{String.valueOf(id)});
        db.close();
    }

    public Triagem GetTriagembyId(long idOs) {
        String sql = "SELECT * FROM "+DBHelper.TABELA_TRIAGEM+" WHERE "+DBHelper.COL_TRIAGEM_ID+ " =?";
        String[] args = {String.valueOf(idOs)};
        return this.loadObject(sql,args);
    }

    public Triagem createTriagem(Cursor cursor){
        Triagem triagem = new Triagem();
        int indexId = cursor.getColumnIndex(DBHelper.COL_TRIAGEM_ID);
        int indexOutros = cursor.getColumnIndex(DBHelper.COL_TRIAGEM_OUTROS);
        int indexSintomas = cursor.getColumnIndex(DBHelper.COL_TRIAGEM_SINTOMAS);
        triagem.setSintomas(cursor.getColumnName(indexSintomas));
        triagem.setOutros(cursor.getColumnName(indexOutros));
        triagem.setId(Long.parseLong(cursor.getColumnName(indexId)));
        return triagem;
    }

    public Triagem loadObject(String sql,String[] args){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,args);
        Triagem triagem = null;
        if (cursor.moveToFirst()){
            triagem = createTriagem(cursor);
        }
        cursor.close();
        db.close();
        return triagem;
    }
}
