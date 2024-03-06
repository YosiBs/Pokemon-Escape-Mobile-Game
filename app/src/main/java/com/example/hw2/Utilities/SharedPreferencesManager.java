package com.example.hw2.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesManager {

    private static final String DB_FILE = "DB_FILE";
    private SharedPreferences sharedPref;
    private static volatile SharedPreferencesManager instance = null;

    private SharedPreferencesManager(Context context) {
        Log.d("bbb", "18");

        this.sharedPref = context.getApplicationContext().getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        Log.d("bbb", "19");
    }

    public static void init(Context context) {
        Log.d("bbb", "27");

        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
    }

    public static SharedPreferencesManager getInstance() {
        Log.d("bbb", "20");


        return instance;
    }
    public void putInt( String key, int value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt( String key, int defaultValue){
        return sharedPref.getInt(key, defaultValue);
    }

    public void putString( String key, String value){
        Log.d("bbb", "22");

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getString( String key, String defaultValue){
        Log.d("bbb", "21");

        return sharedPref.getString(key, defaultValue);
    }




}
