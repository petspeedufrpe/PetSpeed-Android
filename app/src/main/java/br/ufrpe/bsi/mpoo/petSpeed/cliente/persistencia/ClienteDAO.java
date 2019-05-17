package br.ufrpe.bsi.mpoo.petSpeed.cliente.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class ClienteDAO {

	private DBHelper dbHelper = new DBHelper();

	public long cadastraCliente(Cliente cliente) {
		long res;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_CLIENTE_AVALIACAO,cliente.getAvaliacao());
		values.put(DBHelper.COL_CLIENTE_FK_USUARIO,cliente.getUsuario().getId());
		values.put(DBHelper.COL_CLIENTE_FK_PESSOA,cliente.getDadosPessoais().getId());
		res = db.insert(DBHelper.TABELA_CLIENTE,null,values);
		db.close();
		return  res;

	}

	public void deletaCliente(Cliente cliente) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(DBHelper.TABELA_CLIENTE,DBHelper.COL_CLIENTE_ID + " = ?",new String[]{String.valueOf(cliente.getId())});
		db.close();

	}

	private Cliente createCliente(Cursor cursor){
		int indexId = cursor.getColumnIndex(DBHelper.COL_CLIENTE_ID);
		int indexAvaliacao = cursor.getColumnIndex(DBHelper.COL_CLIENTE_AVALIACAO);
		long id = cursor.getLong(indexId);
		long avaliacao = cursor.getLong(indexAvaliacao);
		Cliente cliente = new Cliente();
		cliente.setId(id);
		cliente.setAvaliacao(avaliacao);
		return cliente;
	}

	public Cliente loadCliente(String query , String[] args){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(query,args);
		Cliente cliente = null;
		if (cursor.moveToNext()){
			cliente = createCliente(cursor);
		}
		cursor.close();
		db.close();

		return cliente;
	}

	public Cliente getClienteById(Long id){
		String query = "SELECT * FROM " + DBHelper.TABELA_CLIENTE+" WHERE "+DBHelper.COL_CLIENTE_ID+ " LIKE ?;";
		String[] args = {String.valueOf(id)};
		return this.loadCliente(query,args);
	}

	public Cursor getIdObjectByCliente(Long idCliente){
		SQLiteDatabase db= dbHelper.getReadableDatabase();
		String query = "SELECT * FROM "+DBHelper.TABELA_CLIENTE+ " WHERE "+DBHelper.COL_CLIENTE_ID+
				" LIKE ?;";
		String[] args = {String.valueOf(idCliente)};

		return db.rawQuery(query,args); //Metodo para ser usado na classe de negocio, que usa o id do cliente
										// para recuperar o objeto inteiro(todas as outras classes
										// ex.(pessoa,usuario e endereco podem ser acessadas).
	}

	public Cliente getIdClienteByUsuario(long idUsuario){
		String query = " SELECT * FROM "+DBHelper.TABELA_CLIENTE+" WHERE "+DBHelper.COL_CLIENTE_FK_USUARIO+
				" LIKE ?;";
		String[] args = {String.valueOf(idUsuario)};
		return this.loadCliente(query,args);
	}
	public Cliente getClienteByEmail(String email){//Passando um email como parametro, recupera o cliente
		UsuarioDAO usuarioDAO = new UsuarioDAO();  // que está atribuido a este email de um usuario(classe)
		Usuario usuario = usuarioDAO.getUsuario(email);//retorna o usuario que tem este email
		Cliente cliente= getIdClienteByUsuario(usuario.getId());//retorna o cliente que tem este usuario
		return cliente;
	}


	public Animal getAnimalById(long idCliente) {

		return null;
	}

	public Animal getAnimalByRaca() {
		return null;
	}

	public List<Endereco> getAllEndereco() {
		return null;
	}

	public List<Animal> getAllAnimal() {
		return null;
	}

	public void removeEndereco() {

	}

	public void adicionaEndereco() {

	}

	public void alteraEmail() {


	}

	public void alteraSenha() {

	}

	public void alteraAvaliacao(Cliente cliente) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_CLIENTE_AVALIACAO,cliente.getAvaliacao());
		db.update(DBHelper.TABELA_CLIENTE,values,DBHelper.COL_CLIENTE_ID+ " = ?",
				new String[]{String.valueOf(cliente.getId())});
		db.close();

	}

}
