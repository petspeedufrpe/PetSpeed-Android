package br.ufrpe.bsi.mpoo.petspeed.pessoa.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Pessoa;

public class PessoaDAO {
    private static final String SQL_SELECT_FROM = "SELECT * FROM ";
    private static final String SQL_WHERE = " WHERE ";
    private static final String SQL_LIKE = " LIKE ?;";
    private DBHelper dbHelper = new DBHelper();

    public long cadastraPessoa(Pessoa pessoa) { // Retorna o id da pessoa cadastrada
        long res;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_PESSOA_NOME, pessoa.getNome());
        values.put(DBHelper.COL_PESSOA_CPF, pessoa.getCpf());
        values.put(DBHelper.COL_PESSOA_FK_USUARIO, pessoa.getIdUsuario());
        res = db.insert(DBHelper.TABELA_PESSOA, null, values);
        db.close();

        return res;
    }//Insere na tabela o nome e o cpf, o id é autoincrementado

    public void deletaPessoa(Pessoa pessoa) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.TABELA_PESSOA, DBHelper.COL_PESSOA_ID + " = ?", new String[]{String.valueOf(pessoa.getId())});
        db.close();
    }

    public void alteraNome(Pessoa pessoa) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_PESSOA_NOME, pessoa.getNome());
        db.update(DBHelper.TABELA_PESSOA, values, DBHelper.COL_PESSOA_ID + " = ?", new String[]{String.valueOf(pessoa.getId())});
        db.close();//recupera a pessoa pelo ID_pessoa
    }

    public void alteraCPF(Pessoa pessoa) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_PESSOA_CPF, pessoa.getCpf());
        db.update(DBHelper.TABELA_PESSOA, values, DBHelper.COL_PESSOA_ID + " = ?", new String[]{String.valueOf(pessoa.getId())});
        db.close();//recupera a pessoa pelo ID_pessoa
    }

    private Pessoa createPessoa(Cursor cursor) {
        Pessoa pessoa = new Pessoa();
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        int indexId = cursor.getColumnIndex(DBHelper.COL_PESSOA_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.COL_PESSOA_NOME);
        int indexCpf = cursor.getColumnIndex(DBHelper.COL_PESSOA_CPF);
        pessoa.setNome(cursor.getString(indexNome));
        pessoa.setId(cursor.getInt(indexId));
        pessoa.setCpf(cursor.getString(indexCpf));
        pessoa.setEndereco(enderecoDAO.getEnderecoByFkPessoa(pessoa.getId()));

        return pessoa;

    }

    public Pessoa getPessoaById(Long id) {
        String sql = SQL_SELECT_FROM + DBHelper.TABELA_PESSOA + SQL_WHERE + DBHelper.COL_PESSOA_ID + SQL_LIKE;
        String[] args = {String.valueOf(id)};
        return this.loadObject(sql, args); /** pega o object da pessoa pelo id dela. usa a func loadObject
         para criar a pessoa e assim retorna-la nessa funcao**/
    }

    public List<Pessoa> getMultiplePessoaById(List<Long> indicesPessoas) {
        StringBuilder strIndexes = new StringBuilder();
        for (int i = 0; i < indicesPessoas.size() - 1; i++) {
            strIndexes.append(indicesPessoas.get(i));
            strIndexes.append(", ");
        }
        strIndexes.append(indicesPessoas.get(indicesPessoas.size() - 1));

        String sql = SQL_SELECT_FROM + DBHelper.TABELA_PESSOA + SQL_WHERE + DBHelper.COL_PESSOA_ID + " IN (?);";
        String[] args = {strIndexes.toString()};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        Pessoa pessoa = null;
        List<Pessoa> pessoas = new LinkedList<>();
        if (cursor.moveToFirst()) {
            do {
                pessoa = createPessoa(cursor);
                pessoas.add(pessoa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pessoas;
    }

    public Pessoa getPessoaByFkUsuario(Long id) {
        String sql = SQL_SELECT_FROM + DBHelper.TABELA_PESSOA + SQL_WHERE + DBHelper.COL_PESSOA_FK_USUARIO + SQL_LIKE;
        String[] args = {String.valueOf(id)};
        return this.loadObject(sql, args);
    }

    public Cursor getIdEnderecoByPessoa(Long idPessoa) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = SQL_SELECT_FROM + DBHelper.TABELA_ENDERECO + SQL_WHERE +
                DBHelper.COL_ENDERECO_FK_PESSOA + SQL_LIKE;
        String[] args = {String.valueOf(idPessoa)};
        return db.rawQuery(query, args);
    }

    private Pessoa loadObject(String sql, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        Pessoa pessoa = null;
        if (cursor.moveToFirst()) {
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
