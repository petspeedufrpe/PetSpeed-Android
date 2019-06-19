package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sintomas;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemXsintomaDAO;

public class StatusOsCliente extends AppCompatActivity {
    private TextView nome,endereco,avaliacao,nomeAnimal,raca,prioridade,data,statusDescricao,verSintomas;
    private Button finalizarAtendimento;
    private String mNome,mBairro,mRua,mNumero,mAvaliacao,mNomeAnimal,mRaca,mPrioridade,mData,mStatusDescricao;
    private OrdemServico ordemServico;
    private Triagem triagem;
    private List<Sintomas> list;
    private TriagemDAO triagemDAO = new TriagemDAO();
    private OrdemServicoServices ordemServicoServices = new OrdemServicoServices();
    private TriagemXsintomaDAO triagemXsintomaDAO = new TriagemXsintomaDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_os_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initOS();
        cadastrarOsAndTriagem();
        findTexts();
        getAllTexts();
        setTextsNome();
        finalizarAtendimento = findViewById(R.id.btnFinalizarAtendimento);
        finalizarAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatusDescricao.equals(OrdemServico.Status.AGUARDANDO_ATENDIMENTO.getDescricao())){
                    Toast.makeText(StatusOsCliente.this,"Favor aguardar a confirmação do Médico",Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(StatusOsCliente.this,null));
                }
            }
        });

        verSintomas = findViewById(R.id.fragPopSintomas);
        verSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // a fazer
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
        statusDescricao = findViewById(R.id.StatusDescricao);
        //data = findViewById(R.id.fragPopUpDate);
    }

    private void setTextsNome(){
        nome.setText("Nome: "+mNome);
        endereco.setText("Endereço: "+mRua+", "+mNumero+", "+mBairro);
        avaliacao.setText(mAvaliacao);
        nomeAnimal.setText(mNomeAnimal);
        raca.setText(mRaca);
        prioridade.setText(mPrioridade);
        statusDescricao.setText(mStatusDescricao);
        if (mStatusDescricao.equals(OrdemServico.Status.AGUARDANDO_ATENDIMENTO.getDescricao())){
            statusDescricao.setTextColor(Color.BLUE);
        } else if(mStatusDescricao.equals(OrdemServico.Status.EM_ATENDIMENTO.getDescricao())){
            statusDescricao.setTextColor(Color.YELLOW);
        } else if(mStatusDescricao.equals(OrdemServico.Status.FINALIZADA.getDescricao())){
            statusDescricao.setTextColor(Color.GREEN);
        }
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
        mStatusDescricao = SessaoAgendamento.instance.getStatus().getDescricao();
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
        triagem.setOutros("Outros SIntomas Digitados pelo Cliente");
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

}
