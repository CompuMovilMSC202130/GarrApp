package com.example.garrapp;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.garrapp.utilidades.Constants;

public class PreferManag {

    private SharedPreferences sharedPreferences = null;

    public PreferManag(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.KEY_NAME, Context.MODE_PRIVATE);

    }

    public  void putBoolean (String key, Boolean value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }


    public Boolean getBoolean (String key ){
        return sharedPreferences.getBoolean(key,false);
    }

    public void putString (String key ,String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString (String key){
        return sharedPreferences.getString(key, null);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
