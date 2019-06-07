package br.ufrpe.bsi.mpoo.petspeed.infra.Persistencia;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.ufrpe.bsi.mpoo.petspeed.infra.app.PetSpeedApp;


public class DBHelper extends SQLiteOpenHelper {
    // TABELA ANIMAL:
    public static final String TABELA_ANIMAL = "TB_ANIMAL";
    public static final String COL_ANIMAL_ID = "ID";
    public static final String COL_ANIMAL_NOME = "NOME";
    public static final String COL_ANIMAL_RACA = "RACA";
    public static final String COL_ANIMAL_PESO = "PESO";
    public static final String COL_ANIMAL_IDADE = "IDADE";
    public static final String COL_ANIMAL_FOTO = "FOTO";
    public static final String COL_ANIMAL_FK_CLIENTE = "FK_CLIENTE";
    // TABELA MEDICO:
    public static final String TABELA_MEDICO = "TB_MEDICO";
    public static final String COL_MEDICO_ID = "ID";
    public static final String COL_MEDICO_AVALIACAO = "AVALIACAO";
    public static final String COL_MEDICO_CRMV = "CRMV";
    public static final String COL_MEDICO_FK_USUARIO = "FK_USUARIO";
    public static final String COL_MEDICO_FK_CLINICA = "FK_CLINICA";
    public static final String COL_MEDICO_FK_PESSOA = "FK_PESSOA";
    // TABELA CLINICA:
    public static final String TABELA_CLINICA = "TB_CLINICA";
    public static final String COL_CLINICA_ID = "ID";
    public static final String COL_CLINICA_NOME = "NOME";
    public static final String COL_CLINICA_RAZAO_SOCIAL = "RAZAO_SOCIAL";
    public static final String COL_CLINICA_AVALIACAO = "AVALIACAO";
    public static final String COL_CLINICA_CRMV = "CRMV";
    public static final String COL_CLINICA_FK_USUARIO = "FK_USUARIO";
    // TABELA PESSOA:
    public static final String TABELA_PESSOA = "TB_PESSOA";
    public static final String COL_PESSOA_ID = "ID";
    public static final String COL_PESSOA_NOME = "NOME";
    public static final String COL_PESSOA_CPF = "CPF";
    public static final String COL_PESSOA_FK_USUARIO = "FK_USUARIO";
    // TABELA ENDERECO:
    public static final String TABELA_ENDERECO = "TB_ENDERECO";
    public static final String COL_ENDERECO_ID = "ID";
    public static final String COL_ENDERECO_CEP = "CEP";
    public static final String COL_ENDERECO_UF = "UF";
    public static final String COL_ENDERECO_CIDADE = "CIDADE";
    public static final String COL_ENDERECO_BAIRRO = "BAIRRO";
    public static final String COL_ENDERECO_LOGRADOURO = "LOGRADOURO";
    public static final String COL_ENDERECO_NUMERO = "NUMERO";
    public static final String COL_ENDERECO_COMPLEMENTO = "COMPLEMENTO";
    public static final String COL_ENDERECO_FK_PESSOA = "FK_PESSOA";
    public static final String COL_ENDERECO_FK_CLINICA = "FK_CLINICA";
    public static final String COL_ENDERECO_LATITUTDE  = "LAT";
    public static final String COL_ENDERECO_LONGITUDE = "LONG";
    // TABELA CLIENTE:
    public static final String TABELA_CLIENTE = "TB_CLIENTE";
    public static final String COL_CLIENTE_ID = "ID";
    public static final String COL_CLIENTE_AVALIACAO = "AVALIACAO";
    public static final String COL_CLIENTE_FK_USUARIO = "FK_USUARIO";
    public static final String COL_CLIENTE_FK_PESSOA = "FK_PESSOA";
    // TABELA OS:
    public static final String TABELA_OS = "TB_OS";
    public static final String COL_OS_ID = "ID";
    public static final String COL_OS_PENDENTE = "PENDENTE";
    public static final String COL_OS_DESCRICAO = "DESCRICAO";
    public static final String COL_OS_PRIORIDADE = "PRIORIDADE";
    public static final String COL_OS_FK_MEDICO = "FK_MEDICO";
    public static final String COL_OS_FK_TRIAGEM = "FK_TRIAGEM";
    public static final String COL_OS_FK_CLIENTE = "FK_CLIENTE";
    public static final String COL_OS_FK_ANIMAL = "FK_ANIMAL";
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
    private static final String NOME_DB = "petspeed.db";
    private static final int VERSAO = 17;
    private static final String[] TABELAS = {
            TABELA_MEDICO, TABELA_ANIMAL, TABELA_CLIENTE, TABELA_CLINICA,
            TABELA_ENDERECO, TABELA_OS, TABELA_PESSOA, TABELA_TRIAGEM, TABELA_USUARIO
    };


