package br.ufrpe.bsi.mpoo.petSpeed.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;

import br.ufrpe.bsi.mpoo.petSpeed.dominio.Endereco.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;

public class EnderecoDAO {

	private DBHelper dbHelper = new DBHelper();

	public long cadastraEndereco(Endereco endereco) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long res;
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_CEP_ENDERECO, endereco.getCep());
		values.put(DBHelper.COL_NUMERO_ENDERECO, endereco.getNumero());
		values.put(DBHelper.COL_COMPLEMENTO_ENDERECO, endereco.getComplemento());
		res = db.insert(DBHelper.TABELA_ENDERECO,null,values);
		db.close();

		return  res;
	}

	public void deletaEndereco(Endereco endereco) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		db.delete(DBHelper.TABELA_ENDERECO, DBHelper.COL_ID_ENDERECO + " = ?", new String[]
				{String.valueOf(endereco.getId())});
		db.close();

	}

	public void alteraCep(Endereco endereco) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_CPF_PESSOA, endereco.getNumero());
		db.update(DBHelper.TABELA_ENDERECO, values, "id = ?", new String[]{String.valueOf(endereco.getId())});
		db.close();


	}

	public void alteraNumero(Endereco endereco) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_NUMERO_ENDERECO, endereco.getNumero());
		db.update(DBHelper.TABELA_ENDERECO, values, "id = ?", new String[]{String.valueOf(endereco.getId())});
		db.close();

	}

	public void alteraComplemento(Endereco endereco) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_COMPLEMENTO_ENDERECO,endereco.getComplemento());
		db.update(DBHelper.TABELA_ENDERECO,values,DBHelper.COL_ID_ENDERECO + " = ?",new String[]{String.valueOf(endereco.getId())});
		db.close();

	}

	public Endereco getEnderecoById(Integer id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Endereco endereco = null;
		String sql = "SELECT * FROM " + DBHelper.TABELA_ENDERECO + "U WHERE U." + DBHelper.COL_ID_ENDERECO + " LIKE ?;";
		String[] args = {String.valueOf(id)};
		return this.loadEndereco(sql,args);
	}

	private Endereco createEndereco(Cursor cursor) {
		Endereco endereco = new Endereco();
		int indexId = cursor.getColumnIndex(DBHelper.COL_ID_ENDERECO);
		int indexCep = cursor.getColumnIndex(DBHelper.COL_CEP_ENDERECO);
		int indexNumero = cursor.getColumnIndex(DBHelper.COL_NUMERO_ENDERECO);
		int indexComplemento = cursor.getColumnIndex(DBHelper.COL_COMPLEMENTO_ENDERECO);
		endereco.setId(cursor.getInt(indexId));
		endereco.setCep(cursor.getString(indexCep));
		endereco.setNumero(cursor.getInt(indexNumero));
		endereco.setComplemento(cursor.getString(indexComplemento));
		return endereco;


	}

	private Endereco loadEndereco(String sql, String [] args){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql,args);
		Endereco endereco = null;
		if(cursor.moveToFirst()){
			endereco = createEndereco(cursor);
		}
		cursor.close();
		db.close();
		return endereco;//passa a query sql e uma array com os campos do banco de dados para criar a pessoa com esses dados
	}
}