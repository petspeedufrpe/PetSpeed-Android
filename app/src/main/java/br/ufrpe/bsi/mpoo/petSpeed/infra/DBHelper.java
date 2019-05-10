package br.ufrpe.bsi.mpoo.petSpeed.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NOME_DB = "petspeed.db";
    private static final int VERSAO = 2;

    // TABELA ANIMAL:
    public static final String TABELA_ANIMAL = "TB_ANIMAL";
    public static final String COL_ANIMAL_ID = "ID";
    public static final String COL_ANIMAL_NOME = "NOME";
    public static final String COL_ANIMAL_RACA = "RACA";
	public static final String COL_ANIMAL_PESO = "PESO";
	public static final String COL_ANIMAL_IDADE = "IDADE";
	public static final String COL_ANIMAL_FOTO = "FOTO";
    // TABELA MEDICO:
    public static final String TABELA_MEDICO = "TB_MEDICO";
    public static final String COL_MEDICO_ID = "ID";
    public static final String COL_MEDICO_AVALIACAO = "AVALIACAO";
    public static final String COL_MEDICO_AVALIACAO_TOTAL = "AVALIACAO_TOTAL";
    public static final String COL_MEDICO_CRMV = "CRMV";
    public static final String COL_MEDICO_ENDERECO = "ENDERECO";
    public static final String COL_MEDICO_DADOS_PESSOAIS = "DADOS_PESSOAIS";
    public static final String COL_MEDICO_FK_USUARIO = "FK_USUARIO";
    public static final String COL_MEDICO_FK_CLINICA = "CLINICAS";
    // TABELA CLINICA:
    public static final String TABELA_CLINICA = "TB_CLINICA";
    public static final String COL_CLINICA_ID = "ID";
    public static final String COL_CLINICA_AVALIACAO = "AVALIACAO";
    public static final String COL_CLINICA_AVALIACAO_TOTAL = "AVALIACAO_TOTAL";
    public static final String COL_CLINICA_CRMV = "CRMV";
    public static final String COL_CLINICA_FK_USUARIO = "FK_USUARIO";

    // TABELA PESSOA:
    public static final String TABELA_PESSOA = "TB_PESSOA";
    public static final String COL_PESSOA_ID = "ID";
    public static final String COL_PESSOA_NOME = "NOME";
    public static final String COL_PESSOA_CPF = "CPF";
    // TABELA ENDERECO:
    public static final String TABELA_ENDERECO = "TB_ENDERECO";
    public static final String COL_ENDERECO_ID = "ID";
    public static final String COL_ENDERECO_CEP = "CEP";
    public static final String COL_ENDERECO_NUMERO = "NUMERO";
    public static final String COL_ENDERECO_COMPLEMENTO = "COMPLEMENTO";
    public static final String COL_ENDERECO_FK_CLINICA = "FK_CLINICA";

    // TABELA CLIENTE:
    public static final String TABELA_CLIENTE = "TB_CLIENTE";
    public static final String COL_CLIENTE_ID = "ID";
    public static final String COL_CLIENTE_AVALIACAO = "AVALIACAO";
    public static final String COL_CLIENTE_AVALIACAO_TOTAL = "AVALIACAO_TOTAL";
    public static final String COL_CLIENTE_ENDERECO = "ENDERECO";
    public static final String COL_CLIENTE_DADOS_ESSOAIS = "DADOS_PESSOAIS";
    public static final String COL_CLIENTE_FK_USUARIO = "FK_USUARIO";

    // TABELA OS:
    public static final String TABELA_OS = "TB_OS";
    public static final String COL_OS_ID = "ID";
    public static final String COL_OS_MEDICO = "MEDICO";
    public static final String COL_OS_TRIAGEM = "TRIAGEM";
    public static final String COL_OS_CLIENTE = "CLIENTE";
    public static final String COL_OS_ANIMAL = "ANIMAL";
    public static final String COL_OS_DESCICAO = "DESCRICAO";
    public static final String COL_OS_PRIORIDADE = "PRIORIDADE";
    //TABELA USUARIO:
    public static final String TABELA_USUARIO = "TB_USUARIO";
    public static final String COL_USUARIO_ID = "ID";
    public static final String COL_USUARIO_EMAIL = "EMAIL";
    public static final String COL_USUARIO_SENHA = "SENHA";
    // TABELA TRIAGEM:
    public static final String TABELA_TRIAGEM = "TB_TRIAGEM";
    public static final String COL_TRIAGEM_ID = "ID";
    public static final String COL_TRIAGEM_SINTOMAS = "SINTOMAS";
    public static final String COL_TRIAGEM_OUTROS = "OUTROS";

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
                        "  %6$s TEXT NOT NULL " +
                        "  %7$s TEXT NOT NULL " +


						");";
        sqlTbAnimal = String.format(sqlTbAnimal,
                TABELA_ANIMAL, COL_ANIMAL_ID, COL_ANIMAL_NOME, COL_ANIMAL_RACA, COL_ANIMAL_FOTO,COL_ANIMAL_IDADE,COL_ANIMAL_PESO);
        db.execSQL(sqlTbAnimal);
    }

    private void createTabelaMedico(SQLiteDatabase db) {
        String sqlTbMedico=
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        " %5$s TEXT NOT NULL" +
                        " %6$s TEXT NOT NULL" +
                        ");";
        sqlTbMedico = String.format(sqlTbMedico,
                TABELA_MEDICO, COL_MEDICO_ID, COL_MEDICO_AVALIACAO,
                COL_MEDICO_CRMV, COL_MEDICO_ENDERECO, COL_MEDICO_DADOS_PESSOAIS);
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
                TABELA_CLINICA, COL_CLINICA_ID, COL_CLINICA_EMAIL, COL_CLINICA_SENHA, COL_CLINICA_AVALIACAO,
                COL_CLINICA_CRMV, COL_CLINICA_ENDERECO, COL_CLINICA_MEDICOS);
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
                TABELA_PESSOA, COL_PESSOA_ID, COL_PESSOA_NOME, COL_PESSOA_CPF);
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
                TABELA_ENDERECO, COL_ENDERECO_ID, COL_ENDERECO_CEP, COL_ENDERECO_NUMERO, COL_ENDERECO_COMPLEMENTO);
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
                TABELA_CLIENTE, COL_CLIENTE_ID, COL_CLIENTE_EMAIL, COL_CLIENTE_SENHA, COL_CLIENTE_AVALIACAO,
                COL_CLIENTE_ENDERECO, COL_CLIENTE_DADOS_ESSOAIS, COL_ANIMAIS_CLIENTE);
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
                TABELA_OS, COL_OS_ID, COL_OS_MEDICO, COL_OS_TRIAGEM, COL_OS_CLIENTE, COL_OS_ANIMAL,
                COL_OS_DESCICAO, COL_OS_PRIORIDADE);
        db.execSQL(sqlTbOS);
    }

    private void createTabelaTriagem(SQLiteDatabase db) {
        String sqlTbTriagem =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
						"  %6$s TEXT NOT NULL " +
                        ");";
        sqlTbTriagem = String.format(sqlTbTriagem,
                TABELA_PESSOA, COL_TRIAGEM_ID, COL_TRIAGEM_SINTOMAS, COL_TRIAGEM_OUTROS);
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
