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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.Triagem;
import br.ufrpe.bsi.mpoo.petspeed.os.negocio.OrdemServicoServices;
import br.ufrpe.bsi.mpoo.petspeed.os.persistencia.TriagemDAO;

public class StatusOsCliente extends AppCompatActivity {
    private CardView cardView;
    private RelativeLayout relativeLayout;
    private TextView nome;
    private TextView endereco;
    private TextView avaliacao;
    private TextView nomeAnimal;
    private TextView raca;
    private TextView prioridade;
    private TextView data;
    private TextView statusDescricao;
    private String mNome;
    private String mBairro;
    private String mRua;
    private String mNumero;
    private String mAvaliacao;
    private String mNomeAnimal;
    private String mRaca;
    private String mPrioridade;
    private String mData;
    private String mStatusDescricao;
    private OrdemServico ordemServico;
    private TriagemDAO triagemDAO = new TriagemDAO();
    private OrdemServicoServices ordemServicoServices = new OrdemServicoServices();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_os_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Status de Atendimento");
        Button finalizarAtendimento = findViewById(R.id.btnFinalizarAtendimento);
        findTexts();
        if (initOS()){
            getAtualDate();
            initTriagem();
            getAllTexts();
            setTextsNome();
        } else{
            TextView view = findViewById(R.id.viewOsVazio);
            view.setVisibility(View.VISIBLE);
            setAllInvisible();

        }
        finalizarAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatusDescricao.equals(OrdemServico.Status.AGUARDANDO_ATENDIMENTO.getDescricao().replace("_"," "))){
                    Toast.makeText(StatusOsCliente.this,"Favor aguardar a confirmação do Médico",Toast.LENGTH_SHORT).show();
                }else{
                    ordemServico.setStatus(OrdemServico.Status.FINALIZADA);
                    SessaoAgendamento.instance.setOs(ordemServico);
                    ordemServicoServices.alteraStatusOs(ordemServico);
                    startActivity(new Intent(StatusOsCliente.this,FinalizarAtendimentoActivity.class));
                    finish();
                }
            }
        });

        TextView verSintomas = findViewById(R.id.fragPopSintomas);
        verSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sessao.instance.setOs(ordemServico);
                startActivity(new Intent(StatusOsCliente.this,ViewSintomasAnimalAcitivity.class));
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
        data = findViewById(R.id.fragPopUpDate);
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
        data.setText(mData);
        statusDescricao.setText(mStatusDescricao);
        statusDescricao.setTextColor(Color.BLACK);
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
            if (ordemServico.getStatus() == OrdemServico.Status.AGUARDANDO_ATENDIMENTO ||
                    ordemServico.getStatus() == OrdemServico.Status.EM_ATENDIMENTO) {

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
        Triagem triagem = triagemDAO.getTriagembyId(ordemServico.getId());
        ordemServico.setTriagem(triagem);
    }


    private void getAtualDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = ordemServico.getData();
        mData = dateFormat.format(date);
    }

}
