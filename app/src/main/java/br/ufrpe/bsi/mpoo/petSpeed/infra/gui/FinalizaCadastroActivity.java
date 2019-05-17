package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

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
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;
import br.ufrpe.bsi.mpoo.petSpeed.usuario.dominio.Usuario;

public class FinalizaCadastroActivity extends AppCompatActivity {

    Button btnCancelar, btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finaliza_cadastro);
        btnCadastrar = (Button) findViewById(R.id.finaliza_cadastro);
        btnCancelar = (Button) findViewById(R.id.cancela_cadastro);
        final Intent registerEnd = getIntent();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle accountBundle = registerEnd.getExtras().getBundle("bundle");
                Endereco endereco = (Endereco)accountBundle.getSerializable("endereco");
                String tipo = (String)accountBundle.getString("tipo");
                Toast.makeText(FinalizaCadastroActivity.this,tipo,Toast.LENGTH_LONG).show();

                if (tipo.equals("cliente")){
                    Toast.makeText(FinalizaCadastroActivity.this,"Classe cliente",Toast.LENGTH_LONG).show();
                     Cliente cliente = (Cliente) accountBundle.getSerializable("cliente");
                     cliente.getDadosPessoais().setEndereco(endereco);
                     cadastraCliente(cliente);
                }

                else if (tipo.equals("medico")){
                    Toast.makeText(FinalizaCadastroActivity.this,"Classe medico",Toast.LENGTH_LONG).show();
                    Medico medico = (Medico) accountBundle.getSerializable("medico");
                    medico.getDadosPessoais().setEndereco(endereco);
                    cadastraMedico(medico);
                }

                else if (tipo.equals("clinica")){
                    Toast.makeText(FinalizaCadastroActivity.this,"Classe clinica",Toast.LENGTH_LONG).show();
                    Clinica clinica = (Clinica) accountBundle.getSerializable("clinica");
                    //clinica.setEnderecos();
                    //cadastraClinica(clinica);
                }

                else {
                    Toast.makeText(FinalizaCadastroActivity.this,"Ops! parece que algo deu errado.",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FinalizaCadastroActivity.this,"Cadastro cancelado.",Toast.LENGTH_LONG).show();
                startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));

            }
        });
    }


    private void cadastraCliente(Cliente cliente) {
        PessoaServices pessoaServices = new PessoaServices();
        long idPessoa = pessoaServices.cadastraPessoa(cliente.getDadosPessoais(), cliente.getDadosPessoais().getEndereco());
        cliente.getDadosPessoais().setId(idPessoa);
        ClienteServices clienteServices = new ClienteServices();
        clienteServices.cadastraCliente(cliente, cliente.getUsuario());
        Toast.makeText(FinalizaCadastroActivity.this, "Cadastro realizado.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
    }

    private void cadastraMedico(Medico medico) {
        PessoaServices pessoaServices = new PessoaServices();
        long idPessoa = pessoaServices.cadastraPessoa(medico.getDadosPessoais(), medico.getDadosPessoais().getEndereco());
        medico.getDadosPessoais().setId(idPessoa);
        MedicoServices medicoServices = new MedicoServices();
        try {
            medicoServices.cadastraMedico(medico, medico.getUsuario());
        } catch (AppException e) {
            Toast.makeText(FinalizaCadastroActivity.this, "Ops! parece que algo deu errado.", Toast.LENGTH_LONG).show();

        }
        Toast.makeText(FinalizaCadastroActivity.this, "Cadastro realizado.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
    }

}
