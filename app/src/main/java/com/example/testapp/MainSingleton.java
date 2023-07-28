package com.example.testapp;

import android.annotation.SuppressLint;

public class MainSingleton {

    @SuppressLint("StaticFieldLeak")
    private static final MainActivity instance = new MainActivity();

    private MainSingleton() {}

    public static MainActivity getInstance() {
        return instance;
    }

}
