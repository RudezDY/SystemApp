package com.ngo100.systemapp.app;

import android.app.Application;


import org.xutils.x;

/**
 * Created by donghui on 2017/6/16.
 */

public class App extends Application {
    public String paySuccessUrl="";

    static App app;
    public static App getInstance(){
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }


}
