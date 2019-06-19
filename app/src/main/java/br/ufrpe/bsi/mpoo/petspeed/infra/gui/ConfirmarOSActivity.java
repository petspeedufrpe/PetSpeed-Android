package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.HomeClienteActivity;
import br.ufrpe.bsi.mpoo.petspeed.cliente.gui.StatusOsCliente;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.AppException;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemXsintomaDAO;

public class ConfirmarOSActivity extends AppCompatActivity {
    private TextView nome,endereco,avaliacao,nomeAnimal,raca,prioridade,data,verSintomas;
    private Button confirmarAtendimento,abortar;
    private String mNome,mBairro,mRua,mNumero,mAvaliacao,mNomeAnimal,mRaca,mPrioridade,mData;
    private OrdemServico ordemServico;
    private Triagem triagem;
    private List<Sintomas> list;
    private TriagemDAO triagemDAO = new TriagemDAO();
    private OrdemServicoServices ordemServicoServices = new OrdemServicoServices();
    private TriagemXsintomaDAO triagemXsintomaDAO = new TriagemXsintomaDAO();
    private RegisterTask registerTask = null;
    private boolean isTaskRunning;
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

        confirmarAtendimento = findViewById(R.id.btn_confirmar_os);
        abortar = findViewById(R.id.btn_cancelar_os);

        confirmarAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTaskRunning){
                    Toast.makeText(getBaseContext(),"Processando...",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (registerTask == null){
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


    }


    private void findTexts(){
        nome = findViewById(R.id.fragPopUpMedNome);
        endereco = findViewById(R.id.fragPopUpMedEnd);
        avaliacao = findViewById(R.id.fragPopUpAval);
        nomeAnimal = findViewById(R.id.fragPopUpAnimNome);
        raca = findViewById(R.id.fragPopUpAnimRaca);
        prioridade = findViewById(R.id.fragPopUpPrioridade);
        //data = findViewById(R.id.fragPopUpDate);
    }

    private void setTextsNome(){
        nome.setText("Nome: "+mNome);
        endereco.setText("Endereço: "+mRua+", "+mNumero+", "+mBairro);
        avaliacao.setText("Avaliação: "+mAvaliacao);
        nomeAnimal.setText("Nome: "+mNomeAnimal);
        raca.setText("Raça: "+mRaca);
        prioridade.setText("PRIORIDADE: "+mPrioridade);

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
        //getAtualDate();
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

    private void cadastrarOsAndTriagem(){
        long idOs = ordemServicoServices.cadastraOS(ordemServico,triagem);
        ordemServico.setId(idOs);
        list = SessaoAgendamento.instance.getSintomas();
        triagem = triagemDAO.getTriagembyId(idOs);
        triagemXsintomaDAO.cadastrar(triagem,list);
        inputSessao();
    }

    private void inputSessao(){
        SessaoAgendamento.instance.setOs(ordemServico);
        SessaoAgendamento.instance.setTriagem(triagem);

    }

    /*private void getAtualDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        mData = format.format(calendar);
    }*/

    private class RegisterTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            cadastrarOsAndTriagem();
            startActivity(new Intent(ConfirmarOSActivity.this, StatusOsCliente.class));
            finish();
            SessaoAgendamento.instance.reset();
            resetTask();
            return null;
        }

        private void resetTask() {
            registerTask =  null;
            isTaskRunning = false;
        }
    }

}
