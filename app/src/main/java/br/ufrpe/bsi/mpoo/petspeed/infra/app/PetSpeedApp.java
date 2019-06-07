package br.ufrpe.bsi.mpoo.petspeed.infra.app;

import android.app.Application;
import android.content.Context;

public class PetSpeedApp extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
