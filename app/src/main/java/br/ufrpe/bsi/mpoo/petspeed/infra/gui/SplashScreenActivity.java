package br.ufrpe.bsi.mpoo.petspeed.infra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.cliente.negocio.ClienteServices;
import br.ufrpe.bsi.mpoo.petspeed.infra.persistencia.PreencherBanco;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(new ClienteServices().getClienteCompleto(1)==null){
            new PreencherBanco().start();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();

            }
        },2000);
    }
}
