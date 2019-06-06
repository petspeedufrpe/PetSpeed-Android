package br.ufrpe.bsi.mpoo.petSpeed.medico.negocio;

import java.util.LinkedList;
import java.util.List;

import br.ufrpe.bsi.mpoo.petSpeed.clinica.persistencia.ClinicaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.persistencia.MedicoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.EnderecoDAO;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.persistencia.PessoaDAO;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.persistencia.UsuarioDAO;

public class MedicoServices {

    private MedicoDAO medicoDAO = new MedicoDAO();

    private EnderecoDAO enderecoDAO = new EnderecoDAO();

    private ClinicaDAO clinicaDAO = new ClinicaDAO();

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PessoaDAO pessoaDAO = new PessoaDAO();


    public Medico cadastraMedico(Medico medico, Usuario usuario) throws AppException {
        try {
            this.checkNull(medico);
        } catch (AppException e) {
            throw new AppException(String.valueOf(e));
        }
        Usuario usuarioReferencia = usuarioDAO.getUsuario(usuario.getEmail());
        if (usuarioReferencia != null) {
            if (!usuarioPossuiMedico(usuarioReferencia.getEmail())) {
                PessoaServices pessoaServices = new PessoaServices();
                Pessoa pessoaReferencia = pessoaServices.getPessoaByFkUsuario(usuarioReferencia.getId());
                medico.setDadosPessoais(pessoaReferencia);
                medico.setUsuario(usuarioReferencia);
                long idmedico = medicoDAO.cadastraMedico(medico);
                medico.setId(idmedico);
                return medico;
            } else {
                throw new AppException("Usuário já possui conta de médico");
            }
        } else {
            long idUsuario = usuarioDAO.cadastrarUsuario(usuario);
            medico.getUsuario().setId(idUsuario);
            medico.getDadosPessoais().setFkUsuario(idUsuario);
            PessoaServices pessoaServices = new PessoaServices();
            long idPessoa = pessoaServices.cadastraPessoa(medico.getDadosPessoais(), medico.getDadosPessoais().getEndereco());
            medico.getDadosPessoais().setId(idPessoa);
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

        if (medico.getDadosPessoais().getEndereco() == null) {
            throw new AppException("endereco dados pessoais null");
        }
    }

    public List<Medico> getMedicosInRaio(double radius, double userLat, double userLng) {
        final double kmInLatLng = 0.008983;
        double latDownRange = 0;
        double latUpRange = 0;
        double lngDownRange = 0;
        double lngUpRange = 0;
        if (userLat<0){
            latDownRange = -1*(Math.abs(userLat) - (radius * kmInLatLng));
            latUpRange = -1*(Math.abs(userLat) + (radius * kmInLatLng));
        } else {

            latDownRange = userLat - (radius * kmInLatLng);
            latUpRange = userLat + (radius * kmInLatLng);
        }
        if (userLng<0){
            lngDownRange = -1*(Math.abs(userLng) - (radius * kmInLatLng));
            lngUpRange = -1*(Math.abs(userLng) + (radius * kmInLatLng));

        } else {
            lngDownRange = userLng - (radius * kmInLatLng);
            lngUpRange = userLng + (radius * kmInLatLng);
        }

        List<Endereco> enderecosInRadius = enderecoDAO.getEnderecosByLatLngInterval(latDownRange, latUpRange, lngDownRange, lngUpRange);
        List<Medico> medicos = new LinkedList<>();
        for (Endereco endereco : enderecosInRadius) {
            if (endereco.getFkPessoa() != 0) {
                Medico medico = medicoDAO.getMedicoByFkPessoa(endereco.getFkPessoa());
                if (medico != null) {
                    medicos.add(medico);
                }
            }
        }
        return medicos;
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

    public Double getAvaliacaoByIdPessoa(long idPessoa) {
        Medico medico = medicoDAO.getMedicoByFkPessoa(idPessoa);
        return medico.getAvaliacao();
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
