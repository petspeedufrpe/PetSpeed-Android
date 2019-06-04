package br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;

public class EnderecoDAO {

    private DBHelper dbHelper = new DBHelper();

    public long cadastraEndereco(Endereco endereco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long res;
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ENDERECO_CEP, endereco.getCep());
        values.put(DBHelper.COL_ENDERECO_UF, endereco.getUf());
        values.put(DBHelper.COL_ENDERECO_CIDADE, endereco.getCidade());
        values.put(DBHelper.COL_ENDERECO_BAIRRO, endereco.getBairro());
        values.put(DBHelper.COL_ENDERECO_LOGRADOURO, endereco.getLogradouro());
        values.put(DBHelper.COL_ENDERECO_NUMERO, endereco.getNumero());
        values.put(DBHelper.COL_ENDERECO_COMPLEMENTO, endereco.getComplemento());
        values.put(DBHelper.COL_ENDERECO_FK_CLINICA, endereco.getFkClinica());
        values.put(DBHelper.COL_ENDERECO_FK_PESSOA, endereco.getFkPessoa());
        values.put(DBHelper.COL_ENDERECO_LATITUTDE,endereco.getLatidude());
        values.put(DBHelper.COL_ENDERECO_LONGITUDE,endereco.getLongitude());
        res = db.insert(DBHelper.TABELA_ENDERECO, null, values);
        db.close();

        return res;
    }

    public void deletaEndereco(Endereco endereco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.TABELA_ENDERECO, DBHelper.COL_ENDERECO_ID + " = ?", new String[]
                {String.valueOf(endereco.getId())});
        db.close();

    }

    public void alteraCep(Endereco endereco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_PESSOA_CPF, endereco.getNumero());
        db.update(DBHelper.TABELA_ENDERECO, values, DBHelper.COL_ENDERECO_CEP + " = ?", new String[]{String.valueOf(endereco.getId())});
        db.close();


    }

    public void alteraUf(Endereco endereco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ENDERECO_UF, endereco.getComplemento());
        db.update(DBHelper.TABELA_ENDERECO, values, DBHelper.COL_ENDERECO_ID + " = ?", new String[]{String.valueOf(endereco.getId())});
        db.close();

    }

    public void alteraCidade(Endereco endereco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ENDERECO_CIDADE, endereco.getComplemento());
        db.update(DBHelper.TABELA_ENDERECO, values, DBHelper.COL_ENDERECO_ID + " = ?", new String[]{String.valueOf(endereco.getId())});
        db.close();

    }

    public void alteraBairro(Endereco endereco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ENDERECO_BAIRRO, endereco.getComplemento());
        db.update(DBHelper.TABELA_ENDERECO, values, DBHelper.COL_ENDERECO_ID + " = ?", new String[]{String.valueOf(endereco.getId())});
        db.close();

    }

    public void alteraLogradouro(Endereco endereco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ENDERECO_LOGRADOURO, endereco.getComplemento());
        db.update(DBHelper.TABELA_ENDERECO, values, DBHelper.COL_ENDERECO_ID + " = ?", new String[]{String.valueOf(endereco.getId())});
        db.close();


    }

    public void alteraNumero(Endereco endereco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ENDERECO_NUMERO, endereco.getNumero());
        db.update(DBHelper.TABELA_ENDERECO, values, DBHelper.COL_ENDERECO_NUMERO + " = ?", new String[]{String.valueOf(endereco.getId())});
        db.close();

    }

    public void alteraComplemento(Endereco endereco) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_ENDERECO_COMPLEMENTO, endereco.getComplemento());
        db.update(DBHelper.TABELA_ENDERECO, values, DBHelper.COL_ENDERECO_ID + " = ?", new String[]{String.valueOf(endereco.getId())});
        db.close();

    }

    public Endereco getEnderecoById(Long id) {
        String sql = "SELECT * FROM " + DBHelper.TABELA_ENDERECO + " WHERE " + DBHelper.COL_ENDERECO_ID + " LIKE ?;";
        String[] args = {String.valueOf(id)};
        return this.loadEndereco(sql, args);
    }

    public Cursor getIdPessoaByEndereco(Long idEndereco) {
        return null;
    }

    private Endereco createEndereco(Cursor cursor) {
        Endereco endereco = new Endereco();
        int indexId = cursor.getColumnIndex(DBHelper.COL_ENDERECO_ID);
        int indexCep = cursor.getColumnIndex(DBHelper.COL_ENDERECO_CEP);
        int indexUF = cursor.getColumnIndex(DBHelper.COL_ENDERECO_UF);
        int indexCidade = cursor.getColumnIndex(DBHelper.COL_ENDERECO_CIDADE);
        int indexBairro = cursor.getColumnIndex(DBHelper.COL_ENDERECO_BAIRRO);
        int indexLogradouro = cursor.getColumnIndex(DBHelper.COL_ENDERECO_LOGRADOURO);
        int indexNumero = cursor.getColumnIndex(DBHelper.COL_ENDERECO_NUMERO);
        int indexComplemento = cursor.getColumnIndex(DBHelper.COL_ENDERECO_COMPLEMENTO);
        int indexLatitutde = cursor.getColumnIndex(DBHelper.COL_ENDERECO_LATITUTDE);
        int indexLongitude = cursor.getColumnIndex(DBHelper.COL_ENDERECO_LONGITUDE);
        int indexFkPessoa = cursor.getColumnIndex(DBHelper.COL_ENDERECO_FK_PESSOA);
        int indexFkClinica = cursor.getColumnIndex(DBHelper.COL_ENDERECO_FK_CLINICA);
        endereco.setId(cursor.getInt(indexId));
        endereco.setCep(cursor.getString(indexCep));
        endereco.setUf(cursor.getString(indexUF));
        endereco.setCidade(cursor.getString(indexCidade));
        endereco.setBairro(cursor.getString(indexBairro));
        endereco.setLogradouro(cursor.getString(indexLogradouro));
        endereco.setNumero(cursor.getInt(indexNumero));
        endereco.setComplemento(cursor.getString(indexComplemento));
        endereco.setLatidude(cursor.getDouble(indexLatitutde));
        endereco.setLongitude(cursor.getDouble(indexLongitude));
        endereco.setFkPessoa(cursor.getLong(indexFkPessoa));
        endereco.setFkClinica(cursor.getLong(indexFkClinica));
        return endereco;
    }

    private Endereco loadEndereco(String sql, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        Endereco endereco = null;
        if (cursor.moveToFirst()) {
            endereco = createEndereco(cursor);
        }
        cursor.close();
        db.close();
        return endereco;//passa a query sql e uma array com os campos do banco de dados para criar a pessoa com esses dados
    }

    public ArrayList<Endereco> getAllAddressByBairro(String arg){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Endereco> enderecoArrayList = new ArrayList<>();
        String sql = "SELECT * FROM "+DBHelper.TABELA_ENDERECO+", "+DBHelper.TABELA_MEDICO+" WHERE "+DBHelper.COL_ENDERECO_BAIRRO+
                " = ?";
        String args[] = {arg};
        Cursor cursor = db.rawQuery(sql,args);
        Endereco endereco = null;
        if (cursor.moveToFirst()){
            do
                {
                endereco = createEndereco(cursor);
                enderecoArrayList.add(endereco);
            }while (cursor.moveToNext());
            return  enderecoArrayList;
        }

        return null;
    }
}