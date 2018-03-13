package com.ngo100.systemapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by homework on 2018/1/24.
 */

public class NetworkConnectReceiver extends BroadcastReceiver {
    static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    boolean isLaunch;
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (isLaunch)
//            return;
//        if (intent.getAction().equals(ACTION)) {
//            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
//            if (activeNetInfo != null&&activeNetInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
//
//                Intent launchActivityIntent = new Intent(context, MainActivity.class);  // 要启动的Activity
//                launchActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(launchActivityIntent);
//                Log.i("网络自启动", "成功");
//                isLaunch = true;
//
//            }
//        }
    }
}
