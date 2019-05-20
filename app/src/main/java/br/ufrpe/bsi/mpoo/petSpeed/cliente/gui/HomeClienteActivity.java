package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.ApiRequestService;
import br.ufrpe.bsi.mpoo.petSpeed.infra.negocio.Sessao;

public class HomeClienteActivity extends AppCompatActivity {

    private Button btMedicos;
    private Button btHistorico;
    private Button btClinicas;
    private Button btPerfil;
    private Button btConfig;
    private Button btPets;
    private TextView txVSair;
    private TextView txVlatlong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        btMedicos = (Button) findViewById(R.id.ActHomeClBtnMed);
        btHistorico = (Button) findViewById(R.id.ActHomeClBtnHist);
        btClinicas = (Button) findViewById(R.id.ActHomeClBtnCln);
        btPerfil = (Button) findViewById(R.id.ActHomeClBtnPerf);
        btConfig = (Button) findViewById(R.id.ActHomeClBtnCfg);
        btPets = (Button) findViewById(R.id.ActHomeClBtnPets);
        txVSair = (TextView) findViewById(R.id.ActHomeClTxVwLogOut);
        txVlatlong = (TextView) findViewById(R.id.homeCltextView);


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
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=rua+leparc,+100&key=AIzaSyBhntuU8NDLx8ZoIIfxnNRXaziGPvtEB6s";
                ApiRequestService geocodeRequest = new ApiRequestService();
                geocodeRequest.geocodeReq(url);
                Double lat = (Double) Sessao.instance.getValue(ApiRequestService.geoCodeCoord.LAT.getStr());
                Double lng = (Double) Sessao.instance.getValue(ApiRequestService.geoCodeCoord.LNG.getStr());
                StringBuilder coordString = new StringBuilder();
                coordString.append(String.valueOf(lat)+" "+String.valueOf(lng));
                txVlatlong.append(coordString);

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
