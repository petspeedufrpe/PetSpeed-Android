package com.bsi.mpoo.petspeed.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    DBHelper(Context context) {
        super(context, "/mnt/sdcard/petspeed.db", null, 0);
    }
    private static final String NOME_DB = "petspeed.db";
    private static final int VERSAO = 1;

    public static final String CAMPO_ID = "ID";
    // TABELA DOS USUARIOS
    public static final String TABELA_USUARIO = "TB_USUARIO";
    public static final String CAMPO_EMAIL = "EMAIL";
    public static final String CAMPO_PASSWORD = "PASSWORD";

    private static final String[] TABELAS = {
            TABELA_USUARIO
    };

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTabelaUsuario(db);
    }

    private void createTabelaUsuario(SQLiteDatabase db) {
        String sqlTbUsuario =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL UNIQUE, " +
                        "  %4$s TEXT NOT NULL " +
                        ");";
        sqlTbUsuario = String.format(sqlTbUsuario,
                TABELA_USUARIO, CAMPO_ID, CAMPO_EMAIL, CAMPO_PASSWORD);
        db.execSQL(sqlTbUsuario);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }

    private void dropTables(SQLiteDatabase db) {
        StringBuilder dropTables = new StringBuilder();
        for (String tabela : TABELAS) {
            dropTables.append(" DROP TABLE IF EXISTS ");
            dropTables.append(tabela);
            dropTables.append("; ");
        }
        db.execSQL(dropTables.toString());
    }
}
