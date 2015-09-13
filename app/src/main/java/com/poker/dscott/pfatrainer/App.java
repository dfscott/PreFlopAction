package com.poker.dscott.pfatrainer;

import android.app.Application;
import android.content.Context;

/**
 * Created by dscott on 9/13/2015.
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
