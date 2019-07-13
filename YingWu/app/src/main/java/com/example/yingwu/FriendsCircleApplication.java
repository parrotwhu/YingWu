package com.example.yingwu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.yingwu.others.DataCenter;

@SuppressLint("Registered")
public class FriendsCircleApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        DataCenter.init();
    }
}

