package com.grimos.push.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import com.grimos.push.activity.AutoWedActivity;
import com.grimos.push.http.HttpCallBack;
import com.grimos.push.http.HttpUtil;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by homework on 2018/3/12.
 */

public class SystemUtil {

    /**
     * 吱口令支付
     * @param aty
     */
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

    public static void toAlipay(Activity aty,String urlCode){
        String intentFullUrl="intent://platformapi/startapp?saId=10000007&" +
                "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F"+urlCode+"%3F_s" +
                "%3Dweb-other&_t=1472443966571#Intent;" +
                "scheme=alipayqr;package=com.eg.android.AlipayGphone;end";
        Intent intent = null;
        try {
            intent = Intent.parseUri(intentFullUrl, Intent.URI_INTENT_SCHEME );
            aty.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
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

    /**
     * 跳转微信
     * @param context
     */
    public static void toWechat(Context context){
        Intent intent = new Intent();
        ComponentName cmp=new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);

    }

    /**
     * 跳转到与num聊天
     * @param context
     * @param num
     */
    public static void joinqq(Context context,String num){
        try {
            //num是你自己要跳转的QQ号码 　　 　
            String urlQQ = "mqqwpa://im/chat?chat_type=wpa&uin="+num+"&version=1";
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
        }catch (Exception e){
            //如果没有装ＱＱ，检测异常，不然会出问题滴！
            Toast.makeText(context, "未安装手机QQ或安装的版本不支持！",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 跳转到加入QQ群
     * @param context
     * @param QQkey
     * @return
     */
    public static boolean joinQQGroup(Context context,String QQkey) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + QQkey));
//        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "_JsrtC--SQW29GZSI63VxqcxXIkGMgiJ"));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(context, "未安装手机QQ或安装的版本不支持！",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 发送邮件
     * @param context
     * @param nums
     * @param subject
     * @param content
     */
    public static void sendEmail(Context context,String[] nums,String subject,String content){
        Intent i = new Intent(Intent.ACTION_SEND);
        // i.setType("text/plain"); //模拟器请使用这行
        i.setType("message/rfc822"); // 真机上使用这行
        i.putExtra(Intent.EXTRA_EMAIL,
                nums);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(i,
                "Select email application."));
    }


    /**
     * 跳转其他应用
     * @param context
     * @param packagename
     */
    public static void doStartApplicationWithPackageName(Context context,String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            ToastUtil.show("未找到对应包名:"+packagename);
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    public static void jumpMsg(final Context context, final String url){
        HttpUtil.readMsg(new HttpCallBack() {
            @Override
            public void onSuccess(String result) {
//                String code=JSONParser.getStringFromJsonString("status",result);
//                String msg=JSONParser.getStringFromJsonString("msg",result);
//                if ("1".equals(code))
                    AutoWedActivity.startAty(context,url);
//                else
//                    ToastUtil.show(msg);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

}
