package br.ufrpe.bsi.mpoo.petSpeed.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NOME_DB = "petspeed.db";
    private static final int VERSAO = 1;

    // TABELA ANIMAL:
    public static final String TABELA_ANIMAL = "TB_ANIMAL";
    public static final String COL_ID_ANIMAL = "ID";
    public static final String COL_NOME_ANIMAL = "NOME";
    public static final String COL_RACA_ANIMAL = "RACA";
    public static final String COL_FOTO_ANIMAL = "FOTO";
    public static final String COL_HISTORICO = "HISTORICO";
    // TABELA MEDICO:
    public static final String TABELA_MEDICO = "TB_MEDICO";
    public static final String COL_ID_MEDICO = "ID";
    public static final String COL_EMAIL_MEDICO = "EMAIL";
    public static final String COL_SENHA_MEDICO = "SENHA";
    public static final String COL_AVALIACAO_MEDICO = "AVALIACAO";
    public static final String COL_CRMV_MEDICO = "CRMV";
    public static final String COL_ENDERECO_MEDICO = "ENDERECO";
    public static final String COL_DADOS_PESSOAIS_MEDICO = "DADOS_PESSOAIS";
    public static final String COL_CLINICAS_MEDICO = "CLINICAS";
    // TABELA CLINICA:
    public static final String TABELA_CLINICA = "TB_CLINICA";
    public static final String COL_ID_CLINICA = "ID";
    public static final String COL_EMAIL_CLINICA = "EMAIL";
    public static final String COL_SENHA_CLINICA = "SENHA";
    public static final String COL_AVALIACAO_CLINICA = "AVALIACAO";
    public static final String COL_CRMV_CLINICA = "CRMV";
    public static final String COL_ENDERECO_CLINICA = "ENDERECO";
    public static final String COL_MEDICOS_CLINICA = "MEDICOS_CREDENCIADOS";
    // TABELA PESSOA:
    public static final String TABELA_PESSOA = "TB_PESSOA";
    public static final String COL_ID_PESSOA = "ID";
    public static final String COL_NOME_PESSOA = "NOME";
    public static final String COL_CPF_PESSOA = "CPF";
    // TABELA ENDERECO:
    public static final String TABELA_ENDERECO = "TB_ENDERECO";
    public static final String COL_ID_ENDERECO = "ID";
    public static final String COL_CEP_ENDERECO = "CEP";
    public static final String COL_NUMERO_ENDERECO = "NUMERO";
    public static final String COL_COMPLEMENTO_ENDERECO = "COMPLEMENTO";
    // TABELA CLIENTE:
    public static final String TABELA_CLIENTE = "TB_CLIENTE";
    public static final String COL_ID_CLIENTE= "ID";
    public static final String COL_EMAIL_CLIENTE = "EMAIL";
    public static final String COL_SENHA_CLIENTE = "SENHA";
    public static final String COL_AVALIACAO_CLIENTE = "AVALIACAO";
    public static final String COL_ENDERECO_CLIENTE = "ENDERECO";
    public static final String COL_DADOS_PESSOAIS_CLIENTE = "DADOS_PESSOAIS";
    public static final String COL_ANIMAIS_CLIENTE= "ANIMAIS";
    // TABELA OS:
    public static final String TABELA_OS = "TB_OS";
    public static final String COL_ID_OS= "ID";
    public static final String COL_MEDICO_OS = "MEDICO";
    public static final String COL_TIRAGEM_OS = "TRIAGEM";
    public static final String COL_CLIENTE_OS = "CLIENTE";
    public static final String COL_ANIMAL_OS = "ANIMAL";
    public static final String COL_DESCRICAO_OS = "DESCRICAO";
    public static final String COL_PRIORIDADE_OS = "PRIORIDADE";
    // TABELA TRIAGEM:
    public static final String TABELA_TRIAGEM = "TB_TRIAGEM";
    public static final String COL_ID_TRIAGEM = "ID";
    public static final String COL_SINTOMAS_TRIAGEM = "SINTOMAS";
    public static final String COL_OUTROS_TRIAGEM = "OUTROS";

    public DBHelper(Context context) {
        super(context, "mnt/sdcard/petspeed.db", null, 0);
    }


    private static final String[] TABELAS = {
            TABELA_ANIMAL, TABELA_CLIENTE, TABELA_CLINICA,
            TABELA_ENDERECO, TABELA_MEDICO, TABELA_OS, TABELA_PESSOA, TABELA_TRIAGEM
    };

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTabelaAnimal(db);
        createTabelaMedico(db);
        createTabelaClinica(db);
        createTabelaPessoa(db);
        createTabelaEndereco(db);
        createTabelaCliente(db);
        createTabelaOS(db);
        createTabelaTriagem(db);

    }

    private void createTabelaAnimal(SQLiteDatabase db) {
        String sqlTbAnimal=
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        " %5$s TEXT NOT NULL" +
                        "  %6$s BLOB NOT NULL " +
                        "  %7$s TEXT NOT NULL " +
                        ");";
        sqlTbAnimal = String.format(sqlTbAnimal,
                TABELA_ANIMAL, COL_ID_ANIMAL, COL_NOME_ANIMAL, COL_RACA_ANIMAL, COL_FOTO_ANIMAL,COL_HISTORICO);
        db.execSQL(sqlTbAnimal);
    }

    private void createTabelaMedico(SQLiteDatabase db) {
        String sqlTbMedico=
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        " %5$s TEXT NOT NULL" +
                        " %6$s REAL NOT NULL" +
                        " %7$s TEXT NOT NULL" +
                        " %8$s TEXT NOT NULL" +
                        " %9$s TEXT NOT NULL" +
                        " %10$s TEXT NOT NULL" +
                        ");";
        sqlTbMedico = String.format(sqlTbMedico,
                TABELA_MEDICO, COL_ID_MEDICO, COL_EMAIL_MEDICO, COL_SENHA_MEDICO, COL_AVALIACAO_MEDICO,
                COL_CRMV_MEDICO, COL_ENDERECO_MEDICO, COL_DADOS_PESSOAIS_MEDICO, COL_CLINICAS_MEDICO);
        db.execSQL(sqlTbMedico);
    }

    private void createTabelaClinica(SQLiteDatabase db) {
        String sqlTbClinica =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        " %5$s TEXT NOT NULL" +
                        " %6$s REAL NOT NULL" +
                        " %7$s TEXT NOT NULL" +
                        " %8$s TEXT NOT NULL" +
                        " %9$s TEXT NOT NULL" +
                        ");";
        sqlTbClinica = String.format(sqlTbClinica,
                TABELA_CLINICA, COL_ID_CLINICA, COL_EMAIL_CLINICA, COL_SENHA_CLINICA, COL_AVALIACAO_CLINICA,
                COL_CRMV_CLINICA, COL_ENDERECO_CLINICA, COL_MEDICOS_CLINICA);
        db.execSQL(sqlTbClinica);
    }

    private void createTabelaPessoa(SQLiteDatabase db) {
        String sqlTbPessoa =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbPessoa = String.format(sqlTbPessoa,
                TABELA_PESSOA, COL_ID_PESSOA, COL_NOME_PESSOA, COL_CPF_PESSOA);
        db.execSQL(sqlTbPessoa);
    }

    private void createTabelaEndereco(SQLiteDatabase db) {
        String sqlTbEndereco =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        " %5$s INTEGER NOT NULL" +
                        " %6$s TEXT NOT NULL" +
                        ");";
        sqlTbEndereco = String.format(sqlTbEndereco,
                TABELA_ENDERECO, COL_ID_ENDERECO, COL_CEP_ENDERECO, COL_NUMERO_ENDERECO, COL_COMPLEMENTO_ENDERECO);
        db.execSQL(sqlTbEndereco);
    }

    private void createTabelaCliente(SQLiteDatabase db) {
        String sqlTbCliente =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        " %5$s TEXT NOT NULL" +
                        " %6$s REAL NOT NULL" +
                        " %7$s TEXT NOT NULL" +
                        " %8$s TEXT NOT NULL" +
                        " %9$s TEXT NOT NULL" +
                        ");";
        sqlTbCliente = String.format(sqlTbCliente,
                TABELA_CLIENTE, COL_ID_CLIENTE, COL_EMAIL_CLIENTE, COL_SENHA_CLIENTE, COL_AVALIACAO_CLIENTE,
                COL_ENDERECO_CLIENTE, COL_DADOS_PESSOAIS_CLIENTE, COL_ANIMAIS_CLIENTE);
        db.execSQL(sqlTbCliente);
    }

    private void createTabelaOS(SQLiteDatabase db) {
        String sqlTbOS =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        " %5$s TEXT NOT NULL" +
                        " %6$s TEXT NOT NULL" +
                        " %7$s TEXT NOT NULL" +
                        " %8$s TEXT NOT NULL" +
                        " %9$s TEXT NOT NULL" +
                        ");";
        sqlTbOS = String.format(sqlTbOS,
                TABELA_OS, COL_ID_OS, COL_MEDICO_OS, COL_TIRAGEM_OS, COL_CLIENTE_OS, COL_ANIMAL_OS,
                COL_DESCRICAO_OS, COL_PRIORIDADE_OS);
        db.execSQL(sqlTbOS);
    }

    private void createTabelaTriagem(SQLiteDatabase db) {
        String sqlTbTriagem =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        ");";
        sqlTbTriagem = String.format(sqlTbTriagem,
                TABELA_PESSOA, COL_SINTOMAS_TRIAGEM, COL_OUTROS_TRIAGEM);
        db.execSQL(sqlTbTriagem);
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

    protected SQLiteDatabase getReadable(){
        return this.getReadableDatabase();
    }

    protected SQLiteDatabase getWritable() {
        return this.getWritableDatabase();
    }



}
