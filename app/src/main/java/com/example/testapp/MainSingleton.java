package com.example.testapp;

public class MainSingleton {

    private static MainActivity instance = new MainActivity();

    private MainSingleton() {}

    public static MainActivity getInstance() {
        return instance;
    }

}
