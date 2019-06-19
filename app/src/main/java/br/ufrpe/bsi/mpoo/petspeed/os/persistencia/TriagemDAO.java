package br.ufrpe.bsi.mpoo.petspeed.os.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.SintomasDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;

public class TriagemDAO {
    private static final String SQL_SELECT_FROM = "SELECT * FROM ";
    private static final String SQL_WHERE = " WHERE ";
    private SintomasDAO sintomasDAO = new SintomasDAO();
    private DBHelper dbHelper = new DBHelper();

    public long cadastraTriagem(Triagem triagem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
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

    public Triagem getTriagembyId(long idOs) {
        String sql = SQL_SELECT_FROM +DBHelper.TABELA_TRIAGEM+ SQL_WHERE +DBHelper.COL_TRIAGEM_ID+ " = ?";
        String[] args = {String.valueOf(idOs)};
        return this.loadObject(sql,args);
    }

    public Triagem createTriagem(Cursor cursor){
        Triagem triagem = new Triagem();
        int indexId = cursor.getColumnIndex(DBHelper.COL_TRIAGEM_ID);
        int indexOutros = cursor.getColumnIndex(DBHelper.COL_TRIAGEM_OUTROS);
        int indexSintomas = cursor.getColumnIndex(DBHelper.COL_TRIAGEM_SINTOMAS);
        triagem.setSintomas(cursor.getString(indexSintomas));
        triagem.setOutros(cursor.getString(indexOutros));
        triagem.setId(cursor.getLong(indexId))
        ;
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
