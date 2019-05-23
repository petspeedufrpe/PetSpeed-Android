package br.ufrpe.bsi.mpoo.petSpeed.cliente.gui;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import br.ufrpe.bsi.mpoo.petSpeed.R;
import br.ufrpe.bsi.mpoo.petSpeed.infra.gui.MapsFragment;


public class HomeClienteActivity extends AppCompatActivity{


    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_, new MapsFragment(),"FragmentMaps");
        fragmentTransaction.commitAllowingStateLoss();

    }

}