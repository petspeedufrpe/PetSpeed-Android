package br.ufrpe.bsi.mpoo.petSpeed.medico.negocio;

import br.ufrpe.bsi.mpoo.petSpeed.clinica.persistencia.ClinicaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
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


    public long cadastraMedico(Medico medico) throws AppException {
        try {
            this.checkNull(medico);
        } catch (AppException e) {
            throw new AppException(String.valueOf(e));
        }
        if (this.usuarioPossuiMedico(medico)) {
            throw new AppException("UsuarioPossuiMedico");
        } else {
            long id = medicoDAO.cadastraMedico(medico);
            return id;
        }
    }

    private void checkNull(Medico medico) throws AppException {
        if (medico.getUsuario() == null) {
            throw new AppException("Null attribute");
        }
        if (medico.getUsuario().getId() == 0) {
            throw new AppException("usuario medico sem id");
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
        if (medico.getDadosPessoais().getEndereco().getId() == 0) {
            throw new AppException("endereco sem id");
        }
        if (medico.getAvaliacao() > 5) {
            throw new AppException("avaliacao maior q 5");
        }
    }

    private boolean usuarioPossuiMedico(Medico medico) throws AppException {
        try {
            Medico medicoReferencia = medicoDAO.getMedicoByFkUsuario(medico.getUsuario().getId());
            medicoReferencia.getId();
            if (medicoReferencia.getUsuario().getId() == medico.getUsuario().getId()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
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
