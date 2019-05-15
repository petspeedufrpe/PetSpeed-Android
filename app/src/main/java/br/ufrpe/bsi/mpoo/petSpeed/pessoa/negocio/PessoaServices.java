package br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio;


import android.database.Cursor;

import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;

public class PessoaServices {

	private PessoaDAO pessoaDAO = new PessoaDAO();

	private boolean validaCpf() {
		return false;
	}

	public void cadastraPessoa(Pessoa pessoa) {
	//	if (pessoaDAO.getPessoa(pessoa.getId())
	}

	public void deletaPessoa() {

	}

	public void alteraCpf() {

	}

	public Pessoa getPessoaCompleta(long idPessoa){
		Pessoa pessoa;
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		pessoa = pessoaDAO.getPessoaById(idPessoa);
		Cursor data = pessoaDAO.getIdEnderecoByPessoa(idPessoa);
		if(data!=null && data.moveToFirst()){
			int indexEndereco = data.getColumnIndex(DBHelper.COL_ENDERECO_FK_PESSOA);
			long idEndereco = data.getLong(indexEndereco);
			data.close();
			pessoa.setEndereco(enderecoDAO.getEnderecoById(idEndereco));

			return pessoa;
		}
		return null;
	}

}
