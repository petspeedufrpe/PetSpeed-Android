package br.ufrpe.bsi.mpoo.petSpeed.clinica.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petSpeed.clinica.dominio.Clinica;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;

public class ClinicaDAO {

    private DBHelper dbHelper = new DBHelper();

    public long cadastraClinica(Clinica clinica) {
        long res;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CLINICA_NOME, clinica.getNome());
        values.put(DBHelper.COL_CLINICA_RAZAO_SOCIAL, clinica.getRazaoSocial());
        values.put(DBHelper.COL_CLINICA_CRMV, clinica.getCrmv());
        values.put(DBHelper.COL_CLINICA_AVALIACAO, clinica.getAvaliacao());
        values.put(DBHelper.COL_CLINICA_FK_USUARIO, clinica.getUsuario().getId());
        res = db.insert(DBHelper.TABELA_CLINICA, null, values);

        return res;

    }

    public void deletaClinica(Clinica clinica) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABELA_CLINICA, DBHelper.COL_CLINICA_ID + " = ?",
                new String[]{String.valueOf(clinica.getId())});
        db.close();
    }

    public void alteraAvaliacao(Clinica clinica) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CLINICA_AVALIACAO, clinica.getAvaliacao());
        db.update(DBHelper.TABELA_CLINICA, values, DBHelper.COL_CLINICA_ID + " = ?",
                new String[]{String.valueOf(clinica.getId())});
        db.close();
    }

    public Clinica getClinicaById(long idClinica) {
        String query = "SELECT * FROM " + DBHelper.TABELA_CLINICA +
                " WHERE " + DBHelper.TABELA_CLINICA + " = ?";
        String[] args = {String.valueOf(idClinica)};

        return this.loadClinica(query, args);
    }

    public Clinica getClinicaByCnpj(String cnpj) {
        String query = " SELECT * FROM " + DBHelper.TABELA_CLINICA +
                " WHERE " + DBHelper.COL_CLINICA_RAZAO_SOCIAL + " =?";
        String[] args = {cnpj};

        return this.loadClinica(query, args);
    }

    public Clinica loadClinica(String query, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);
        Clinica clinica = null;
        if (cursor.moveToFirst()) {
            clinica = createClinica(cursor);
        }
        cursor.close();
        db.close();
        return clinica;
    }

    private Clinica createClinica(Cursor cursor) {
        int indexId = cursor.getColumnIndex(DBHelper.COL_CLINICA_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.COL_CLINICA_NOME);
        int indexCrmv = cursor.getColumnIndex(DBHelper.COL_CLINICA_CRMV);
        int indexRazaoSocial = cursor.getColumnIndex(DBHelper.COL_CLINICA_RAZAO_SOCIAL);
        int indexAvaliacao = cursor.getColumnIndex(DBHelper.COL_CLINICA_AVALIACAO);
        Clinica clinica = new Clinica();
        clinica.setAvaliacao(cursor.getLong(indexAvaliacao));
        clinica.setId(cursor.getLong(indexId));
        clinica.setCrmv(cursor.getString(indexCrmv));
        clinica.setNome(cursor.getString(indexNome));
        clinica.setRazaoSocial(cursor.getString(indexRazaoSocial));

        return clinica;
    }


    public void alteraCrmv(Clinica clinica) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CLINICA_CRMV, clinica.getCrmv());
        db.update(DBHelper.TABELA_CLINICA, values, DBHelper.COL_CLINICA_ID + " = ?",
                new String[]{String.valueOf(clinica.getId())});
        db.close();
    }

}
