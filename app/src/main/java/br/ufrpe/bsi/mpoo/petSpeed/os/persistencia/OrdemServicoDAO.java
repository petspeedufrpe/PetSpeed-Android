package br.ufrpe.bsi.mpoo.petSpeed.os.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petSpeed.os.dominio.Prioridade;

public class OrdemServicoDAO {

    private DBHelper dbHelper = new DBHelper();

    public long cadastraOS(OrdemServico ordemServico) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_OS_ID, ordemServico.getId());
        values.put(DBHelper.COL_OS_PENDENTE, ordemServico.isPendente());
        values.put(DBHelper.COL_OS_DESCRICAO, ordemServico.getDescricao());
        values.put(DBHelper.COL_OS_PRIORIDADE, String.valueOf(ordemServico.getPrioridade()));
        values.put(DBHelper.COL_OS_FK_ANIMAL, ordemServico.getAnimal().getId());
        values.put(DBHelper.COL_OS_FK_CLIENTE, ordemServico.getCliente().getId());
        values.put(DBHelper.COL_OS_FK_MEDICO, ordemServico.getMedico().getId());
        values.put(DBHelper.COL_OS_FK_TRIAGEM, ordemServico.getTriagem().getId());
        long id = db.insert(DBHelper.TABELA_OS, null, values);
        db.close();
        return id;
    }

    public void deletaOS(long id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABELA_OS,DBHelper.COL_OS_ID+" = ?",new String[]{String.valueOf(id)});
        db.close();

    }

    public OrdemServico getOSbyId(long idOs) {
        String sql = "SELECT * FROM "+DBHelper.TABELA_OS+" WHERE "+DBHelper.COL_OS_ID+ " =?";
        String[] args = {String.valueOf(idOs)};
        return this.loadObject(sql,args);
    }

    public OrdemServico createOS(Cursor cursor){
        OrdemServico ordemServico = new OrdemServico();
        int indexId = cursor.getColumnIndex(DBHelper.COL_OS_ID);
        int indexPendente = cursor.getColumnIndex(DBHelper.COL_OS_PENDENTE);
        int indexDescricao = cursor.getColumnIndex(DBHelper.COL_OS_DESCRICAO);
        ordemServico.setDescricao(cursor.getColumnName(indexDescricao));
        ordemServico.setPendente(Boolean.parseBoolean(cursor.getColumnName(indexPendente)));
        ordemServico.setId(Long.parseLong(cursor.getColumnName(indexId)));
        return ordemServico;
    }

    public List<OrdemServico> getOsByProridade (Prioridade p){
        String sql = "SELECT * FROM "+DBHelper.TABELA_OS+" WHERE "+DBHelper.COL_OS_PRIORIDADE+ " =?";
        String[] args = {String.valueOf(p)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,args);
        OrdemServico ordemServico = null;
        List<OrdemServico> osByPriority = new ArrayList<>();
        while (cursor.moveToFirst()){
            ordemServico = createOS(cursor);
            osByPriority.add(ordemServico);
        }
        cursor.close();
        db.close();
        return osByPriority;
    }

    public List<OrdemServico> getOsByAnimal (long id){
        String sql = "SELECT * FROM "+DBHelper.TABELA_OS+" WHERE "+DBHelper.COL_OS_FK_ANIMAL+ " =?";
        String[] args = {String.valueOf(id)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,args);
        OrdemServico ordemServico = null;
        List<OrdemServico> osByPriority = new ArrayList<>();
        while (cursor.moveToFirst()){
            ordemServico = createOS(cursor);
            osByPriority.add(ordemServico);
        }
        cursor.close();
        db.close();
        return osByPriority;
    }

    public OrdemServico loadObject(String sql,String[] args){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,args);
        OrdemServico ordemServico = null;
        if (cursor.moveToFirst()){
            ordemServico = createOS(cursor);
        }
        cursor.close();
        db.close();
        return ordemServico;
    }

}
