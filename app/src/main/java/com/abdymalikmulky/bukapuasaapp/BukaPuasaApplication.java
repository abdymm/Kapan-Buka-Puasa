package com.abdymalikmulky.bukapuasaapp;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/4/17.
 */

public class BukaPuasaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(this);
        //FlowLog.setMinimumLoggingLevel(FlowLog.Level.);

        Timber.plant(new Timber.DebugTree());

    }
}
