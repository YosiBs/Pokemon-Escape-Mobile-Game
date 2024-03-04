package com.example.hw2.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String DB_FILE = "DB_FILE";



    public static void putInt(Context context, String key, int value){
        SharedPreferences sharedPref = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public static int getInt(Context context, String key, int defaultValue){
        SharedPreferences sharedPref = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, defaultValue);
    }

    public static void putString(Context context, String key, String value){
        SharedPreferences sharedPref = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String defaultValue){
        SharedPreferences sharedPref = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaultValue);
    }





}
