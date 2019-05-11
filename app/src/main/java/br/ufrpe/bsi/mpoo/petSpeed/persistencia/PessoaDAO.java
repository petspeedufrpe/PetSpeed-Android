package br.ufrpe.bsi.mpoo.petSpeed.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.ufrpe.bsi.mpoo.petSpeed.dominio.Pessoa.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;

public class PessoaDAO {
	private  DBHelper dbHelper = new DBHelper();
	public long cadastraPessoa(Pessoa pessoa){ // Retorna o id da pessoa cadastrada
		long res;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_NOME_PESSOA,pessoa.getNome());
		values.put(DBHelper.COL_CPF_PESSOA, pessoa.getCpf());
		values.put(DBHelper.COL_ENDERECO_PESSOA,pessoa.getEndereco().getId());
		res = db.insert(DBHelper.TABELA_PESSOA,null,values);
		db.close();

		return res;
	}//Insere na tabela o nome e o cpf, o id é autoincrementado

	public void deletaPessoa(Pessoa pessoa) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		db.delete(DBHelper.TABELA_PESSOA,DBHelper.COL_ID_PESSOA + " = ?",new String[]{String.valueOf(pessoa.getId())});
		db.close();
	}

	public void alteraNome(Pessoa pessoa) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_NOME_PESSOA,pessoa.getNome());
		db.update(DBHelper.TABELA_PESSOA,values,DBHelper.COL_ID_PESSOA + " = ?", new String[]{String.valueOf(pessoa.getId())});
		db.close();//recupera a pessoa pelo ID_pessoa
	}

	public void alteraCPF(Pessoa pessoa) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_CPF_PESSOA,pessoa.getCpf());
		db.update(DBHelper.TABELA_PESSOA,values,DBHelper.COL_ID_PESSOA + " = ?", new String[]{String.valueOf(pessoa.getId())});
		db.close();//recupera a pessoa pelo ID_pessoa
	}

	private  Pessoa createPessoa(Cursor cursor){ //usa o cursor para recuperar os valores das colunas;
		Pessoa pessoa = new Pessoa();
		int indexId = cursor.getColumnIndex(DBHelper.COL_ID_PESSOA);
		int indexNome = cursor.getColumnIndex(DBHelper.COL_NOME_PESSOA);
		int indexCpf = cursor.getColumnIndex(DBHelper.COL_CPF_PESSOA);
		pessoa.setNome(cursor.getString(indexNome));
		pessoa.setId(cursor.getInt(indexId));
		pessoa.setCpf(cursor.getString(indexCpf));

		return pessoa;

	}

	public Pessoa getPessoaById(Long id){
		Pessoa pessoa = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM " + DBHelper.TABELA_PESSOA+ " WHERE " + DBHelper.COL_ID_PESSOA + " LIKE ?;";
		String[] args = {String.valueOf(id)};
		return this.loadObject(sql,args); /** pega o object da pessoa pelo id dela. usa a func loadObject
		 									  para criar a pessoa e assim retorna-la nessa funcao**/
	}

	private Pessoa loadObject(String sql, String [] args){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql,args);
		Pessoa pessoa = null;
		if(cursor.moveToFirst()){
			pessoa = createPessoa(cursor);
		}
		cursor.close();
		db.close();
		return pessoa;//passa a query sql e uma array com os campos do banco de dados para criar a pessoa com esses dados
	}
	/**duvidas
	 * pq é preciso fazer o createPessoa?
	 * Posso recuperar a pessoa pelo id?
	 * preciso de mais getters de pessoa com overload dos metodos ?
	 * **/


}
