package com.ngo100.systemapp.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;

import com.ngo100.systemapp.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by homework on 2017/12/26.
 */

public class DeviceUtil {

    public static String getDeiceID(Context context){
        TelephonyManager tm= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId().toString();
    }

    /**
     * 获取本机所有应用（包括系统应用）
     * @param context
     * @return
     */
    public static ArrayList<AppInfo> getallApps(Context context) {

        ArrayList<AppInfo> res = new ArrayList<AppInfo>();
        // 获取手机应用的集合
        List<PackageInfo> packs = context.getPackageManager()
                .getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            // 定义应用bean对象
            AppInfo newInfo = new AppInfo();
            if ((ApplicationInfo.FLAG_SYSTEM & packs.get(i).applicationInfo.flags) != 0) {//判断是否是系统应用
                newInfo.isSystemApp=true;
            }else {
                newInfo.isSystemApp=false;
            }
            // 应用名
            newInfo.appName=(p.applicationInfo.loadLabel(
                    context.getPackageManager()).toString());
            // 包名
            newInfo.packageName=p.packageName;
            // 获取清单文件的versionName版本名
            newInfo.versionName=p.versionName;
            // 获取清单文件的versionCode版本号
            newInfo.versionCode=p.versionCode;
            res.add(newInfo);
        }
        return res;
    }


}
