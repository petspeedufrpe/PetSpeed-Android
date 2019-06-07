package br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petspeed.usuario.persistencia.UsuarioDAO;

public class ClienteDAO {

    private DBHelper dbHelper = new DBHelper();


    public long cadastraCliente(Cliente cliente) {
        long res;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CLIENTE_AVALIACAO, cliente.getAvaliacao());
        values.put(DBHelper.COL_CLIENTE_FK_USUARIO, cliente.getUsuario().getId());
        values.put(DBHelper.COL_CLIENTE_FK_PESSOA, cliente.getDadosPessoais().getId());
        res = db.insert(DBHelper.TABELA_CLIENTE, null, values);
        db.close();
        return res;

    }

    public Cliente getClienteByFkUsuario(Long fkUsuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cliente cliente = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_CLIENTE + " WHERE " + DBHelper.COL_CLIENTE_FK_USUARIO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(fkUsuario)});
        if (cursor.moveToFirst()) {
            cliente = createCliente(cursor);
        }
        cursor.close();
        db.close();

        return cliente;

    }

    public void deletaCliente(Cliente cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABELA_CLIENTE, DBHelper.COL_CLIENTE_ID + " = ?", new String[]{String.valueOf(cliente.getId())});
        db.close();

    }

    //guarda o indice de cada coluna necessária para montar o obj, e depois passa em uma variavel o valor retornado do banco
    //seta no objeto e retorna ele.
    private Cliente createCliente(Cursor cursor) {
        int indexId = cursor.getColumnIndex(DBHelper.COL_CLIENTE_ID);
        int indexAvaliacao = cursor.getColumnIndex(DBHelper.COL_CLIENTE_AVALIACAO);
        int indexIdUsuario = cursor.getColumnIndex(DBHelper.COL_CLIENTE_FK_USUARIO);
        int indexIdPessoa = cursor.getColumnIndex(DBHelper.COL_CLIENTE_FK_PESSOA);
        long id = cursor.getLong(indexId);
        long avaliacao = cursor.getLong(indexAvaliacao);
        long idUsuario = cursor.getLong(indexIdUsuario);
        long idPessoa = cursor.getLong(indexIdPessoa);
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setAvaliacao(avaliacao);
        cliente.setIdUsuario(idUsuario);
        cliente.setIdPessoa(idPessoa);
        cursor.close();
        return cliente;
    }

    public Cliente loadObject(String query, String[] args) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);
        Cliente cliente = null;
        if (cursor.moveToFirst()) {
            cliente = createCliente(cursor);
        }
        cursor.close();
        db.close();

        return cliente;
    }

    /**
     * @param id
     * @return Cliente
     * nesse metodo so inicia a getString da query e o arg que será passado.
     * no loadCliente o banco é aberto para leitura e retorna um cursor para poder criar o cliente
     */
    public Cliente getClienteById(Long id) {
        String query = "SELECT * FROM " + DBHelper.TABELA_CLIENTE + " WHERE " + DBHelper.COL_CLIENTE_ID + " LIKE ?;";
        String[] args = {String.valueOf(id)};
        return this.loadObject(query, args);
    }

    public Cursor getIdObjectByCliente(Long idCliente) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DBHelper.TABELA_CLIENTE + " WHERE " + DBHelper.COL_CLIENTE_ID +
                " LIKE ?;";
        String[] args = {String.valueOf(idCliente)};

        return db.rawQuery(query, args); //Metodo para ser usado na classe de negocio, que usa o id do cliente
        // para recuperar o objeto inteiro(todas as outras classes
        // ex.(pessoa,usuario e endereco podem ser acessadas).
    }

    public Cliente getIdClienteByUsuario(long idUsuario) {
        String query = " SELECT * FROM " + DBHelper.TABELA_CLIENTE + " WHERE " + DBHelper.COL_CLIENTE_FK_USUARIO +
                " LIKE ?;";
        String[] args = {String.valueOf(idUsuario)};
        return this.loadObject(query, args);
    }

    public Cliente getClienteByEmail(String email) {//Passando um email como parametro, recupera o cliente
        UsuarioDAO usuarioDAO = new UsuarioDAO();  // que está atribuido a este email de um usuario(classe)
        Usuario usuario = usuarioDAO.getUsuario(email);//retorna o usuario que tem este email
        Cliente cliente = getIdClienteByUsuario(usuario.getId());//retorna o cliente que tem este usuario
        return cliente;//este metodo tem que ir para o services de cliente
    }


    public void alteraAvaliacao(Cliente cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CLIENTE_AVALIACAO, cliente.getAvaliacao());
        db.update(DBHelper.TABELA_CLIENTE, values, DBHelper.COL_CLIENTE_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();

    }

}
