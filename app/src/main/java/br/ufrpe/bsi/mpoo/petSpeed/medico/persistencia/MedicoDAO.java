package br.ufrpe.bsi.mpoo.petSpeed.medico.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

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
        int indexFkPessoa = cursor.getColumnIndex(DBHelper.COL_MEDICO_FK_PESSOA);
        medico.setId(cursor.getLong(indexID));
        medico.setCrmv(cursor.getString(indexCRMV));
        medico.setAvaliacao(cursor.getDouble(indexAvaliacao));
        int indexfkUsuario = cursor.getColumnIndex(DBHelper.COL_MEDICO_FK_USUARIO);
        long fkUsuario = cursor.getLong(indexfkUsuario);
        Usuario usuario = usuarioDAO.getUsuario(fkUsuario);
        usuario.setId(fkUsuario);
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoaDAO.getPessoaById(cursor.getLong(indexFkPessoa)));

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

    public Medico getMedicoById(long idMedico) {
        SQLiteDatabase db = helperDb.getReadableDatabase();
        Medico medico = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_MEDICO + " WHERE " + DBHelper.COL_MEDICO_ID + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idMedico)});
        if (cursor.moveToFirst()) {
            medico = createMedico(cursor);
        }
        cursor.close();
        db.close();

        return medico;
    }


    public void deletaMedico(Medico medico) {
        SQLiteDatabase db = helperDb.getWritableDatabase();

        db.delete(DBHelper.TABELA_MEDICO, DBHelper.COL_MEDICO_ID + " = ?",
                new String[]{String.valueOf(medico.getId())});
        db.close();
    }

    public Medico getMedicoByFkPessoa(long idPessoa) {
        SQLiteDatabase db = helperDb.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_MEDICO +
                " WHERE " + DBHelper.COL_MEDICO_FK_PESSOA + " = ?";
        String[] args = {String.valueOf(idPessoa)};
        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            return createMedico(cursor);
        }
        cursor.close();
        return null;
    }

    String makePlaceholders(int len) {
        if (len < 1) {
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    public List<Medico> getMultipleMedicoById(List<Long> indicesPessoas) {
        // NÃO USAR! precisa de implementar o String[] args que seja de tamanho genérico.
        String sql = "SELECT * FROM " + DBHelper.TABELA_MEDICO + " WHERE " + DBHelper.COL_MEDICO_FK_PESSOA + " IN (" + makePlaceholders(indicesPessoas.size()) + ");";
        String[] args = {};
        SQLiteDatabase db = helperDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        Medico medico = null;
        List<Medico> medicos = new LinkedList<>();
        if (cursor.moveToFirst()) {
            do {
                medico = createMedico(cursor);
                medicos.add(medico);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return medicos;
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


