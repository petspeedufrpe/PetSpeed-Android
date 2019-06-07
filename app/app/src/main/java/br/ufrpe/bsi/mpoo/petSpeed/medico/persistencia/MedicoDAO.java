package br.ufrpe.bsi.mpoo.petspeed.medico.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petspeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petspeed.usuario.persistencia.UsuarioDAO;

public class MedicoDAO {

    private DBHelper helperDb = new DBHelper();


    public long cadastraMedico(Medico medico) {
        SQLiteDatabase dbWrite = helperDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_MEDICO_AVALIACAO, medico.getAvaliacao());
        values.put(DBHelper.COL_MEDICO_CRMV, medico.getCrmv());
        values.put(DBHelper.COL_MEDICO_FK_USUARIO, medico.getUsuario().getId());
        values.put(DBHelper.COL_MEDICO_FK_PESSOA, medico.getDadosPessoais().getId());
        long id = dbWrite.insert(DBHelper.TABELA_MEDICO, null, values);
        dbWrite.close();
        return id;
    }

    private Medico createMedico(Cursor cursor) {
        Medico medico = new Medico();
        PessoaDAO pessoaDAO = new PessoaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int indexID = cursor.getColumnIndex(DBHelper.COL_MEDICO_ID);
        int indexCRMV = cursor.getColumnIndex(DBHelper.COL_MEDICO_CRMV);
        int indexAvaliacao = cursor.getColumnIndex(DBHelper.COL_MEDICO_AVALIACAO);
        medico.setId(cursor.getLong(indexID));
        medico.setCrmv(cursor.getString(indexCRMV));
        medico.setAvaliacao(cursor.getDouble(indexAvaliacao));
        int indexfkUsuario = cursor.getColumnIndex(DBHelper.COL_MEDICO_FK_USUARIO);
        long fkUsuario = cursor.getLong(indexfkUsuario);
        Usuario usuario = usuarioDAO.getUsuario(fkUsuario);
        usuario.setId(fkUsuario);
        medico.setUsuario(usuario);

        return medico;
    }

    public Medico getMedicoByFkUsuario(Long fkUsuario) {
        SQLiteDatabase db = helperDb.getReadableDatabase();
        Medico medico = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_MEDICO + " WHERE " + DBHelper.COL_MEDICO_FK_USUARIO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(fkUsuario)});
        if (cursor.moveToFirst()) {
            medico = createMedico(cursor);
        }
        cursor.close();
        db.close();

        return medico;

    }

    public Medico getMedicoById(long idMedico){
        SQLiteDatabase db = helperDb.getReadableDatabase();
        Medico medico = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_MEDICO + " WHERE " + DBHelper.COL_MEDICO_ID+ " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idMedico)});
        if (cursor.moveToFirst()){
            medico = createMedico(cursor);
        }
        cursor.close();
        db.close();

        return medico;
    }


    public void deletaMedico(Medico medico) {
        SQLiteDatabase db = helperDb.getWritableDatabase();

        db.delete(DBHelper.TABELA_MEDICO,DBHelper.COL_MEDICO_ID+ " = ?",
                new String[]{String.valueOf(medico.getId())});
        db.close();
    }

    public Double getMedicoByFkPessoa(long idPessoa){
        SQLiteDatabase db = helperDb.getReadableDatabase();
        String sql = "SELECT AVALIACAO FROM " +DBHelper.TABELA_MEDICO+
                " WHERE "+DBHelper.COL_MEDICO_FK_PESSOA+ " = ?";
        String[] args={String.valueOf(idPessoa)};
        Cursor cursor = db.rawQuery(sql,args);
        if (cursor.moveToFirst()){
            int indexAvaliacao = cursor.getColumnIndex(DBHelper.COL_MEDICO_AVALIACAO);
            return cursor.getDouble(indexAvaliacao);
        }
        cursor.close();
        return null;
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


