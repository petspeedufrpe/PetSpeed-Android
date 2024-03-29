package br.ufrpe.bsi.mpoo.petspeed.os.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.animal.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.medico.persistencia.MedicoDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class OrdemServicoDAO {

    private static final String SQL_SELECT_FROM = "SELECT * FROM ";
    private static final String SQL_WHERE = " WHERE ";
    private DBHelper dbHelper = new DBHelper();

    public long cadastraOS(OrdemServico ordemServico) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_OS_STATUS, ordemServico.getStatus().getDescricao());
        values.put(DBHelper.COL_OS_DESCRICAO, ordemServico.getDescricao());
        values.put(DBHelper.COL_OS_PRIORIDADE, String.valueOf(ordemServico.getPrioridade()));
        values.put(DBHelper.COL_OS_FK_ANIMAL, ordemServico.getAnimal().getId());
        values.put(DBHelper.COL_OS_FK_CLIENTE, ordemServico.getCliente().getId());
        values.put(DBHelper.COL_OS_FK_TRIAGEM, ordemServico.getTriagem().getId());
        values.put(DBHelper.COL_OS_FK_MEDICO, ordemServico.getMedico().getId());
        values.put(DBHelper.COL_OS_DATA,new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ordemServico.getData()));
        long id = db.insert(DBHelper.TABELA_OS, null, values);
        db.close();
        return id;
    }

    public void deletaOS(long id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABELA_OS, DBHelper.COL_OS_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();

    }

    public void insereMedico(Medico medico, long osId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_OS_FK_MEDICO, medico.getId());
        db.update(DBHelper.TABELA_OS, values, DBHelper.COL_OS_ID + " = ?",
                new String[]{String.valueOf(medico.getId())});
        db.close();
    }

    public OrdemServico getOSbyId(long idOs) {
        String sql = SQL_SELECT_FROM + DBHelper.TABELA_OS + SQL_WHERE + DBHelper.COL_OS_ID + " = ?";
        String[] args = {String.valueOf(idOs)};
        return this.loadObject(sql, args);
    }

    public OrdemServico getOsByIdMedico(Medico medico){
        String sql = SQL_SELECT_FROM+DBHelper.TABELA_OS + SQL_WHERE+ DBHelper.COL_OS_FK_MEDICO+ " = ?";
        String[] args = {String.valueOf(medico.getId())};

        return this.loadObject(sql,args);
    }

    private OrdemServico createOS(Cursor cursor) {
        OrdemServico ordemServico = new OrdemServico();
        int indexId = cursor.getColumnIndex(DBHelper.COL_OS_ID);
        int indexPendente = cursor.getColumnIndex(DBHelper.COL_OS_STATUS);
        int indexDescricao = cursor.getColumnIndex(DBHelper.COL_OS_DESCRICAO);
        int indexPrioridade = cursor.getColumnIndex(DBHelper.COL_OS_PRIORIDADE);
        int indexFkAnimal = cursor.getColumnIndex(DBHelper.COL_OS_FK_ANIMAL);
        int indexFkCliente = cursor.getColumnIndex(DBHelper.COL_OS_FK_CLIENTE);
        int indexFkMedico = cursor.getColumnIndex(DBHelper.COL_OS_FK_MEDICO);
        int indexFkTriagem = cursor.getColumnIndex(DBHelper.COL_OS_FK_TRIAGEM);
        int indexData = cursor.getColumnIndex(DBHelper.COL_OS_DATA);
        ordemServico.setDescricao(cursor.getString(indexDescricao));
        String prioridade = cursor.getString(indexPrioridade);
        ordemServico.setPrioridade(OrdemServico.Prioridade.valueOf(prioridade));
        String status = cursor.getString(indexPendente);
        ordemServico.setStatus(OrdemServico.Status.valueOf(status));
        ordemServico.setId(cursor.getLong(indexId));
        ordemServico.setAnimal(new AnimalDAO().getAnimalById(cursor.getLong(indexFkAnimal)));
        ordemServico.setCliente(new ClienteServices().getClienteCompleto(cursor.getLong(indexFkCliente)));
        ordemServico.setMedico(new MedicoDAO().getMedicoById(cursor.getLong(indexFkMedico)));
        ordemServico.setTriagem(new TriagemDAO().getTriagembyId(cursor.getLong(indexFkTriagem)));
        String data = cursor.getString(indexData);
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(data);
        } catch (ParseException e) {
            Log.d("TagOS",e.toString());
        }
        ordemServico.setData(date);
        return ordemServico;
    }

    public void alterarStatus(OrdemServico ordemServico){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_OS_STATUS,ordemServico.getStatus().getDescricao());
        db.update(DBHelper.TABELA_OS, values, DBHelper.COL_OS_ID + " = ?", new String[]{String.valueOf(ordemServico.getId())});
        db.close();
    }

    public List<OrdemServico> getOsByProridade(long idMedico, OrdemServico.Prioridade p) {
        String sql = SQL_SELECT_FROM + DBHelper.TABELA_OS + SQL_WHERE + DBHelper.COL_OS_FK_MEDICO + " = ?"+ " AND "+DBHelper.COL_OS_PRIORIDADE + " = ?"+
                " ORDER BY "+ DBHelper.COL_OS_ID + " DESC";
        String[] args = {String.valueOf(idMedico),p.getDescricao()};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        OrdemServico ordemServico;
        List<OrdemServico> osByPriority = new LinkedList<>();
        if (cursor.moveToFirst()) {
            do {

                ordemServico = createOS(cursor);
                osByPriority.add(ordemServico);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return osByPriority;
    }

    public List<OrdemServico> getOsByAnimal(long id) {
        String sql = SQL_SELECT_FROM + DBHelper.TABELA_OS + SQL_WHERE + DBHelper.COL_OS_FK_ANIMAL + " = ?";
        String[] args = {String.valueOf(id)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        OrdemServico ordemServico;
        List<OrdemServico> osByPriority = new LinkedList<>();
        if (cursor.moveToFirst()) {
            do {
                ordemServico = createOS(cursor);
                osByPriority.add(ordemServico);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return osByPriority;
    }

    public List<OrdemServico> getOsByIdMedico(long idMedico){
        String sql = SQL_SELECT_FROM + DBHelper.TABELA_OS + SQL_WHERE + DBHelper.COL_OS_FK_MEDICO + " = ?"+" ORDER BY "+ DBHelper.COL_OS_ID+" DESC";
        String[] args = {String.valueOf(idMedico)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        OrdemServico ordemServico;
        List<OrdemServico> osByMedico = new LinkedList<>();
        if (cursor.moveToFirst()){
            do {
                ordemServico = createOS(cursor);
                osByMedico.add(ordemServico);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return osByMedico;
    }

    private OrdemServico loadObject(String sql, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        OrdemServico ordemServico = null;
        if (cursor.moveToFirst()) {
            ordemServico = createOS(cursor);
        }
        cursor.close();
        db.close();
        return ordemServico;
    }

    public OrdemServico getOsByIdCliente(long idCliente) {
        String aguardando = OrdemServico.Status.AGUARDANDO_ATENDIMENTO.getDescricao();
        String emAtendimento = OrdemServico.Status.EM_ATENDIMENTO.getDescricao();
        String sql = SQL_SELECT_FROM + DBHelper.TABELA_OS + SQL_WHERE + DBHelper.COL_OS_FK_CLIENTE + " = ?"
                + " AND "+DBHelper.COL_OS_STATUS + " = ?" + " OR "+ DBHelper.COL_OS_STATUS+ " = ?";
        String[] args = {String.valueOf(idCliente),aguardando,emAtendimento};

        return this.loadObject(sql,args);
    }

    public List<OrdemServico> getOsByIdCliente(Cliente cliente){
        String sql = SQL_SELECT_FROM + DBHelper.TABELA_OS + SQL_WHERE + DBHelper.COL_OS_FK_CLIENTE + " = ?";
        String[] args = {String.valueOf(cliente.getId())};
        return loadAllOs(sql,args);
    }

    public List<OrdemServico> loadAllOs(String sql,String[] args){
        List<OrdemServico> ordemServicos = new ArrayList<>();
        OrdemServico ordemServico= null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,args);
        if (cursor.moveToFirst()){
            do {
                ordemServico = createOS(cursor);
                ordemServicos.add(ordemServico);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return ordemServicos;
    }

}
