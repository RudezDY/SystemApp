package com.grimos.push.app;

import android.app.Application;
import android.os.Build;

import com.grimos.push.service.BackService;
import com.grimos.push.user.User;
import com.grimos.push.util.DeviceUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

/**
 * Created by donghui on 2017/6/16.
 */

public class App extends Application {

    public boolean isCreat;
    static App app;
    public static App getInstance(){
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        initUm();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        User.getInstance().identifier= DeviceUtil.getDeviceID(this);
        User.getInstance().model= Build.MODEL;
        User.getInstance().firm= Build.MANUFACTURER;
        BackService.startBackService(app);

    }

    /**
     * 初始化友盟
     */
    private void initUm() {
        UMConfigure.init(this, "5ae97132f29d9866f2000016", "UMENG_CHANNEL", UMConfigure.DEVICE_TYPE_PHONE, "9760d07e35e21aee5bb300b4e846fcb5");

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.i(deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
            }
        });
    }


}
