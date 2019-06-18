package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.Sessao;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class StatusOsCliente extends AppCompatActivity {
    private TextView nome,endereco,avaliacao,nomeAnimal,raca,prioridade,data,statusDescricao;
    private Button finalizarAtendimento,verSintomas;
    private String mNome,mBairro,mRua,mNumero,mAvaliacao,mNomeAnimal,mRaca,mPrioridade,mData,mStatusDescricao;
    private OrdemServico ordemServico;
    private Context mContext = getBaseContext();

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
                    Toast.makeText(mContext,"Favor aguardar a confirmação do Médico",Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(StatusOsCliente.this,null));
                }
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

    private void getAllTexts(){
        mNome = SessaoAgendamento.instance.getMedico().getDadosPessoais().getNome();
        mRua  = ordemServico.getMedico().getDadosPessoais().getEndereco().getLogradouro();
        mNumero = String.valueOf(ordemServico.getMedico().getDadosPessoais().getEndereco().getNumero());
        mBairro = ordemServico.getMedico().getDadosPessoais().getEndereco().getBairro();
        mNomeAnimal = ordemServico.getAnimal().getNome();
        mRaca = ordemServico.getAnimal().getRaca();
        mAvaliacao = String.valueOf(ordemServico.getMedico().getAvaliacao());
        mPrioridade = String.valueOf(ordemServico.getPrioridade());
        mStatusDescricao = ordemServico.getStatus().getDescricao();
    }

    private void initOS(){
        ordemServico = new OrdemServico();
        //getAtualDate();
        ordemServico.setMedico(SessaoAgendamento.instance.getMedico());
        ordemServico.setAnimal(SessaoAgendamento.instance.getAnimal());
        ordemServico.setCliente(Sessao.instance.getCliente());
        ordemServico.setStatus(OrdemServico.Status.AGUARDANDO_ATENDIMENTO);
        ordemServico.setPrioridade(OrdemServico.Prioridade.BAIXA);
    }

    /*private void getAtualDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        mData = format.format(calendar);
    }*/

}
