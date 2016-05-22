package com.geniusmart.binder;

import android.app.Application;

public class ServerApplication extends Application {

    private static ServerApplication sInstance;

    public static ServerApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
