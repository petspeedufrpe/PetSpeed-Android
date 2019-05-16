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
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
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
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastraCliente();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
            }
        });
    }

    public Cliente getClienteByAct() {
        Intent registerEnd = getIntent();
        Cliente cliente = (Cliente) registerEnd.getExtras().getSerializable("cliente");
        return cliente;
    }

    public Endereco getEnderecoByAct() {
        Intent registerEnd = getIntent();
        Endereco endereco = (Endereco) registerEnd.getExtras().getSerializable("endereco");
        return endereco;
    }

    private void cadastraCliente() {
        Cliente cliente = getClienteByAct();
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
}
