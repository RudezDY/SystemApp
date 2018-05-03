package com.grimos.push.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.grimos.push.activity.MainActivity;
import com.grimos.push.http.CheckNetwork;

/**
 * Created by homework on 2017/12/30.
 */

public class ListenerService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("service启动","yes");
        new Thread(){
            @Override
            public void run() {
                while (true){
                    if (CheckNetwork.isNetworkConnected(ListenerService.this)){
                        Log.i("联网","成功");
                        startActivity(new Intent(ListenerService.this, MainActivity.class));
                    break;
                    }
                    else {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }
}
