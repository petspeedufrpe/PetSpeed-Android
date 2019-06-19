package br.ufrpe.bsi.mpoo.petspeed.os.persistencia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

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
}
