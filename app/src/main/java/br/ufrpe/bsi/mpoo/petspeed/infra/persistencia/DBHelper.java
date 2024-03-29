package br.ufrpe.bsi.mpoo.petspeed.infra.persistencia;

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
    public static final String COL_MEDICO_TELEFONE = "TELEFONE";
    public static final String COL_MEDICO_FK_USUARIO = "FK_USUARIO";
    public static final String COL_MEDICO_FK_CLINICA = "FK_CLINICA";
    public static final String COL_MEDICO_FK_PESSOA = "FK_PESSOA";
    public static final String COL_MEDICO_FOTO = "FOTO";

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
    public static final String COL_CLIENTE_TELEFONE = "TELEFONE";
    public static final String COL_CLIENTE_FK_USUARIO = "FK_USUARIO";
    public static final String COL_CLIENTE_FK_PESSOA = "FK_PESSOA";
    public static final String COL_CLIENTE_FOTO = "FOTO";
    // TABELA OS:
    public static final String TABELA_OS = "TB_OS";
    public static final String COL_OS_ID = "ID";
    public static final String COL_OS_STATUS = "PENDENTE";
    public static final String COL_OS_DESCRICAO = "DESCRICAO";
    public static final String COL_OS_PRIORIDADE = "PRIORIDADE";
    public static final String COL_OS_DATA = "DATA";
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

    // TABELA SINTOMAS
    public static final String TABELA_SINTOMAS = "TB_SINTOMAS";
    public static final String COL_SINTOMAS_ID = "ID";
    public static final String COL_SINTOMAS_DESCRICAO = "SINTOMA";
    public static final String COL_SINTOMAS_FK_TRIAGEM = "FK_TRIAGEM";

    //TABELA RELATION SINTOMASXTRIAGEM
    public static final String TABELA_SINTOMAS_X_TRIAGEM = "TB_SINTOMAS_X_TRIAGEM";
    public static final String COL_SINTOMAS_X_TRIAGEM_ID = "ID";
    public static final String COL_FK_SINTOMAS = "FK_SINTOMA";
    public static final String COL_FK_TRIAGEM = "FK_TRIAGEM";

    //TABELA DISEASE PROBS
    public static final String TABELA_DISEASES = "TB_DOENÇAS";
    public static final String COL_DISEASES_ID = "ID";
    public static final String COL_NOME = "NOME";
    public static final String COL_PROB = "PROB";
    public static final String COL_DISEASE_FK_OS = "FK_OS";



    private static final String NOME_DB = "petspeed.db";
    private static final int VERSAO = 23;
    private static final String[] TABELAS = {
            TABELA_MEDICO, TABELA_ANIMAL, TABELA_CLIENTE, TABELA_CLINICA,
            TABELA_ENDERECO, TABELA_OS, TABELA_PESSOA, TABELA_TRIAGEM, TABELA_USUARIO,TABELA_SINTOMAS,
            TABELA_SINTOMAS_X_TRIAGEM,TABELA_DISEASES
    };
    private static final String SQL_CREATE_TABLE = "CREATE TABLE %1$s ";
    private static final String SQL_INTEGER_AUTOINCREMENT = "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, ";


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
        createTabelaSintomas(db);
        createTableSintomasXtriagem(db);
        createTableDiseases(db);
    }

    private void createTableDiseases(SQLiteDatabase db) {

        String sqlTbDisease =
                SQL_CREATE_TABLE + "( " +
                        SQL_INTEGER_AUTOINCREMENT +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s REAL NOT NULL, "+
                        "  %5$s TEXT NOT NULL "+
                        ");";
        sqlTbDisease = String.format(sqlTbDisease,
                TABELA_DISEASES,COL_DISEASES_ID,COL_NOME,COL_PROB,COL_DISEASE_FK_OS);
        db.execSQL(sqlTbDisease);
    }


    private void createTabelaAnimal(SQLiteDatabase db) {
        String sqlTbAnimal =
                SQL_CREATE_TABLE + "( " +
                        SQL_INTEGER_AUTOINCREMENT +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        " %5$s BLOB, " +
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
                SQL_CREATE_TABLE + "( " +
                        SQL_INTEGER_AUTOINCREMENT +
                        "  %3$s REAL NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        " %5$s TEXT NOT NULL, " +
                        " %6$s TEXT NOT NULL, " +
                        " %7$s TEXT NOT NULL, " +
                        " %8$s BLOB, " +
                        " %9$s TEXT " +

                        ");";
        sqlTbMedico = String.format(sqlTbMedico,
                TABELA_MEDICO, COL_MEDICO_ID, COL_MEDICO_AVALIACAO,
                COL_MEDICO_CRMV,COL_MEDICO_TELEFONE, COL_MEDICO_FK_USUARIO,
                COL_MEDICO_FK_PESSOA,COL_MEDICO_FOTO,COL_MEDICO_FK_CLINICA);
        db.execSQL(sqlTbMedico);
    }

    private void createTabelaClinica(SQLiteDatabase db) {
        String sqlTbClinica =
                SQL_CREATE_TABLE + "( " +
                        SQL_INTEGER_AUTOINCREMENT +
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
                SQL_CREATE_TABLE + "( " +
                        SQL_INTEGER_AUTOINCREMENT +
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
                SQL_CREATE_TABLE + "( " +
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
                        " %12$s FLOAT, " +
                        " %13$s FLOAT " +
                        ");";
        sqlTbEndereco = String.format(sqlTbEndereco,
                TABELA_ENDERECO, COL_ENDERECO_ID, COL_ENDERECO_CEP, COL_ENDERECO_NUMERO,
                COL_ENDERECO_COMPLEMENTO, COL_ENDERECO_UF, COL_ENDERECO_BAIRRO, COL_ENDERECO_LOGRADOURO,
                COL_ENDERECO_CIDADE, COL_ENDERECO_FK_PESSOA, COL_ENDERECO_FK_CLINICA,COL_ENDERECO_LATITUTDE,COL_ENDERECO_LONGITUDE);
        db.execSQL(sqlTbEndereco);
    }

    private void createTabelaCliente(SQLiteDatabase db) {
        String sqlTbCliente =
                SQL_CREATE_TABLE + "( " +
                        SQL_INTEGER_AUTOINCREMENT +
                        "  %3$s REAL NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s BLOB, " +
                        "  %6$s TEXT NOT NULL, " +
                        "  %7$s TEXT NOT NULL " +
                        ");";
        sqlTbCliente = String.format(sqlTbCliente,
                TABELA_CLIENTE, COL_CLIENTE_ID, COL_CLIENTE_AVALIACAO,COL_CLIENTE_TELEFONE,COL_CLIENTE_FOTO,COL_CLIENTE_FK_PESSOA, COL_CLIENTE_FK_USUARIO);
        db.execSQL(sqlTbCliente);
    }

    private void createTabelaUsuario(SQLiteDatabase db) {
        String sqlTbUsuario =
                SQL_CREATE_TABLE + "( " +
                        SQL_INTEGER_AUTOINCREMENT +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        ");";
        sqlTbUsuario = String.format(sqlTbUsuario,
                TABELA_USUARIO, COL_USUARIO_ID, COL_USUARIO_EMAIL, COL_USUARIO_SENHA);
        db.execSQL(sqlTbUsuario);
    }

    private void createTabelaOS(SQLiteDatabase db) {
        String sqlTbOS =
                SQL_CREATE_TABLE + "( " +
                        SQL_INTEGER_AUTOINCREMENT +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        " %5$s TEXT NOT NULL, " +
                        " %6$s TEXT, " +
                        " %7$s TEXT, " +
                        " %8$s TEXT NOT NULL, " +
                        " %9$s TEXT NOT NULL, " +
                        " %10$s TEXT NOT NULL " +
                        ");";
        sqlTbOS = String.format(sqlTbOS,
                TABELA_OS, COL_OS_ID, COL_OS_DESCRICAO, COL_OS_STATUS, COL_OS_PRIORIDADE,
                COL_OS_DATA,COL_OS_FK_MEDICO, COL_OS_FK_TRIAGEM, COL_OS_FK_CLIENTE, COL_OS_FK_ANIMAL);
        db.execSQL(sqlTbOS);
    }

    private void createTabelaTriagem(SQLiteDatabase db) {
        String sqlTbTriagem =
                SQL_CREATE_TABLE + "( " +
                        SQL_INTEGER_AUTOINCREMENT +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        ");";
        sqlTbTriagem = String.format(sqlTbTriagem,
                TABELA_TRIAGEM, COL_TRIAGEM_ID, COL_TRIAGEM_SINTOMAS, COL_TRIAGEM_OUTROS);
        db.execSQL(sqlTbTriagem);
    }

    private void createTabelaSintomas(SQLiteDatabase db){
        {
            String sqlTbSintomas =
                    SQL_CREATE_TABLE + "( " +
                            SQL_INTEGER_AUTOINCREMENT +
                            "  %3$s TEXT NOT NULL, " +
                            "  %4$s TEXT " +
                            ");";
            sqlTbSintomas = String.format(sqlTbSintomas,
                    TABELA_SINTOMAS, COL_SINTOMAS_ID, COL_SINTOMAS_DESCRICAO, COL_SINTOMAS_FK_TRIAGEM);
            db.execSQL(sqlTbSintomas);
        }
    }

    private void createTableSintomasXtriagem(SQLiteDatabase db){
        {
            String sqlTbSintomasTriagem =
                    SQL_CREATE_TABLE + "( " +
                            SQL_INTEGER_AUTOINCREMENT +
                            "  %3$s TEXT NOT NULL, " +
                            "  %4$s TEXT NOT NULL " +
                            ");";
            sqlTbSintomasTriagem = String.format(sqlTbSintomasTriagem,
                    TABELA_SINTOMAS_X_TRIAGEM, COL_SINTOMAS_X_TRIAGEM_ID, COL_FK_SINTOMAS, COL_FK_TRIAGEM);
            db.execSQL(sqlTbSintomasTriagem);
        }
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
