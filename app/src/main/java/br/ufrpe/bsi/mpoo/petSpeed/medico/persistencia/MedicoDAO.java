package br.ufrpe.bsi.mpoo.petSpeed.medico.persistencia;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.infra.DBHelper;

public class MedicoDAO {

	private DBHelper db;

	public MedicoDAO(DBHelper db) {
		this.db = db;
	}

	public long cadastraMedico(Medico medico, String idEndereco, String idDadosPessoais, String clinicas) {
		SQLiteDatabase dbWrite = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_MEDICO_AVALIACAO, medico.getAvaliacao());
		values.put(DBHelper.COL_MEDICO_CRMV,medico.getCrmv());
		values.put(DBHelper.COL_MEDICO_ENDERECO, idEndereco);
		values.put(DBHelper.COL_MEDICO_DADOS_PESSOAIS, idDadosPessoais);
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
