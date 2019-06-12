package br.ufrpe.bsi.mpoo.petspeed.pessoa.negocio;


import android.database.Cursor;

import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.persistencia.PessoaDAO;

public class PessoaServices {
    private EnderecoDAO enderecoDAO = new EnderecoDAO();
    private PessoaDAO pessoaDAO = new PessoaDAO();

    public long cadastraPessoa(Pessoa pessoa, Endereco endereco) {
        long idPessoa = pessoaDAO.cadastraPessoa(pessoa);
        endereco.setFkPessoa(idPessoa);
        enderecoDAO.cadastraEndereco(endereco);
        return idPessoa;
    }

    public Pessoa getPessoaByFkUsuario(long fkUsuario){
        return pessoaDAO.getPessoaByFkUsuario(fkUsuario);
    }

    public Pessoa getPessoaCompleta(long idPessoa) {
        Pessoa pessoa;
        pessoa = pessoaDAO.getPessoaById(idPessoa);
        Cursor data = pessoaDAO.getIdEnderecoByPessoa(idPessoa);
        if (data != null && data.moveToFirst()) {
            int indexEndereco = data.getColumnIndex(DBHelper.COL_ENDERECO_ID);
            long idEndereco = data.getLong(indexEndereco);
            data.close();
            pessoa.setEndereco(enderecoDAO.getEnderecoById(idEndereco));

            return pessoa;
        }
        return pessoa;
    }

}
