package br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petspeed.usuario.persistencia.UsuarioDAO;

public class ClienteDAO {

    public static final String SQL_SELECT_ALL_FROM = "SELECT * FROM ";
    public static final String SQL_WHERE = " WHERE ";
    public static final String SQL_LIKE = " LIKE ?;";
    private DBHelper dbHelper = new DBHelper();


    public long cadastraCliente(Cliente cliente) {
        long res;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CLIENTE_AVALIACAO, cliente.getAvaliacao());
        values.put(DBHelper.COL_CLIENTE_TELEFONE, cliente.getTelefone());
        values.put(DBHelper.COL_CLIENTE_FK_USUARIO, cliente.getUsuario().getId());
        values.put(DBHelper.COL_CLIENTE_FK_PESSOA, cliente.getDadosPessoais().getId());
        values.put(DBHelper.COL_CLIENTE_FOTO,cliente.getFoto());
        res = db.insert(DBHelper.TABELA_CLIENTE, null, values);
        db.close();
        return res;

    }

    public Cliente getClienteByFkUsuario(Long fkUsuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cliente cliente = null;
        String sql = SQL_SELECT_ALL_FROM + DBHelper.TABELA_CLIENTE + SQL_WHERE + DBHelper.COL_CLIENTE_FK_USUARIO + SQL_LIKE;
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(fkUsuario)});
        if (cursor.moveToFirst()) {
            cliente = createCliente(cursor);
        }
        cursor.close();
        db.close();

        return cliente;

    }

    public void deletaCliente(Cliente cliente) throws AppException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.delete(DBHelper.TABELA_CLIENTE, DBHelper.COL_CLIENTE_ID + " = ?", new String[]{String.valueOf(cliente.getId())});
        }catch(Exception e){
            throw new AppException("Erro ao deletar");
        }
        db.close();

    }

    private Cliente createCliente(Cursor cursor) {
        int indexId = cursor.getColumnIndex(DBHelper.COL_CLIENTE_ID);
        int indexAvaliacao = cursor.getColumnIndex(DBHelper.COL_CLIENTE_AVALIACAO);
        int indexTelefone = cursor.getColumnIndex(DBHelper.COL_CLIENTE_TELEFONE);
        int indexFoto = cursor.getColumnIndex(DBHelper.COL_CLIENTE_FOTO);
        int indexIdUsuario = cursor.getColumnIndex(DBHelper.COL_CLIENTE_FK_USUARIO);
        int indexIdPessoa = cursor.getColumnIndex(DBHelper.COL_CLIENTE_FK_PESSOA);
        long id = cursor.getLong(indexId);
        long avaliacao = cursor.getLong(indexAvaliacao);
        String telefone = cursor.getString(indexTelefone);
        byte[] foto = cursor.getBlob(indexFoto);
        long idUsuario = cursor.getLong(indexIdUsuario);
        long idPessoa = cursor.getLong(indexIdPessoa);
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setTelefone(telefone);
        cliente.setFoto(foto);
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
        String query = SQL_SELECT_ALL_FROM + DBHelper.TABELA_CLIENTE + SQL_WHERE + DBHelper.COL_CLIENTE_ID + SQL_LIKE;
        String[] args = {String.valueOf(id)};
        return this.loadObject(query, args);
    }

    public Cursor getIdObjectByCliente(Long idCliente) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = SQL_SELECT_ALL_FROM + DBHelper.TABELA_CLIENTE + SQL_WHERE + DBHelper.COL_CLIENTE_ID +
                SQL_LIKE;
        String[] args = {String.valueOf(idCliente)};

        return db.rawQuery(query, args); //Metodo para ser usado na classe de negocio, que usa o id do cliente
        // para recuperar o objeto inteiro(todas as outras classes
        // ex.(pessoa,usuario e endereco podem ser acessadas).
    }

    public Cliente getIdClienteByUsuario(long idUsuario) {
        String query = " SELECT * FROM " + DBHelper.TABELA_CLIENTE + SQL_WHERE + DBHelper.COL_CLIENTE_FK_USUARIO +
                SQL_LIKE;
        String[] args = {String.valueOf(idUsuario)};
        return this.loadObject(query, args);
    }

    public Cliente getClienteByEmail(String email) {//Passando um email como parametro, recupera o cliente
        UsuarioDAO usuarioDAO = new UsuarioDAO();  // que está atribuido a este email de um usuario(classe)
        Usuario usuario = usuarioDAO.getUsuario(email);//retorna o usuario que tem este email
        return getIdClienteByUsuario(usuario.getId());//retorna o cliente que tem este usuario
    }


    public void alteraAvaliacao(Cliente cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CLIENTE_AVALIACAO, cliente.getAvaliacao());
        db.update(DBHelper.TABELA_CLIENTE, values, DBHelper.COL_CLIENTE_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();

    }

    public void alteraFotoCliente(Cliente cliente){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_CLIENTE_FOTO,cliente.getFoto());
        db.update(DBHelper.TABELA_CLIENTE,values,DBHelper.COL_CLIENTE_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();
    }

}
