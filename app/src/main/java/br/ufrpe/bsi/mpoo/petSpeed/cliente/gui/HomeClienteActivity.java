package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petSpeed.R;

public class HomeClienteActivity extends AppCompatActivity {

    private Button btMedicos;
    private Button btHistorico;
    private Button btClinicas;
    private Button btPerfil;
    private Button btConfig;
    private Button btPets;
    private TextView txVSair;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        btMedicos = (Button)findViewById(R.id.ActHomeClBtnMed);
         btHistorico = (Button)findViewById(R.id.ActHomeClBtnHist);
         btClinicas= (Button)findViewById(R.id.ActHomeClBtnCln);
         btPerfil= (Button)findViewById(R.id.ActHomeClBtnPerf);
         btConfig= (Button)findViewById(R.id.ActHomeClBtnCfg);
         btPets= (Button)findViewById(R.id.ActHomeClBtnPets);
         txVSair= (TextView)findViewById(R.id.ActHomeClTxVwLogOut);

         btMedicos.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

         btHistorico.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

         btClinicas.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

         btPerfil.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

         btConfig.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

         btPets.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

         txVSair.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });
    }
}
