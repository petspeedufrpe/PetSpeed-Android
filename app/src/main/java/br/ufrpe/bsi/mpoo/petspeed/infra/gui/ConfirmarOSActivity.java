package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.HomeClienteActivity;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.StatusOsCliente;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.ViewSintomasAnimalAcitivity;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.RequestServiceBayesNet;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;
import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.DiseasesService;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.DiseaseProb;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemXsintomaDAO;

public class ConfirmarOSActivity extends AppCompatActivity {
    private TextView nome;
    private TextView endereco;
    private TextView avaliacao;
    private TextView nomeAnimal;
    private TextView raca;
    private TextView prioridade;
    private TextView data;
    private String mNome;
    private String mBairro;
    private String mRua;
    private String mNumero;
    private String mAvaliacao;
    private String mNomeAnimal;
    private String mRaca;
    private String mPrioridade;
    private String mData;
    private OrdemServico ordemServico;
    private Triagem triagem;
    private TriagemDAO triagemDAO = new TriagemDAO();
    private OrdemServicoServices ordemServicoServices = new OrdemServicoServices();
    private TriagemXsintomaDAO triagemXsintomaDAO = new TriagemXsintomaDAO();
    private DiseasesService diseasesService = new DiseasesService();
    private RegisterTask registerTask = null;
    private boolean isTaskRunning;
    private String res = "";
    private ArrayList<DiseaseProb> diseases = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_os_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initOS();
        findTexts();
        getAllTexts();
        setTextsNome();

        Button confirmarAtendimento = findViewById(R.id.btn_confirmar_os);
        Button abortar = findViewById(R.id.btn_cancelar_os);
        TextView verSintomas = findViewById(R.id.fragPopSintomas);

        confirmarAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTaskRunning){
                    Toast.makeText(getBaseContext(),"Processando...",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (registerTask == null){
                    Log.e("REQUEST",res);
                    isTaskRunning = true;
                    registerTask = new RegisterTask();
                    registerTask.execute((Void)null);
                }
            }
        });

        abortar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmarOSActivity.this, "Abortado.", Toast.LENGTH_LONG).show();
                SessaoAgendamento.instance.reset();
                startActivity(new Intent(ConfirmarOSActivity.this, HomeClienteActivity.class));
                finish();
            }
        });

        verSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sessao.instance.setOs(ordemServico);
                startActivity(new Intent(ConfirmarOSActivity.this,ViewSintomasAnimalAcitivity.class));
            }
        });


    }


    private void findTexts(){
        nome = findViewById(R.id.fragPopUpMedNome);
        endereco = findViewById(R.id.fragPopUpMedEnd);
        avaliacao = findViewById(R.id.fragPopUpAval);
        nomeAnimal = findViewById(R.id.fragPopUpAnimNome);
        raca = findViewById(R.id.fragPopUpAnimRaca);
        prioridade = findViewById(R.id.fragPopUpPrioridade);
        data = findViewById(R.id.fragPopUpDate);
    }

    private void setTextsNome(){
        nome.setText("Nome: "+mNome);
        endereco.setText("Endereço: "+mRua+", "+mNumero+", "+mBairro);
        avaliacao.setText("Avaliação: "+mAvaliacao);
        nomeAnimal.setText("Nome: "+mNomeAnimal);
        raca.setText("Raça: "+mRaca);
        prioridade.setText("PRIORIDADE: "+mPrioridade);
        data.setText(mData);

    }
    //Ajeitar para setar o OS na Sessao Agendamento.
    private void getAllTexts(){
        mNome = SessaoAgendamento.instance.getMedico().getDadosPessoais().getNome();
        mRua  = ordemServico.getMedico().getDadosPessoais().getEndereco().getLogradouro();
        mNumero = String.valueOf(ordemServico.getMedico().getDadosPessoais().getEndereco().getNumero());
        mBairro = ordemServico.getMedico().getDadosPessoais().getEndereco().getBairro();
        mNomeAnimal = ordemServico.getAnimal().getNome();
        mRaca = ordemServico.getAnimal().getRaca();
        mAvaliacao = String.valueOf(ordemServico.getMedico().getAvaliacao());
        mPrioridade = String.valueOf(ordemServico.getPrioridade());
    }

    private void initOS(){
        ordemServico = new OrdemServico();
        getAtualDate();
        ordemServico.setMedico(SessaoAgendamento.instance.getMedico());
        ordemServico.setAnimal(SessaoAgendamento.instance.getAnimal());
        ordemServico.setCliente(Sessao.instance.getCliente());
        ordemServico.setStatus(OrdemServico.Status.AGUARDANDO_ATENDIMENTO);
        ordemServico.setPrioridade(OrdemServico.Prioridade.BAIXA);
        ordemServico.setDescricao("Teste");
        initTriagem();
        ordemServico.setTriagem(triagem);
    }

    private void initTriagem(){
        triagem  = new Triagem();
        triagem.setSintomas(SessaoAgendamento.instance.getSintomas().toString());
        triagem.setOutros("Outros Sintomas Digitados pelo Cliente");
    }



    private void getAtualDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        ordemServico.setData(date);
        mData = dateFormat.format(date);
    }

    private class RegisterTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<Sintomas> sintomasList = (ArrayList<Sintomas>) SessaoAgendamento.instance.getSintomas();
            //RequestBayesNetwork(sintomasList);
            long idOs = cadastrarOsAndTriagem();
            //insertAllDiseasesOsId(diseases,idOs);
            startActivity(new Intent(ConfirmarOSActivity.this, StatusOsCliente.class));
            finish();
            SessaoAgendamento.instance.reset();
            resetTask();
            return null;
        }

        private void RequestBayesNetwork(ArrayList<Sintomas> sintomasList) {
            RequestServiceBayesNet bayesNet = new RequestServiceBayesNet(getBaseContext(),sintomasList);
            String c = bayesNet.toArrayJson(sintomasList);
            res = bayesNet.RequestService(c);
            diseases = bayesNet.JsontoString(res);
        }

        private void insertAllDiseasesOsId(ArrayList<DiseaseProb> diseaseProbs,long idOs){
            diseasesService.insertAllDiseases(diseaseProbs,idOs);
        }

        private long cadastrarOsAndTriagem(){
            long idOs = ordemServicoServices.cadastraOS(ordemServico,triagem);
            ordemServico.setId(idOs);
            List<Sintomas> list = SessaoAgendamento.instance.getSintomas();
            triagem = triagemDAO.getTriagembyId(idOs);
            triagemXsintomaDAO.cadastrar(triagem, list);
            inputSessao();

            return idOs;
        }

        private void inputSessao(){
            SessaoAgendamento.instance.setOs(ordemServico);
            SessaoAgendamento.instance.setTriagem(triagem);

        }


        private void resetTask() {
            registerTask =  null;
            isTaskRunning = false;
        }
    }

}
