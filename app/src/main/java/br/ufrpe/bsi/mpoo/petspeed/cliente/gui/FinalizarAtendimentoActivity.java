package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.negocio.SessaoAgendamento;
import br.ufrpe.bsi.mpoo.petspeed.medico.negocio.MedicoServices;
import br.ufrpe.bsi.mpoo.petspeed.os.dominio.OrdemServico;

public class FinalizarAtendimentoActivity extends AppCompatActivity {

    private MedicoServices medicoServices = new MedicoServices();
    private RatingBar ratingBar;
    private TextView textViewAvaliacao;
    private Button btnPronto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_atendimento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Finalizar Atendimento");
        findViews();
        addListenerOnRatingBar();
        addListenerOnButton();
    }

    public void findViews(){
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        textViewAvaliacao = (TextView) findViewById(R.id.txt_avaliacao);
        btnPronto = (Button) findViewById(R.id.button_pronto);
    }


    public void addListenerOnRatingBar(){
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textViewAvaliacao.setText(String.valueOf(rating));
            }
        });
    }

    public void addListenerOnButton(){
        btnPronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarAvaliacao();
                startActivity(new Intent(FinalizarAtendimentoActivity.this,HomeClienteActivity.class));
                finish();
            }
        });
    }

    private void alterarAvaliacao() {
        OrdemServico os = SessaoAgendamento.instance.getOs();
        os.getMedico().setAvaliacao(Double.parseDouble(textViewAvaliacao.getText().toString()));
        medicoServices.alteraAvaliacao(os.getMedico());//avaliação só ta colocando o ultimo valor que o medico foi avaliado
    }


}
