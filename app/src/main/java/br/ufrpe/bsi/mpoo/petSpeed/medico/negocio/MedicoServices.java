package br.ufrpe.bsi.mpoo.petSpeed.medico.negocio;

import br.ufrpe.bsi.mpoo.petSpeed.clinica.persistencia.ClinicaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.persistencia.MedicoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class MedicoServices {

    private MedicoDAO medicoDAO = new MedicoDAO();

    private EnderecoDAO enderecoDAO = new EnderecoDAO();

    private ClinicaDAO clinicaDAO = new ClinicaDAO();

    private UsuarioDAO usuarioDAO = new UsuarioDAO();


    public Medico cadastraMedico(Medico medico, Usuario usuario) throws AppException {
        try {
            this.checkNull(medico);
        } catch (AppException e) {
            throw new AppException(String.valueOf(e));
        }
        Usuario usuarioReferencia = usuarioDAO.getUsuario(usuario.getEmail());
        if(usuarioReferencia!=null){
            if(!usuarioPossuiMedico(usuarioReferencia.getEmail())){
                medico.getUsuario().setId(usuarioReferencia.getId());
                long idmedico = medicoDAO.cadastraMedico(medico);
                medico.setId(idmedico);
                return medico;
            }else{
                throw new AppException("Usuário já possui conta de médico");
            }
        }else{
        long idUsuario = usuarioDAO.cadastrarUsuario(usuario);
        medico.getUsuario().setId(idUsuario);
        long idmedico = medicoDAO.cadastraMedico(medico);
        medico.setId(idmedico);
        return medico;
        }
    }

    private void checkNull(Medico medico) throws AppException {
        if (medico.getUsuario() == null) {
            throw new AppException("Null attribute");
        }
        if (medico.getDadosPessoais() == null) {
            throw new AppException("dados pessoais null");
        }
        if (medico.getDadosPessoais().getId() == 0) {
            throw new AppException("dados pessoais sem id");
        }
        if (medico.getDadosPessoais().getEndereco() == null) {
            throw new AppException("endereco dados pessoais null");
        }
    }

    public boolean usuarioPossuiMedico(Medico medico) {
        Usuario usuarioReferencia = usuarioDAO.getUsuario(medico.getUsuario().getEmail());
        try {
            usuarioReferencia.getId();
        } catch (Exception e) {
            return false;
        }
        try {
            Medico medicoReferencia = medicoDAO.getMedicoByFkUsuario(usuarioReferencia.getId());
            medicoReferencia.getId();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean usuarioPossuiMedico(String attemptedLoginEmail) {
        Usuario usuarioReferencia = usuarioDAO.getUsuario(attemptedLoginEmail);
        try {
            usuarioReferencia.getId();
        } catch (Exception e) {
            return false;
        }
        try {
            Medico medicoReferencia = medicoDAO.getMedicoByFkUsuario(usuarioReferencia.getId());
            medicoReferencia.getId();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void login(String email, String senha) throws AppException {
        Usuario usuario = usuarioDAO.getUsuario(email, senha);
        if (usuario == null) {
            throw new AppException("Credenciais Inválidas.");
        } else {
            Medico medico = medicoDAO.getMedicoByFkUsuario(usuario.getId());
            Sessao.instance.setUsuario(usuario);
            Sessao.instance.setMedico(medico);
        }
    }

    public void logout() {
        Sessao sessao = Sessao.instance;
        sessao.reset();
    }

    public void deletaMedico(Medico medico) {
        if (medicoDAO.getMedicoById(medico.getId()) != null) {
            medicoDAO.deletaMedico(medico);
        }
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
