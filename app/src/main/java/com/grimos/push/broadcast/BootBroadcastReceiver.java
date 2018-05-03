package com.grimos.push.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.grimos.push.activity.MainActivity;

/**
 * Created by donghui on 2017/6/9.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Intent launchActivityIntent = new Intent(context, MainActivity.class);  // 要启动的Activity
            launchActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchActivityIntent);
            Log.i("自启动","成功");
//            Intent serviceIntent=new Intent(context, ListenerService.class);
//            context.startActivity(serviceIntent);
        }
    }
}