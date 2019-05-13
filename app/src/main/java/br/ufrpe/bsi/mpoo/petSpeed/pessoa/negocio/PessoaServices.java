package br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio;


import android.database.Cursor;

import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;

public class PessoaServices {

	private PessoaDAO pessoaDAO;

	private boolean validaCpf() {
		return false;
	}

	public Pessoa getPessoaCompleta(long idPessoa){
		Pessoa pessoa;
		PessoaDAO pessoaDAO = new PessoaDAO();
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		pessoa = pessoaDAO.getPessoaById(idPessoa);
		Cursor data = pessoaDAO.getIdEnderecoByPessoa(idPessoa);
		if (data != null && data.moveToFirst()) {
			int indexEnd = data.getColumnIndex(DBHelper.COL_PESSOA_ENDERECO);
			long idEnd = data.getLong(indexEnd);
			data.close();
			pessoa.setEndereco(enderecoDAO.getEnderecoById((int)idEnd));

			return pessoa;
		}
		return null;
	}

	public void cadastraPessoa(Pessoa pessoa) {
	//	if (pessoaDAO.getPessoa(pessoa.getId())
	}

	public void deletaPessoa() {

	}

	public void alteraCpf() {

	}


}
