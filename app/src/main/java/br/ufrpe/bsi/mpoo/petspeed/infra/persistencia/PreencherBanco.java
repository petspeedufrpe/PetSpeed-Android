package br.ufrpe.bsi.mpoo.petspeed.infra.persistencia;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petspeed.animal.persistencia.AnimalDAO;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petspeed.usuario.dominio.Usuario;

public class PreencherBanco {
    private MedicoServices mServices = new MedicoServices();
    private ClienteServices cServices = new ClienteServices();
    private OrdemServicoServices servicoServices = new OrdemServicoServices();

    public void start() {
        cadastraMedicos(mServices);
        cadastraClientes(cServices);
        cadastraOSs();
    }

    private void cadastraMedicos(MedicoServices mServices) {
        Usuario usuario;
        Pessoa pessoa;
        Endereco endereco;
        Medico medico;

        //MEDICO 1 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med1@med.com");
        usuario.setSenha("12345");

        endereco.setCep("52171011");
        endereco.setNumero(36);
        endereco.setComplemento("apt 101");
        endereco.setUf("PE");
        endereco.setBairro("Dois Irmaos");
        endereco.setLogradouro("R. Manuel de Medeiros");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.0164513);
        endereco.setLongitude(-34.9531421);

        pessoa.setNome("Joao manuel");
        pessoa.setCpf("645389675");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(5.0);
        medico.setCrmv("68444");
        medico.setTelefone("1125698687");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }

        //MEDICO 2 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med2@med.com");
        usuario.setSenha("12345");

        endereco.setCep("52171348");
        endereco.setNumero(1082);
        endereco.setComplemento("sala 3");
        endereco.setUf("PE");
        endereco.setBairro("Dois Irmaos");
        endereco.setLogradouro("Av. Dois Irmaos");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.015605);
        endereco.setLongitude(-34.9444292);

        pessoa.setNome("Miguel Arcanjo");
        pessoa.setCpf("23752395");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.8);
        medico.setCrmv("06856");
        medico.setTelefone("5569868547");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }


        //MEDICO 3 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med3@med.com");
        usuario.setSenha("12345");

        endereco.setCep("52171325");
        endereco.setNumero(16);
        endereco.setComplemento("apt 403");
        endereco.setUf("PE");
        endereco.setBairro("Sitio dos Pintos");
        endereco.setLogradouro("R. Prof. Claudio Selva");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.0132771);
        endereco.setLongitude(-34.9540023);

        pessoa.setNome("Thiago Manuel");
        pessoa.setCpf("7203572");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.4);
        medico.setCrmv("09644");
        medico.setTelefone("8536598658");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }

        //MEDICO 4 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med4@med.com");
        usuario.setSenha("12345");

        endereco.setCep("52171011");
        endereco.setNumero(579);
        endereco.setComplemento("sala 109");
        endereco.setUf("PE");
        endereco.setBairro("Apipucos");
        endereco.setLogradouro("R. Dois Irmaos");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.019182);
        endereco.setLongitude(-34.943102);

        pessoa.setNome("Francisco Albuquerque");
        pessoa.setCpf("765824658");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.8);
        medico.setCrmv("6754357");
        medico.setTelefone("8256965875");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }

        //MEDICO 5 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med5@med.com");
        usuario.setSenha("12345");

        endereco.setCep("52071390");
        endereco.setNumero(423);
        endereco.setComplemento("sala 3, bloco A");
        endereco.setUf("PE");
        endereco.setBairro("Apipucos");
        endereco.setLogradouro("R. Caetés");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.0146247);
        endereco.setLongitude(-34.9409997);

        pessoa.setNome("Edivaldo Nogueira");
        pessoa.setCpf("879665845");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.3);
        medico.setCrmv("8587");
        medico.setTelefone("8365968547");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }

        //MEDICO 6 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med6@med.com");
        usuario.setSenha("12345");

        endereco.setCep("51160280");
        endereco.setNumero(330);
        endereco.setComplemento("sala 502");
        endereco.setUf("PE");
        endereco.setBairro("Imbiribeira");
        endereco.setLogradouro("Av. General Mac Arthur");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.1095715);
        endereco.setLongitude(-34.9107608);

        pessoa.setNome("Irineu Manuel");
        pessoa.setCpf("92385082");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.8);
        medico.setCrmv("8547");
        medico.setTelefone("8125698658");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);

        }

        //MEDICO 7 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med7@med.com");
        usuario.setSenha("12345");

        endereco.setCep("51150400");
        endereco.setNumero(100);
        endereco.setComplemento("apt 1001");
        endereco.setUf("PE");
        endereco.setBairro("Imbiribeira");
        endereco.setLogradouro("R. Leparc");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.1093971);
        endereco.setLongitude(-34.9052494);

        pessoa.setNome("Francisco Mota");
        pessoa.setCpf("769854625");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.9);
        medico.setCrmv("5968");
        medico.setTelefone("8569896354");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);

        }

        //MEDICO 8 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med8@med.com");
        usuario.setSenha("12345");

        endereco.setCep("2351236");
        endereco.setNumero(270);
        endereco.setComplemento("apt 707");
        endereco.setUf("PB");
        endereco.setBairro("Manaíra");
        endereco.setLogradouro("R. Jacinto Dantas");
        endereco.setCidade("João Pessoa");
        endereco.setLatitude(-7.1041287);
        endereco.setLongitude(-34.8322662);

        pessoa.setNome("Joao miguel");
        pessoa.setCpf("987456123");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.9);
        medico.setCrmv("45869");
        medico.setTelefone("1254785698");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }

        //MEDICO 9 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med9@med.com");
        usuario.setSenha("12345");

        endereco.setCep("9856325");
        endereco.setNumero(317);
        endereco.setComplemento("sala 8");
        endereco.setUf("PB");
        endereco.setBairro("Bessa");
        endereco.setLogradouro("R. Ozorio Queiroga de Assis");
        endereco.setCidade("João Pessoa");
        endereco.setLatitude(-7.0732546);
        endereco.setLongitude(-34.8387379);

        pessoa.setNome("Joao Castiel");
        pessoa.setCpf("47586968");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.9);
        medico.setCrmv("75869");
        medico.setTelefone("1256963580");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }


        //MEDICO 10 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med10@med.com");
        usuario.setSenha("12345");

        endereco.setCep("256936585");
        endereco.setNumero(1212);
        endereco.setComplemento("térreo");
        endereco.setUf("RN");
        endereco.setBairro("Areia Preta");
        endereco.setLogradouro("R. Fabrício Pedroza");
        endereco.setCidade("Natal");
        endereco.setLatitude(-5.7856817);
        endereco.setLongitude(-35.1928306);

        pessoa.setNome("Joao Arlequino");
        pessoa.setCpf("14758236");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.7);
        medico.setCrmv("12522");
        medico.setTelefone("1526398540");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }

        //MEDICO 11 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        medico = new Medico();

        usuario.setEmail("med11@med.com");
        usuario.setSenha("12345");

        endereco.setCep("7856955");
        endereco.setNumero(185);
        endereco.setComplemento("Bloco A");
        endereco.setUf("PE");
        endereco.setBairro("Casa Caiada");
        endereco.setLogradouro("R. Carlos Pessoa Monteiro");
        endereco.setCidade("Olinda");
        endereco.setLatitude(-7.9899527);
        endereco.setLongitude(-34.8431607);

        pessoa.setNome("Joao Theodoro");
        pessoa.setCpf("75848452");
        pessoa.setEndereco(endereco);

        medico.setAvaliacao(4.5);
        medico.setCrmv("42522");
        medico.setTelefone("8796585240");
        medico.setUsuario(usuario);
        medico.setDadosPessoais(pessoa);

        try {
            mServices.cadastraMedico(medico, usuario);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }

    }


    private void cadastraClientes(ClienteServices cService) {
        Usuario usuario;
        Pessoa pessoa;
        Endereco endereco;
        Cliente cliente;
        Animal animal;


        //CLIENTE 1 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        cliente = new Cliente();


        usuario.setEmail("gabriel@alves.com");
        usuario.setSenha("12345");

        endereco.setCep("51020020");
        endereco.setNumero(2520);
        endereco.setComplemento("apt 1001");
        endereco.setUf("PE");
        endereco.setBairro("Boa Viagem");
        endereco.setLogradouro("Av. conselheiro aguiar");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.1115339);
        endereco.setLongitude(-34.8942523);

        pessoa.setNome("Gabriel Alves");
        pessoa.setCpf("987654321");
        pessoa.setEndereco(endereco);

        cliente.setAvaliacao(5.0);
        cliente.setTelefone("8759686584");
        cliente.setDadosPessoais(pessoa);
        cliente.setUsuario(usuario);


        try {
            cliente = cService.cadastraCliente(cliente, usuario);
            Sessao.instance.setCliente(cliente);
        } catch (AppException e) {
            Log.getStackTraceString(e);

        }
        cliente = Sessao.instance.getCliente();

        //ANIMAL 1 ##################################

        animal = new Animal();
        animal.setNome("Toto");
        animal.setRaca("Labrador");
        animal.setPeso(12.3);
        animal.setNascimento(7);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 2 ##################################

        animal = new Animal();
        animal.setNome("Zeus");
        animal.setRaca("Poodle");
        animal.setPeso(7.5);
        animal.setNascimento(5);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 3 ##################################

        animal = new Animal();
        animal.setNome("Tody");
        animal.setRaca("Rottweiler");
        animal.setPeso(15.9);
        animal.setNascimento(8);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 4 ##################################

        animal = new Animal();
        animal.setNome("Zeus");
        animal.setRaca("labrador");
        animal.setPeso(12.9);
        animal.setNascimento(7);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 5 ##################################

        animal = new Animal();
        animal.setNome("Bob");
        animal.setRaca("golden retriver");
        animal.setPeso(15.7);
        animal.setNascimento(9);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);


        Sessao.instance.reset();

        //CLIENTE 2 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        cliente = new Cliente();


        usuario.setEmail("cl1@cl.com");
        usuario.setSenha("12345");

        endereco.setCep("520991092");
        endereco.setNumero(30);
        endereco.setComplemento("apt 107");
        endereco.setUf("PE");
        endereco.setBairro("Corrego do Jenipapo");
        endereco.setLogradouro("R. Nove de Março");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.0087481);
        endereco.setLongitude(-34.9380275);

        pessoa.setNome("Tadeu Josué");
        pessoa.setCpf("5385610");
        pessoa.setEndereco(endereco);

        cliente.setAvaliacao(4.4);
        cliente.setTelefone("8236598685");
        cliente.setDadosPessoais(pessoa);
        cliente.setUsuario(usuario);


        try {
            cliente = cService.cadastraCliente(cliente, usuario);
            Sessao.instance.setCliente(cliente);
        } catch (AppException e) {
            Log.getStackTraceString(e);

        }
        cliente = Sessao.instance.getCliente();

        //ANIMAL 1 ##################################

        animal = new Animal();
        animal.setNome("Toto");
        animal.setRaca("Mestiço");
        animal.setPeso(7.7);
        animal.setNascimento(9);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 2 ##################################

        animal = new Animal();
        animal.setNome("Zeus");
        animal.setRaca("shiba inu");
        animal.setPeso(10.1);
        animal.setNascimento(3);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 3 ##################################

        animal = new Animal();
        animal.setNome("Thor");
        animal.setRaca("Rottweiler");
        animal.setPeso(14.8);
        animal.setNascimento(4);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);


        Sessao.instance.reset();

        //CLIENTE 3 ####################################

        usuario = new Usuario();
        pessoa = new Pessoa();
        endereco = new Endereco();
        cliente = new Cliente();


        usuario.setEmail("cl2@cl.com");
        usuario.setSenha("12345");

        endereco.setCep("52171011");
        endereco.setNumero(590);
        endereco.setComplemento("apt 303");
        endereco.setUf("PE");
        endereco.setBairro("Dois Irmãos");
        endereco.setLogradouro("R. Manuel de Medeiros");
        endereco.setCidade("Recife");
        endereco.setLatitude(-8.0148223);
        endereco.setLongitude(-34.9496496);

        pessoa.setNome("José Alves");
        pessoa.setCpf("87049373");
        pessoa.setEndereco(endereco);

        cliente.setAvaliacao(4.1);
        cliente.setTelefone("8658547589");
        cliente.setDadosPessoais(pessoa);
        cliente.setUsuario(usuario);


        try {
            cliente = cService.cadastraCliente(cliente, usuario);
            Sessao.instance.setCliente(cliente);
        } catch (AppException e) {
            Log.getStackTraceString(e);
        }
        cliente = Sessao.instance.getCliente();

        //ANIMAL 1 ##################################

        animal = new Animal();
        animal.setNome("Jupter");
        animal.setRaca("Labrador");
        animal.setPeso(17.3);
        animal.setNascimento(6);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 2 ##################################

        animal = new Animal();
        animal.setNome("Saturno");
        animal.setRaca("Shitsu");
        animal.setPeso(7.9);
        animal.setNascimento(10);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 3 ##################################

        animal = new Animal();
        animal.setNome("Tobias");
        animal.setRaca("Bulldog");
        animal.setPeso(18.5);
        animal.setNascimento(12);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);


        Sessao.instance.reset();
    }

    private void cadastraOSs() {
        Medico medico;
        Cliente cliente;
        Triagem triagem;
        OrdemServico os;

        // OS 1 ############################################

        os = new OrdemServico();

        medico = mServices.getMedicoById(1);
        cliente = cServices.getClienteCompleto(1);
        triagem = new Triagem();
        triagem.setSintomas("Lorem, ipsum, dolor, sit, amet");
        triagem.setOutros("Lorem ipsum dolor sit amet");
        os.setCliente(cliente);
        os.setAnimal(new AnimalDAO().getAnimalById(1));
        os.setDescricao("Lorem ipsum dolor sit amet");
        os.setPrioridade(OrdemServico.Prioridade.ALTA);
        os.setStatus(OrdemServico.Status.AGUARDANDO_ATENDIMENTO);
        os.setTriagem(triagem);
        os.setMedico(medico);
        servicoServices.cadastraOS(os, os.getTriagem());
    }


}
