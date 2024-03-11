package com.example.hw2.Utilities;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(getApplicationContext());
    }
}
