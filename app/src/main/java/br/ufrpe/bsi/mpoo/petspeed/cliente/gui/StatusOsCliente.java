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
    private List<String> list;
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
        avaliacao.setText("Avaliação: "+mAvaliacao);
        nomeAnimal.setText("Nome: "+mNomeAnimal);
        raca.setText("Raça: "+mRaca);
        prioridade.setText("PRIORIDADE: "+mPrioridade);
        statusDescricao.setText(mStatusDescricao);
        if (mStatusDescricao.equals(OrdemServico.Status.AGUARDANDO_ATENDIMENTO.getDescricao())){
            statusDescricao.setTextColor(Color.BLUE);
        } else if(mStatusDescricao.equals(OrdemServico.Status.EM_ATENDIMENTO.getDescricao())){
            statusDescricao.setTextColor(Color.MAGENTA);
        } else if(mStatusDescricao.equals(OrdemServico.Status.FINALIZADA.getDescricao())){
            statusDescricao.setTextColor(Color.GREEN);
        }
    }
    //Ajeitar para setar o OS na Sessao Agendamento.
    private void getAllTexts(){
        mNome = ordemServico.getMedico().getDadosPessoais().getNome();
        mRua  = ordemServico.getMedico().getDadosPessoais().getEndereco().getLogradouro();
        mNumero = String.valueOf(ordemServico.getMedico().getDadosPessoais().getEndereco().getNumero());
        mBairro = ordemServico.getMedico().getDadosPessoais().getEndereco().getBairro();
        mNomeAnimal = ordemServico.getAnimal().getNome();
        mRaca = ordemServico.getAnimal().getRaca();
        mAvaliacao = String.valueOf(ordemServico.getMedico().getAvaliacao());
        mPrioridade = String.valueOf(ordemServico.getPrioridade());
        mStatusDescricao =ordemServico.getStatus().getDescricao();
    }

    private void initOS(){
        ordemServico = ordemServicoServices.getOsByIdCliente(Sessao.instance.getCliente().getId());
        //getAtualDate();
        initTriagem();
        ordemServico.setTriagem(triagem);
    }

    private void initTriagem(){
        triagem  = triagemDAO.getTriagembyId(ordemServico.getId());
        list = triagemXsintomaDAO.getAllSintomasByIdTriagem(triagem.getId());
    }


    /*private void getAtualDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        mData = format.format(calendar);
    }*/

}
