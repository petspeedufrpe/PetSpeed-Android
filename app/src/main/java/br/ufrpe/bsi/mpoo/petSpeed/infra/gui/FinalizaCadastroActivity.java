package br.ufrpe.bsi.mpoo.petSpeed.infra.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petSpeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petSpeed.infra.app.PetSpeedApp;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ApiGeocoder;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ApiRequestService;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.GeocodeRequestCallbackListener;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.SessaoCadastro;
import br.ufrpe.bsi.mpoo.petSpeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petSpeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.dominio.Endereco;
import br.ufrpe.bsi.mpoo.petSpeed.pessoa.negocio.PessoaServices;

public class FinalizaCadastroActivity extends AppCompatActivity {

    RegisterTask registerTask = null;

    boolean isRegisterTaskRunning = false;

    private ApiGeocoder geocoder = new ApiGeocoder(PetSpeedApp.getContext());

    Button btnCancelar, btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_finaliza_cadastro);

        btnCadastrar = (Button) findViewById(R.id.finaliza_cadastro);
        btnCancelar = (Button) findViewById(R.id.cancela_cadastro);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegisterTaskRunning) {
                    Toast.makeText(FinalizaCadastroActivity.this, "Processando...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (registerTask == null) {
                    isRegisterTaskRunning = true;
                    registerTask = new RegisterTask();
                    registerTask.execute((Void) null);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FinalizaCadastroActivity.this, "Cadastro cancelado.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
            }
        });
    }

    private class RegisterTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String enderecoF = getEnderecoFormated();
            ApiRequestService geocodeReq = new ApiRequestService();
            geocodeReq.geocodeRequest(enderecoF, new GeocodeRequestCallbackListener<Map<Enum, Object>>() {
                @Override
                public void onGeocodeCallback(Map latLng) {
                    if (latLng.get(ApiRequestService.GeoCodeCoord.RESULT) == ApiRequestService.GeoCodeCoord.SUCCESS) {
                        double lat = (double) latLng.get(ApiRequestService.GeoCodeCoord.LAT);
                        double lng = (double) latLng.get(ApiRequestService.GeoCodeCoord.LNG);
                        Endereco endereco = SessaoCadastro.instance.getEndereco();
                        endereco.setLatidude(lat);
                        endereco.setLongitude(lng);
                        try {
                            cadastrar(endereco);
                            startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
                            finish();
                            registerTask = null;
                            isRegisterTaskRunning = false;
                        } catch (AppException e) {
                            Toast.makeText(FinalizaCadastroActivity.this, "Ops! parece que algo deu errado.", Toast.LENGTH_LONG).show();
                            registerTask = null;
                            isRegisterTaskRunning = false;
                        }
                    } else {
                        Toast.makeText(FinalizaCadastroActivity.this, "Por favor, verifique seus dados ou a conexão com a internet.", Toast.LENGTH_LONG).show();
                        registerTask = null;
                        isRegisterTaskRunning = false;
                    }
                }
            });
            return null;
        }
    }

    private String getEnderecoFormated() {
        Endereco endereco = SessaoCadastro.instance.getEndereco();
        StringBuilder parserEndereco = new StringBuilder();
        parserEndereco.append(endereco.getLogradouro());
        parserEndereco.append(", ");
        parserEndereco.append(endereco.getNumero());
        parserEndereco.append(" ");
        parserEndereco.append(endereco.getBairro());
        return parserEndereco.toString();
    }

    private void cadastrar(Endereco endereco) throws AppException {
        ContasDeUsuario tipo = SessaoCadastro.instance.getTipo();
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
            throw new AppException("Erro");
        }
    }


    private void cadastraCliente(Cliente cliente) throws AppException{
        PessoaServices pessoaServices = new PessoaServices();
        long idPessoa = pessoaServices.cadastraPessoa(cliente.getDadosPessoais(), cliente.getDadosPessoais().getEndereco());
        cliente.getDadosPessoais().setId(idPessoa);
        ClienteServices clienteServices = new ClienteServices();
        clienteServices.cadastraCliente(cliente, cliente.getUsuario());
        Toast.makeText(FinalizaCadastroActivity.this, "Cadastro realizado.", Toast.LENGTH_LONG).show();
    }

    private void cadastraMedico(Medico medico) throws AppException {
        PessoaServices pessoaServices = new PessoaServices();
        long idPessoa = pessoaServices.cadastraPessoa(medico.getDadosPessoais(), medico.getDadosPessoais().getEndereco());
        medico.getDadosPessoais().setId(idPessoa);
        MedicoServices medicoServices = new MedicoServices();
        medicoServices.cadastraMedico(medico, medico.getUsuario());
        Toast.makeText(FinalizaCadastroActivity.this, "Cadastro realizado.", Toast.LENGTH_LONG).show();
    }

    /*public void setLatLong(Endereco endereco) throws IOException {
        StringBuilder parserEndereco = new StringBuilder();
        parserEndereco.append(endereco.getLogradouro());
        parserEndereco.append(", ");
        parserEndereco.append(endereco.getNumero());
        Map<String, Double> latlngCoord = geocoder.getPositions(parserEndereco.toString());
        endereco.setLatidude(latlngCoord.get(ApiGeocoder.GeocodeLatLng.LAT.getStr()));
        endereco.setLongitude(latlngCoord.get(ApiGeocoder.GeocodeLatLng.LNG.getStr()));
    }*/
}
