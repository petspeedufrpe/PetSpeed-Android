package br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class UsuarioDAO {

    private DBHelper dbHelper = new DBHelper();

    public long cadastrarUsuario(Usuario usuario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_USUARIO_EMAIL,usuario.getEmail());
        values.put(DBHelper.COL_USUARIO_SENHA,usuario.getSenha());

        long res = db.insert(DBHelper.TABELA_USUARIO,null,values);
        db.close();
        return res;


    }

    public void deletarUsuario(Usuario usuario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.TABELA_USUARIO,DBHelper.COL_USUARIO_ID + " = ?",new String[]{String.valueOf(usuario.getId())});
        db.close();

    }

    public void alterarEmail(Usuario usuario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_USUARIO_EMAIL,usuario.getEmail());
        db.update(DBHelper.TABELA_USUARIO,values,"email = ?",new String[]{usuario.getEmail()});
        db.close();

    }

    public void alterarSenha(Usuario usuario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_USUARIO_SENHA,usuario.getSenha());
        db.update(DBHelper.TABELA_USUARIO,values,"senha = ?",new String[]{usuario.getSenha()});
        db.close();

    }

    private Usuario createUsuario(Cursor cursor){
        Usuario usuario = new Usuario();
        int indexID = cursor.getColumnIndex(DBHelper.COL_USUARIO_ID);
        int indexEmail = cursor.getColumnIndex(DBHelper.COL_USUARIO_EMAIL);
        int indexSenha = cursor.getColumnIndex(DBHelper.COL_USUARIO_SENHA);
        usuario.setId(cursor.getLong(indexID));
        usuario.setEmail(cursor.getString(indexEmail));
        usuario.setSenha(cursor.getString(indexSenha));
        return usuario;
    }

    public Usuario getUsuario(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Usuario usuario = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_USUARIO+ " WHERE " + DBHelper.COL_USUARIO_EMAIL + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql,new String[]{email});
        if(cursor.moveToFirst()){
            usuario = createUsuario(cursor);
        }
        cursor.close();
        db.close();

        return usuario;

    }

    public Usuario getUsuarioById(long id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Usuario usuario = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_USUARIO+ " WHERE " + DBHelper.COL_USUARIO_ID + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql,new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            usuario = createUsuario(cursor);
        }
        cursor.close();
        db.close();

        return usuario;

    }

    public Usuario getUsuario(String email, String senha){
        Usuario usuario = getUsuario(email);
        if(usuario != null && !senha.equals(usuario.getSenha())){
            return null;
        }

        return usuario;
    }
}
