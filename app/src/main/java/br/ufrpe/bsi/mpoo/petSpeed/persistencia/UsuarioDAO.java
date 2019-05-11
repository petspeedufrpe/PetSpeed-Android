package br.ufrpe.bsi.mpoo.petSpeed.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petSpeed.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;

public class UsuarioDAO {

    private DBHelper dbHelper = new DBHelper();

    public long cadastrarUsuario(Usuario usuario){
        long res;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_EMAIL_USUARIO,usuario.getEmail());
        values.put(DBHelper.COL_SENHA_USUARIO,usuario.getSenha());

        res = db.insert(DBHelper.TABELA_USUARIO,null,values);
        db.close();
        return res;


    }

    public void deletarUsuario(Usuario usuario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.TABELA_USUARIO,DBHelper.COL_ID_USUARIO + " = ?",new String[]{String.valueOf(usuario.getId())});
        db.close();

    }

    public void alterarEmail(Usuario usuario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_EMAIL_USUARIO,usuario.getEmail());
        db.update(DBHelper.TABELA_USUARIO,values,"email = ?",new String[]{usuario.getEmail()});
        db.close();

    }

    public void alterarSenha(Usuario usuario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_SENHA_USUARIO,usuario.getSenha());
        db.update(DBHelper.TABELA_USUARIO,values,"senha = ?",new String[]{usuario.getSenha()});
        db.close();

    }

    private Usuario createUsuario(Cursor cursor){
        Usuario usuario = new Usuario();
        int indexID = cursor.getColumnIndex(DBHelper.COL_ID_USUARIO);
        int indexEmail = cursor.getColumnIndex(DBHelper.COL_EMAIL_USUARIO);
        int indexSenha = cursor.getColumnIndex(DBHelper.COL_SENHA_USUARIO);
        usuario.setId(cursor.getInt(indexID));
        usuario.setEmail(cursor.getString(indexEmail));
        usuario.setSenha(cursor.getString(indexSenha));
        return usuario;
    }

    public Usuario getUsuario(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Usuario usuario = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_USUARIO+ " WHERE " + DBHelper.COL_EMAIL_USUARIO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql,new String[]{email});
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
