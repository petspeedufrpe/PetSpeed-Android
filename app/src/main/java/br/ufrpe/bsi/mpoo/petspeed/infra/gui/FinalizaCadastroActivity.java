package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.dominio.Cliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.ApiRequestService;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.ContasDeUsuario;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.GeocodeRequestCallbackListener;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoCadastro;
import br.ufrpe.bsi.mpoo.petspeed.medico.dominio.Medico;
import br.ufrpe.bsi.mpoo.petspeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petspeed.pessoa.dominio.Endereco;

public class FinalizaCadastroActivity extends AppCompatActivity {

    RegisterTask registerTask = null;

    boolean isRegisterTaskRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_finaliza_cadastro);

        Button btnCadastrar = findViewById(R.id.finaliza_cadastro);
        Button btnCancelar = findViewById(R.id.cancela_cadastro);

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
            String enderecoF = registerTask.getEnderecoFormated();
            ApiRequestService geocodeReq = new ApiRequestService();
            geocodeReq.geocodeRequest(enderecoF, new GeocodeRequestCallbackListener<Map<Enum, Object>>() {
                @Override
                public void onGeocodeCallback(Map latLng) {
                    if (latLng.get(ApiRequestService.GeoCodeCoord.RESULT) == ApiRequestService.GeoCodeCoord.SUCCESS) {
                        Endereco endereco = setCoordinates(latLng);
                        try {
                            cadastrar(endereco);
                            Toast.makeText(FinalizaCadastroActivity.this, "Cadastro realizado.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(FinalizaCadastroActivity.this, LoginActivity.class));
                            finish();
                            resetTask();
                        } catch (AppException e) {
                            Toast.makeText(FinalizaCadastroActivity.this, "Ops! parece que algo deu errado.", Toast.LENGTH_LONG).show();
                            resetTask();
                        }
                    } else {
                        Toast.makeText(FinalizaCadastroActivity.this, "Por favor, verifique seus dados ou a conexão com a internet.", Toast.LENGTH_LONG).show();
                        resetTask();
                    }
                }
            });
            return null;
        }

        private void resetTask() {
            registerTask = null;
            isRegisterTaskRunning = false;
        }

        private Endereco setCoordinates(Map latLng) {
            double lat = (double) latLng.get(ApiRequestService.GeoCodeCoord.LAT);
            double lng = (double) latLng.get(ApiRequestService.GeoCodeCoord.LNG);
            Endereco endereco = SessaoCadastro.instance.getEndereco();
            endereco.setLatitude(lat);
            endereco.setLongitude(lng);
            return endereco;
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
            } else {
                throw new AppException("Erro");
            }
        }

        private void cadastraCliente(Cliente cliente) throws AppException {
            ClienteServices clienteServices = new ClienteServices();
            clienteServices.cadastraCliente(cliente, cliente.getUsuario());
        }


        private void cadastraMedico(Medico medico) throws AppException {
            MedicoServices medicoServices = new MedicoServices();
            medicoServices.cadastraMedico(medico, medico.getUsuario());

        }


    }


}
