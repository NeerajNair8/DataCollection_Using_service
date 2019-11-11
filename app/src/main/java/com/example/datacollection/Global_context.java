package com.example.datacollection;

import android.app.Application;
import android.content.Context;

public class Global_context extends Application {
    private static Context appcontext ;

    @Override
    public void onCreate() {
        super.onCreate();
        appcontext=getApplicationContext();
    }
    public static Context getAppcontext()
    {
        return appcontext;
    }

}
