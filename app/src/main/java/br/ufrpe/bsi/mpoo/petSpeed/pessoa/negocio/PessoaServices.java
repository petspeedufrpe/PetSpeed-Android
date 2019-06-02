package br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio;


import android.database.Cursor;

import br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;

public class PessoaServices {
    private EnderecoDAO enderecoDAO = new EnderecoDAO();
    private PessoaDAO pessoaDAO = new PessoaDAO();

    private boolean validaCpf() {
        return false;
    }

    public long cadastraPessoa(Pessoa pessoa, Endereco endereco) {
        long idPessoa = pessoaDAO.cadastraPessoa(pessoa);
        endereco.setFkPessoa(idPessoa);
        enderecoDAO.cadastraEndereco(endereco);
        return idPessoa;
    }

    public void deletaPessoa() {

    }

    public void alteraCpf() {

    }

    public Endereco getEnderecoByIdPessoa(long idPessoa){
        Endereco endereco;
        endereco = enderecoDAO.getEnderecoById(idPessoa);
        return endereco;
    }

    public Pessoa getPessoaByFkUsuario(long fkUsuario){
        return pessoaDAO.getPessoaByFkUsuario(fkUsuario);
    }

    public Pessoa getPessoaCompleta(long idPessoa) {
        Pessoa pessoa;
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        pessoa = pessoaDAO.getPessoaById(idPessoa);
        Cursor data = pessoaDAO.getIdEnderecoByPessoa(idPessoa);
        if (data != null && data.moveToFirst()) {
            int indexEndereco = data.getColumnIndex(DBHelper.COL_ENDERECO_ID);
            long idEndereco = data.getLong(indexEndereco);
            data.close();
            pessoa.setEndereco(enderecoDAO.getEnderecoById(idEndereco));

            return pessoa;
        }
        return null;
    }

}
