package br.ufrpe.bsi.mpoo.petSpeed.medico.persistencia;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
public class MedicoDAO {

	private DBHelper db;


	public void cadastraMedico(Medico medico, long idUsuario, long idClinica) {
		SQLiteDatabase dbWrite = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COL_MEDICO_AVALIACAO, medico.getAvaliacao());
		values.put(DBHelper.COL_MEDICO_CRMV,medico.getCrmv());
		values.put(DBHelper.COL_MEDICO_ENDERECO,medico.getDadosPessoais().getEndereco().getId());
		values.put(DBHelper.COL_MEDICO_DADOS_PESSOAIS, medico.getDadosPessoais().getId());
		values.put(DBHelper.COL_MEDICO_FK_USUARIO, idUsuario);
		values.put(DBHelper.COL_MEDICO_FK_CLINICA, idClinica);
		dbWrite.insert(DBHelper.TABELA_MEDICO, null, values);
		db.close();
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
