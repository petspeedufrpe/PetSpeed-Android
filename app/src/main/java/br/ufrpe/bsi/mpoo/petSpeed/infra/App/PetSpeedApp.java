package br.ufrpe.bsi.mpoo.petSpeed.infra.App;

import android.app.Application;
import android.content.Context;

public class PetSpeedApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext(){
        return mContext;
    }
}
