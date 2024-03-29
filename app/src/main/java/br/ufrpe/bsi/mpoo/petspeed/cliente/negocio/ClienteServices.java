package br.ufrpe.bsi.mpoo.petspeed.cliente.negocio;

import android.database.Cursor;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.persistencia.ClienteDAO;
import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.DBHelper;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petspeed.usuario.persistencia.UsuarioDAO;

public class ClienteServices {

    private ClienteDAO clienteDAO = new ClienteDAO();

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private PessoaDAO pessoaDAO = new PessoaDAO();

    private AnimalDAO animalDAO = new AnimalDAO();


    /**
     * verificações são feitas antes do metodo cadastrar ser chamado.
     * cadastra o cliente, sendo o último a ser cadastrado(tem 1 ou mais associações nele)
     *
     * @param cliente
     * @param usuario
     * @return cliente
     */
    public Cliente cadastraCliente(Cliente cliente, Usuario usuario) throws AppException {
        Usuario usuarioReferencia = usuarioDAO.getUsuario(usuario.getEmail());
        if (usuarioReferencia != null) {
            if (!usuarioPossuiCliente(usuarioReferencia.getEmail())) {
                PessoaServices pessoaServices = new PessoaServices();
                Pessoa pessoaReferencia = pessoaServices.getPessoaByFkUsuario(usuarioReferencia.getId());
                cliente.setDadosPessoais(pessoaReferencia);
                cliente.setUsuario(usuarioReferencia);
                long idCliente = clienteDAO.cadastraCliente(cliente);
                cliente.setId(idCliente);
                return cliente;
            } else {
                throw new AppException("Usuário já possui conta de cliente");
            }
        } else {
            long idUsuario = usuarioDAO.cadastrarUsuario(usuario);
            cliente.getUsuario().setId(idUsuario);
            cliente.getDadosPessoais().setFkUsuario(idUsuario);
            PessoaServices pessoaServices = new PessoaServices();
            long idPessoa = pessoaServices.cadastraPessoa(cliente.getDadosPessoais(), cliente.getDadosPessoais().getEndereco());
            cliente.getDadosPessoais().setId(idPessoa);
            long idCliente = clienteDAO.cadastraCliente(cliente);
            cliente.setId(idCliente);
            return cliente;
        }
    }

    public void deletaCliente(Cliente cliente) throws AppException {
        //Medodo a ser utilizado por funcionalidade ainda não implementada.
        if (clienteDAO.getClienteById(cliente.getId()) != null) {
            clienteDAO.deletaCliente(cliente);
        }
    }

    public void login(String email, String senha) throws AppException {
        Usuario usuario = usuarioDAO.getUsuario(email, senha);
        if (usuario == null) {
            throw new AppException("Credenciais Inválidas.");
        } else {
            Cliente cliente = clienteDAO.getClienteByFkUsuario(usuario.getId());
            cliente = getClienteCompleto(cliente.getId());
            Sessao.instance.setUsuario(usuario);
            Sessao.instance.setCliente(cliente);
        }
    }

    public boolean usuarioPossuiCliente(Cliente cliente) {
        Usuario usuarioReferencia = usuarioDAO.getUsuario(cliente.getUsuario().getEmail());
        try {
            usuarioReferencia.getId();
        } catch (Exception e) {
            return false;
        }
        try {
            Cliente clienteReferencia = clienteDAO.getClienteByFkUsuario(usuarioReferencia.getId());
            clienteReferencia.getId();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean usuarioPossuiCliente(String attemptedLoginEmail) {
        Usuario usuarioReferencia = usuarioDAO.getUsuario(attemptedLoginEmail);
        try {
            usuarioReferencia.getId();
        } catch (Exception e) {
            return false;
        }
        try {
            Cliente clienteReferencia = clienteDAO.getClienteByFkUsuario(usuarioReferencia.getId());
            clienteReferencia.getId();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailByCliente(Long idCliente) {
        Cursor data = clienteDAO.getIdObjectByCliente(idCliente);
        if (data != null && data.moveToFirst()) {
            int indexUsuario = data.getColumnIndex(DBHelper.COL_CLIENTE_FK_USUARIO);
            long idUsuario = data.getLong(indexUsuario);
            return usuarioDAO.getUsuario(idUsuario).getEmail();
        }
        return null;
    }

    //monta o objeto completo(com todos os atributos setados, usando o cursor para navegar entre os dados do banco.
    public Cliente getClienteCompleto(long idCliente) {
        Cliente cliente;
        cliente = clienteDAO.getClienteById(idCliente);
        Cursor data = clienteDAO.getIdObjectByCliente(idCliente);

        if (data != null && data.moveToFirst()) {
            PessoaServices pessoaServices = new PessoaServices();
            int indexPessoa = data.getColumnIndex(DBHelper.COL_CLIENTE_FK_PESSOA);
            int indexUsuario = data.getColumnIndex(DBHelper.COL_CLIENTE_FK_USUARIO);
            long idPessoa = data.getLong(indexPessoa);
            long idUsuario = data.getLong(indexUsuario);

            data.close();
            Pessoa pessoa = pessoaServices.getPessoaCompleta(idPessoa);
            cliente.setDadosPessoais(pessoa);
            cliente.setUsuario(usuarioDAO.getUsuario(idUsuario));

            return cliente;
        }
        return null;
    }

    public void alterarDadosCliente(Cliente cliente) {
        usuarioDAO.alterarEmail(cliente.getUsuario());
        pessoaDAO.alteraNome(cliente.getDadosPessoais());
        pessoaDAO.alteraCPF(cliente.getDadosPessoais());

    }

    public void logout() {
        Sessao sessao = Sessao.instance;
        sessao.reset();
    }

    public long cadastraAnimal(Animal animal) {

        return animalDAO.cadastraAnimal(animal);
    }

    public void alteraSenha(Cliente cliente) {
        usuarioDAO.alterarSenha(cliente.getUsuario());
    }

    protected void alteraAvaliacao(Cliente cliente) throws AppException {
        if (cliente.getAvaliacao() > 5 || cliente.getAvaliacao() < 0) {
            throw new AppException("Erro");
        }
        clienteDAO.alteraAvaliacao(cliente);
    }

    public void alteraFotoCliente(Cliente cliente){
        clienteDAO.alteraFotoCliente(cliente);
    }

    public List<Animal> getAllAnimalByIdCliente(long idCliente) {
        return animalDAO.getAllAnimalByIdCliente(idCliente);
    }
}
