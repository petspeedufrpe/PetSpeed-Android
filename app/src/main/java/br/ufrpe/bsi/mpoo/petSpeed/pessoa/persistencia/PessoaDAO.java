package br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;

public class PessoaDAO {
	private  DBHelper dbHelper = new DBHelper();
	public long cadastraPessoa(Pessoa pessoa){ // Retorna o id da pessoa cadastrada
		long res;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_PESSOA_NOME,pessoa.getNome());
		values.put(DBHelper.COL_PESSOA_CPF, pessoa.getCpf());
		values.put(DBHelper.COL_PESSOA_ENDERECO,pessoa.getEndereco().getId());
		res = db.insert(DBHelper.TABELA_PESSOA,null,values);
		db.close();

		return res;
	}//Insere na tabela o nome e o cpf, o id é autoincrementado

	public void deletaPessoa(Pessoa pessoa) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		db.delete(DBHelper.TABELA_PESSOA,DBHelper.COL_PESSOA_ID + " = ?",new String[]{String.valueOf(pessoa.getId())});
		db.close();
	}

	public void alteraNome(Pessoa pessoa) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_PESSOA_NOME,pessoa.getNome());
		db.update(DBHelper.TABELA_PESSOA,values,DBHelper.COL_PESSOA_ID + " = ?", new String[]{String.valueOf(pessoa.getId())});
		db.close();//recupera a pessoa pelo ID_pessoa
	}

	public void alteraCPF(Pessoa pessoa) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_PESSOA_CPF,pessoa.getCpf());
		db.update(DBHelper.TABELA_PESSOA,values,DBHelper.COL_PESSOA_ID + " = ?", new String[]{String.valueOf(pessoa.getId())});
		db.close();//recupera a pessoa pelo ID_pessoa
	}
	public void alteraEndereco(){

    }

	private  Pessoa createPessoa(Cursor cursor){ //usa o cursor para recuperar os valores das colunas;
		Pessoa pessoa = new Pessoa();
		int indexId = cursor.getColumnIndex(DBHelper.COL_PESSOA_ID);
		int indexNome = cursor.getColumnIndex(DBHelper.COL_PESSOA_NOME);
		int indexCpf = cursor.getColumnIndex(DBHelper.COL_PESSOA_CPF);
		pessoa.setNome(cursor.getString(indexNome));
		pessoa.setId(cursor.getInt(indexId));
		pessoa.setCpf(cursor.getString(indexCpf));

		return pessoa;

	}

	public Pessoa getPessoaById(Long id){
		Pessoa pessoa = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM " + DBHelper.TABELA_PESSOA+ " WHERE " + DBHelper.COL_PESSOA_ID + " LIKE ?;";
		String[] args = {String.valueOf(id)};
		return this.loadPessoa(sql,args); /** pega o object da pessoa pelo id dela. usa a func loadPessoa
		 									  para criar a pessoa e assim retorna-la nessa funcao**/
	}

	public Cursor getIdEnderecoByPessoa(Long idPessoa){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String query = "SELECT * FROM "+DBHelper.TABELA_PESSOA+ " WHERE "+
				DBHelper.COL_PESSOA_ENDERECO+ " LIKE ?;";
		String[] args = {String.valueOf(idPessoa)};
		return db.rawQuery(query,args);
	}

	private Pessoa loadPessoa(String sql, String [] args){
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
