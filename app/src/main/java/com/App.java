package com;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.stateManger.AppSateChangeManger;

import java.util.List;



/**
 * APP初始化
 */

public class App extends Application {

    public static Context context;

    public  static AppSateChangeManger appSateChangeManger = AppSateChangeManger.getInstance();
    @Override
    public void onCreate() {
        super.onCreate();
        App.context = this;
        //初始化APP設定監聽者
        appSateChangeManger.init();
    }



}