    public DBHelper() {
        super(PetSpeedApp.getContext(), NOME_DB, null, VERSAO);
    }

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
        createTabelaUsuario(db);
    }


    private void createTabelaAnimal(SQLiteDatabase db) {
        String sqlTbAnimal =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        " %5$s TEXT, " +
                        "  %6$s TEXT NOT NULL, " +
                        "  %7$s TEXT NOT NULL, " +
                        "  %8$s TEXT NOT NULL " +

                        ");";
        sqlTbAnimal = String.format(sqlTbAnimal,
                TABELA_ANIMAL, COL_ANIMAL_ID, COL_ANIMAL_NOME, COL_ANIMAL_RACA,
                COL_ANIMAL_FOTO, COL_ANIMAL_IDADE, COL_ANIMAL_PESO, COL_ANIMAL_FK_CLIENTE);
        db.execSQL(sqlTbAnimal);
    }

    private void createTabelaMedico(SQLiteDatabase db) {
        String sqlTbMedico =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s REAL NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        " %5$s TEXT NOT NULL, " +
                        " %6$s TEXT NOT NULL, " +
                        " %7$s TEXT " +

                        ");";
        sqlTbMedico = String.format(sqlTbMedico,
                TABELA_MEDICO, COL_MEDICO_ID, COL_MEDICO_AVALIACAO,
                COL_MEDICO_CRMV, COL_MEDICO_FK_USUARIO,
                COL_MEDICO_FK_PESSOA, COL_MEDICO_FK_CLINICA);
        db.execSQL(sqlTbMedico);
    }

    private void createTabelaClinica(SQLiteDatabase db) {
        String sqlTbClinica =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        " %5$s REAL NOT NULL, " +
                        " %6$s TEXT NOT NULL, " +
                        " %7$s TEXT NOT NULL " +
                        ");";
        sqlTbClinica = String.format(sqlTbClinica,
                TABELA_CLINICA, COL_CLINICA_ID, COL_CLINICA_NOME, COL_CLINICA_RAZAO_SOCIAL,
                COL_CLINICA_AVALIACAO, COL_CLINICA_CRMV, COL_CLINICA_FK_USUARIO);
        db.execSQL(sqlTbClinica);
    }

    private void createTabelaPessoa(SQLiteDatabase db) {
        String sqlTbPessoa =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbPessoa = String.format(sqlTbPessoa,
                TABELA_PESSOA, COL_PESSOA_ID, COL_PESSOA_NOME, COL_PESSOA_CPF,COL_PESSOA_FK_USUARIO);
        db.execSQL(sqlTbPessoa);
    }

    private void createTabelaEndereco(SQLiteDatabase db) {
        String sqlTbEndereco =
                "CREATE TABLE %1$s ( " +
                        " %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " %3$s TEXT NOT NULL, " +
                        " %4$s TEXT NOT NULL, " +
                        " %5$s TEXT NOT NULL, " +
                        " %6$s TEXT NOT NULL, " +
                        " %7$s TEXT NOT NULL, " +
                        " %8$s TEXT NOT NULL, " +
                        " %9$s TEXT NOT NULL, " +
                        " %10$s TEXT, " +
                        " %11$s TEXT, " +
                        " %12$s TEXT, " +
                        " %13$s TEXT " +
                        ");";
        sqlTbEndereco = String.format(sqlTbEndereco,
                TABELA_ENDERECO, COL_ENDERECO_ID, COL_ENDERECO_CEP, COL_ENDERECO_NUMERO,
                COL_ENDERECO_COMPLEMENTO, COL_ENDERECO_UF, COL_ENDERECO_BAIRRO, COL_ENDERECO_LOGRADOURO,
                COL_ENDERECO_CIDADE, COL_ENDERECO_FK_PESSOA, COL_ENDERECO_FK_CLINICA,COL_ENDERECO_LATITUTDE,COL_ENDERECO_LONGITUDE);
        db.execSQL(sqlTbEndereco);
    }

    private void createTabelaCliente(SQLiteDatabase db) {
        String sqlTbCliente =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s REAL NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbCliente = String.format(sqlTbCliente,
                TABELA_CLIENTE, COL_CLIENTE_ID, COL_CLIENTE_AVALIACAO, COL_CLIENTE_FK_PESSOA, COL_CLIENTE_FK_USUARIO);
        db.execSQL(sqlTbCliente);
    }

    private void createTabelaUsuario(SQLiteDatabase db) {
        String sqlTbUsuario =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        ");";
        sqlTbUsuario = String.format(sqlTbUsuario,
                TABELA_USUARIO, COL_USUARIO_ID, COL_USUARIO_EMAIL, COL_USUARIO_SENHA);
        db.execSQL(sqlTbUsuario);
    }

    private void createTabelaOS(SQLiteDatabase db) {
        String sqlTbOS =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        " %5$s TEXT NOT NULL, " +
                        " %6$s TEXT NOT NULL, " +
                        " %7$s TEXT NOT NULL, " +
                        " %8$s TEXT NOT NULL, " +
                        " %9$s TEXT NOT NULL " +
                        ");";
        sqlTbOS = String.format(sqlTbOS,
                TABELA_OS, COL_OS_ID, COL_OS_DESCRICAO, COL_OS_PENDENTE, COL_OS_PRIORIDADE,
                COL_OS_FK_MEDICO, COL_OS_FK_TRIAGEM, COL_OS_FK_CLIENTE, COL_OS_FK_ANIMAL);
        db.execSQL(sqlTbOS);
    }

    private void createTabelaTriagem(SQLiteDatabase db) {
        String sqlTbTriagem =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        ");";
        sqlTbTriagem = String.format(sqlTbTriagem,
                TABELA_TRIAGEM, COL_TRIAGEM_ID, COL_TRIAGEM_SINTOMAS, COL_TRIAGEM_OUTROS);
        db.execSQL(sqlTbTriagem);
    }


    public void dropTables(SQLiteDatabase db) {
        for (String tabela : TABELAS) {
            StringBuilder dropTable = new StringBuilder();
            dropTable.append(" DROP TABLE IF EXISTS ");
            dropTable.append(tabela);
            db.execSQL(dropTable.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }
}
