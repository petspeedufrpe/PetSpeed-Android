package br.ufrpe.bsi.mpoo.petSpeed.medico.persistencia;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;

public class MedicoDAO {

    private DBHelper db = new DBHelper();


    public long cadastraMedico(Medico medico) {
        SQLiteDatabase dbWrite = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_MEDICO_AVALIACAO, medico.getAvaliacao());
        values.put(DBHelper.COL_MEDICO_CRMV, medico.getCrmv());
        values.put(DBHelper.COL_MEDICO_FK_USUARIO, medico.getUsuario().getId());
        values.put(DBHelper.COL_MEDICO_FK_PESSOA, medico.getDadosPessoais().getId());
        long id = dbWrite.insert(DBHelper.TABELA_MEDICO, null, values);
        db.close();
        return id;
    }

    public void deletaMedico() {

    }

    public void alteraAvaliacao() {

    }

    public void alteraClinica() {

    }

    public void alteraEndereco() {

    }

    public void alteraCrmv() {

    }
}


