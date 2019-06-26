package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemDAO;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemXsintomaDAO;

public class StatusOsCliente extends AppCompatActivity {
    private CardView cardView;
    private RelativeLayout relativeLayout;
    private TextView nome,endereco,avaliacao,nomeAnimal,raca,prioridade,data,statusDescricao,verSintomas,view;
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
        finalizarAtendimento = findViewById(R.id.btnFinalizarAtendimento);
        findTexts();
        if (initOS()){
            initTriagem();
            getAllTexts();
            setTextsNome();
        } else{
            view = findViewById(R.id.viewOsVazio);
            view.setVisibility(View.VISIBLE);
            setAllInvisible();

        }
        finalizarAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatusDescricao.equals(OrdemServico.Status.AGUARDANDO_ATENDIMENTO.getDescricao().replace("_"," "))){
                    Toast.makeText(StatusOsCliente.this,"Favor aguardar a confirmação do Médico",Toast.LENGTH_SHORT).show();
                }else{
                    SessaoAgendamento.instance.setOs(ordemServico);
                    startActivity(new Intent(StatusOsCliente.this,FinalizarAtendimentoActivity.class));
                    finish();
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

    private void setAllInvisible() {
        cardView.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);
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
        cardView = findViewById(R.id.cardView);
        relativeLayout = findViewById(R.id.relativeLayoutClienteOs);
    }

    private void setTextsNome(){
        nome.setText("Nome: "+mNome);
        endereco.setText("Endereço: "+mRua+", "+mNumero+", "+mBairro);
        avaliacao.setText("Avaliação: "+mAvaliacao);
        nomeAnimal.setText("Nome: "+mNomeAnimal);
        raca.setText("Raça: "+mRaca);
        prioridade.setText("PRIORIDADE: "+mPrioridade);
        statusDescricao.setText(mStatusDescricao);
        if (mStatusDescricao.equals(OrdemServico.Status.AGUARDANDO_ATENDIMENTO.getDescricao().replace("_"," "))){
            statusDescricao.setTextColor(Color.BLUE);
        } else if(mStatusDescricao.equals(OrdemServico.Status.EM_ATENDIMENTO.getDescricao().replace("_"," "))){
            statusDescricao.setTextColor(Color.MAGENTA);
        } else if(mStatusDescricao.equals(OrdemServico.Status.FINALIZADA.getDescricao())){
            statusDescricao.setTextColor(Color.GREEN);
        }
    }
    private void getAllTexts(){
        mNome = ordemServico.getMedico().getDadosPessoais().getNome();
        mRua  = ordemServico.getMedico().getDadosPessoais().getEndereco().getLogradouro();
        mNumero = String.valueOf(ordemServico.getMedico().getDadosPessoais().getEndereco().getNumero());
        mBairro = ordemServico.getMedico().getDadosPessoais().getEndereco().getBairro();
        mNomeAnimal = ordemServico.getAnimal().getNome();
        mRaca = ordemServico.getAnimal().getRaca();
        mAvaliacao = String.valueOf(ordemServico.getMedico().getAvaliacao());
        mPrioridade = String.valueOf(ordemServico.getPrioridade());
        mStatusDescricao = ordemServico.getStatus().getDescricao().replace("_"," ");
    }

    private boolean initOS(){
        boolean result;
        try{
            ordemServico = ordemServicoServices.getOsByIdCliente(Sessao.instance.getCliente().getId());
            //getAtualDate();
            if (ordemServico.getStatus() == OrdemServico.Status.AGUARDANDO_ATENDIMENTO ||
                    ordemServico.getStatus() == OrdemServico.Status.EM_ATENDIMENTO) {
                ordemServico.setTriagem(triagem);
                result =  true;
            } else {
                result = false;
            }
        } catch (Exception e){
            return false;
        }

        return result;
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
