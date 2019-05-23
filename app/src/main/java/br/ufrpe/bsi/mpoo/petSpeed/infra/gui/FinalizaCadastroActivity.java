package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ApiGeocoder;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.SessaoCadastro;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;

public class FinalizaCadastroActivity extends AppCompatActivity {

    private ApiGeocoder geocoder = new ApiGeocoder(PetSpeedApp.getContext());

    Button btnCancelar, btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_finaliza_cadastro);


        btnCadastrar = (Button) findViewById(R.id.finaliza_cadastro);
        btnCancelar = (Button) findViewById(R.id.cancela_cadastro);
        final Intent registerEnd = getIntent();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cadastrar();
                } catch (IOException e) {
                    Toast.makeText(FinalizaCadastroActivity.this,"Verifique sua Conexão",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FinalizaCadastroActivity.this, "Cadastro cancelado.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void cadastrar() throws IOException {
        ContasDeUsuario tipo = SessaoCadastro.instance.getTipo();
        Endereco endereco = SessaoCadastro.instance.getEndereco();
        setLatLong(endereco);
        if (tipo == ContasDeUsuario.CLIENTE) {
            Cliente cliente = SessaoCadastro.instance.getCliente();
            cliente.getDadosPessoais().setEndereco(endereco);
            cadastraCliente(cliente);
        } else if (tipo == ContasDeUsuario.MEDICO) {
            Medico medico = SessaoCadastro.instance.getMedico();
            medico.getDadosPessoais().setEndereco(endereco);
            cadastraMedico(medico);
        } else if (tipo == ContasDeUsuario.CLINICA) {
        } else {
            Toast.makeText(FinalizaCadastroActivity.this, "Ops! parece que algo deu errado.", Toast.LENGTH_LONG).show();
        }
    }


    private void cadastraCliente(Cliente cliente) {
        PessoaServices pessoaServices = new PessoaServices();
        long idPessoa = pessoaServices.cadastraPessoa(cliente.getDadosPessoais(), cliente.getDadosPessoais().getEndereco());
        cliente.getDadosPessoais().setId(idPessoa);
        ClienteServices clienteServices = new ClienteServices();
        clienteServices.cadastraCliente(cliente, cliente.getUsuario());
        Toast.makeText(FinalizaCadastroActivity.this, "Cadastro realizado.", Toast.LENGTH_LONG).show();
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
    }

    public void setLatLong(Endereco endereco) throws IOException {
        StringBuilder parserEndereco = new StringBuilder();
        parserEndereco.append(endereco.getLogradouro());
        parserEndereco.append(", ");
        parserEndereco.append(endereco.getNumero());
        Map<String,Double> latlngCoord = geocoder.getPositions(parserEndereco.toString());
          endereco.setLatidude(latlngCoord.get(ApiGeocoder.GeocodeLatLng.LAT.getStr()));
          endereco.setLongitude(latlngCoord.get(ApiGeocoder.GeocodeLatLng.LNG.getStr()));
    }


}
