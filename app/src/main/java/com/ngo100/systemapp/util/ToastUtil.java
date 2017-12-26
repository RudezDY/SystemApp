package com.ngo100.systemapp.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.ngo100.systemapp.app.App;


/**
 * Created by donghui on 2017/6/1.
 */

public class ToastUtil {
    public static Toast toast;



    public static void show(Context context, String showStr){
        if (toast ==null){
            toast = Toast.makeText(context,showStr, Toast.LENGTH_SHORT);
        }
        toast.setText(showStr);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,ScreenUtils.getScreenHeight(context)/3);
        toast.show();
    }

    public static void show(String showStr){
        if (toast ==null){
            toast = Toast.makeText(App.getInstance().getApplicationContext(),showStr, Toast.LENGTH_SHORT);
        }
        toast.setText(showStr);
        toast.show();
    }

    /**
     * 自定义显示时间
     *
     * @param context
     * @param showStr
     * @param duration
     */
    public static void show(Context context, CharSequence showStr, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, showStr, duration);
        } else {
            toast.setText(showStr);
        }
        toast.show();

    }

    /**
     * 隐藏toast
     */
    public static void hideToast() {
        if (toast != null) {
            toast.cancel();
        }
    }


}
