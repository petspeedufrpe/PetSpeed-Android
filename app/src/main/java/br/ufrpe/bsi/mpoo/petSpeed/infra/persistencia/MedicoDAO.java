package br.ufrpe.bsi.mpoo.petSpeed.infra.persistencia;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petSpeed.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.infra.DBHelper;

public class MedicoDAO {

	private DBHelper db;

	public MedicoDAO(DBHelper db) {
		this.db = db;
	}

	public long cadastraMedico(Medico medico, String idEndereco, String idDadosPessoais, String clinicas) {
		SQLiteDatabase dbWrite = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_EMAIL_MEDICO, medico.getEmail());
		values.put(DBHelper.COL_SENHA_MEDICO, medico.getSenha());
		values.put(DBHelper.COL_AVALIACAO_MEDICO, medico.getAvaliacao());
		values.put(DBHelper.COL_CRMV_MEDICO,medico.getCrmv());
		values.put(DBHelper.COL_ENDERECO_MEDICO, idEndereco);
		values.put(DBHelper.COL_DADOS_PESSOAIS_MEDICO, idDadosPessoais);
		values.put(DBHelper.COL_CLINICAS_MEDICO, clinicas);
		long res = dbWrite.insert(DBHelper.TABELA_MEDICO, null, values);
		db.close();
		return res;

	}

	public void deletaMedico() {

	}

	public void alteraAvaliacao() {

	}

	public void alteraClinica() {

	}

	public void alteraEndereco() {

	}

	public void alteraCrmv() {

	}

	public Endereco getEnderecoById() {
		return null;
	}

}
