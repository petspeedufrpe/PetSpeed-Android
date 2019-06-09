package br.ufrpe.bsi.mpoo.petspeed.usuario.negocio;

import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petspeed.usuario.persistencia.UsuarioDAO;

public class UsuarioServices {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public long cadastrarUsuario(Usuario usuario) throws AppException {
        try {
            this.checkNull(usuario);
        } catch (AppException e) {
            throw new AppException("null atributes");
        }
        if (usuarioCadastrado(usuario)) {
            throw new AppException("usuario já cadastrado, não inserido");
        } else {
            return usuarioDAO.cadastrarUsuario(usuario);
        }
    }

    private void checkNull(Usuario usuario) throws AppException {
        if (usuario.getEmail() == "") {
            throw new AppException("Null EMAIL");
        }
        if (usuario.getSenha() == "") {
            throw new AppException("Null SENHA");
        }
    }

    private boolean usuarioCadastrado(Usuario usuario) {
        try {
            Usuario buscaUsuario = usuarioDAO.getUsuario(usuario.getEmail());
            buscaUsuario.getEmail();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
