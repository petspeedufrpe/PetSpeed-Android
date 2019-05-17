package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.clinica.dominio.Clinica;
import br.ufrpe.bsi.mpoo.petSpeed.clinica.negocio.ClinicaServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.gui.LoginActivity;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;

public class FinalizaCadastroActivity extends AppCompatActivity {

    Button btnCancelar, btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finaliza_cadastro);
        btnCadastrar = (Button) findViewById(R.id.finaliza_cadastro);
        btnCancelar = (Button) findViewById(R.id.cancela_cadastro);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalIntent = getIntent();
                Bundle bundle = finalIntent.getExtras().getBundle("bundle");
                String tipo = (String) bundle.getString("tipo");

                if(tipo.equals("cliente")){
                    Cliente cliente = (Cliente) bundle.getSerializable("cliente");
                    cadastraCliente(cliente);
                }
                else if (tipo.equals("medico")){
                    Medico medico = (Medico) bundle.getSerializable("medico");
                    cadastraMedico(medico);
                }
                else if (tipo.equals("clinica")){
                    Clinica clinica = (Clinica) bundle.getSerializable("clinica");
                    cadastraClinica(clinica);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
            }
        });
    }


    public Endereco getEnderecoByAct() {
        Intent finaIntent = getIntent();
        Bundle bundle = finaIntent.getExtras().getBundle("bundle");
        Endereco endereco = (Endereco) bundle.getSerializable("endereco");
        return  endereco;
    }


    /**
     *
     * Os três metodos para cadastrar cliente, medico e clinica. Dependendo de qual objeto seja trazido
     * pelas Intents, seleciona o metodo especifico do objeto
     */
    private void cadastraCliente(Cliente cliente) {
        Endereco endereco = getEnderecoByAct();
        cliente.getDadosPessoais().setEndereco(endereco);
        PessoaServices pessoaServices = new PessoaServices();
        long idPessoa = pessoaServices.cadastraPessoa(cliente.getDadosPessoais(), endereco);
        cliente.getDadosPessoais().setId(idPessoa);
        ClienteServices clienteServices = new ClienteServices();
        clienteServices.cadastraCliente(cliente, cliente.getUsuario());
        Toast.makeText(FinalizaCadastroActivity.this, "Cadastro realizado.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
    }

    private void cadastraClinica(Clinica clinica) {
        Endereco endereco = getEnderecoByAct();
        ClinicaServices clinicaServices = new ClinicaServices();
        clinicaServices.cadastraClinica(clinica, endereco);
        Toast.makeText(FinalizaCadastroActivity.this, "Cadastro realizado.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
    }

    private void cadastraMedico(Medico medico) {
        Endereco endereco = getEnderecoByAct();
        medico.getDadosPessoais().setEndereco(endereco);
        PessoaServices pessoaServices = new PessoaServices();
        long idPessoa = pessoaServices.cadastraPessoa(medico.getDadosPessoais(), endereco);
        medico.getDadosPessoais().setId(idPessoa);
        MedicoServices medicoServices = new MedicoServices();
        medicoServices.cadastraMedico(medico, medico.getUsuario());
        Toast.makeText(FinalizaCadastroActivity.this, "Cadastro realizado.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));


    }
}
