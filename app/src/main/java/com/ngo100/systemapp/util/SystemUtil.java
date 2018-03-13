package com.ngo100.systemapp.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by homework on 2018/3/12.
 */

public class SystemUtil {
    public static void jumpAlipay(Activity aty){
        try{
            Intent in=new Intent();
            //in.addCategory(Intent.CATEGORY_LAUNCHER)
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            ComponentName cn =new ComponentName("com.eg.android.AlipayGphone","com.eg.android.AlipayGphone.AlipayLogin");
            in.setComponent(cn);
            aty.startActivity(in);
        }catch (Exception e){
            ToastUtil.show("找不到应用程序");
        }
    }


    //跳转到微信扫一扫
    public static void toWeChatScanDirect(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            intent.setAction("android.intent.action.VIEW");
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }

    /**
     * 打开微信并跳入到二维码扫描页面
     *
     * @param context
     */
    public static void openWeixinToQE_Code(Context context) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            context.startActivity(intent);

        } catch (Exception e) {

        }
    }

    public static void toWechat(Context context){
        Intent intent = new Intent();
        ComponentName cmp=new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);

    }

    public static void joinqq(Context context,String num){
        try {
            //123是你自己要跳转的QQ号码 　　 　
            String urlQQ = "mqqwpa://im/chat?chat_type=wpa&uin="+num+"&version=1";
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
        }catch (Exception e){
            //如果没有装ＱＱ，检测异常，不然会出问题滴！ 　　　  　
            Toast.makeText(context, "未安装手机QQ或安装的版本不支持！",Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean joinQQGroup(Context context,String QQkey) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + QQkey));
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "_JsrtC--SQW29GZSI63VxqcxXIkGMgiJ"));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(context, "未安装手机QQ或安装的版本不支持！",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
