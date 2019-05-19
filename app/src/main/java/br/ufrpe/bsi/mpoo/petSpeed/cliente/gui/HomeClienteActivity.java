package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.ufrpe.bsi.mpoo.petSpeed.R;

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

                apiGetRequest();

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

    String url = "https://maps.googleapis.com/maps/api/geocode/json?address=rua+leparc,+100&key=AIzaSyBhntuU8NDLx8ZoIIfxnNRXaziGPvtEB6s";


    private void apiGetRequest(String urlRequest) {
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        RequestQueue mQueue = new RequestQueue(cache, network);

        mQueue.start();

        String url = urlRequest;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Map<String, Long> latlong = new HashMap<>();
                    JSONArray jsonResults = response.getJSONArray("results");
                    JSONObject jsonData = jsonResults.getJSONObject(0);
                    JSONObject jsonGeometry = jsonData.getJSONObject("geometry");
                    JSONObject jsonLocation = jsonGeometry.getJSONObject("location");

                    double latitude = jsonLocation.getDouble("lat");
                    double longitude = jsonLocation.getDouble("lng");


                    txVlatlong.setText("");
                    txVlatlong.append("LatLong:  Latitude: " + String.valueOf(latitude) + "  Longitude:  " + String.valueOf(longitude));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}
