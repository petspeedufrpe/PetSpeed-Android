package br.ufrpe.bsi.mpoo.petSpeed.infra.Persistencia;

import br.ufrpe.bsi.mpoo.petSpeed.animal.dominio.Animal;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Pessoa;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class PreencherBanco {
    public void start() {
        MedicoServices mServices = new MedicoServices();
        ClienteServices clienteServices = new ClienteServices();

        cadastraMedicos(mServices);
        cadastraClientes(clienteServices);

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            cliente = cService.cadastraCliente(cliente,usuario);
            Sessao.instance.setCliente(cliente);
        } catch (AppException e) {
            e.printStackTrace();
        }
        cliente = Sessao.instance.getCliente();

        //ANIMAL 1 ##################################

        animal = new Animal();
        animal.setNome("Encapsulamentovaldo");
        animal.setRaca("Labrador");
        animal.setPeso(12.3);
        animal.setNascimento(7);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 2 ##################################

        animal = new Animal();
        animal.setNome("Polimorfismineuso");
        animal.setRaca("Poodle");
        animal.setPeso(7.5);
        animal.setNascimento(5);
        animal.setFkCliente(cliente.getId());
        cService.cadastraAnimal(animal);

        //ANIMAL 3 ##################################

        animal = new Animal();
        animal.setNome("Herrançoso");
        animal.setRaca("Rottweiler");
        animal.setPeso(15.9);
        animal.setNascimento(8);
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
            cliente = cService.cadastraCliente(cliente,usuario);
            Sessao.instance.setCliente(cliente);
        } catch (AppException e) {
            e.printStackTrace();
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
            cliente = cService.cadastraCliente(cliente,usuario);
            Sessao.instance.setCliente(cliente);
        } catch (AppException e) {
            e.printStackTrace();
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


}
