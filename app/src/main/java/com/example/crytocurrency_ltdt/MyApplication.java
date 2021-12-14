package com.example.crytocurrency_ltdt;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static Resources res;
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        mInstance = this;
        res = getResources();
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Resources getRes() {
        return res;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}