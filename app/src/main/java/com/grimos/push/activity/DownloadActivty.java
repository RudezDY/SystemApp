package com.grimos.push.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.grimos.push.R;
import com.grimos.push.base.BaseActivity;
import com.grimos.push.bean.HomeObj;
import com.grimos.push.http.HttpCallBack;
import com.grimos.push.http.HttpUtil;
import com.grimos.push.user.User;
import com.grimos.push.util.JSONParser;
import com.grimos.push.util.LoadingDialogUtils;
import com.grimos.push.util.SystemUtil;
import com.grimos.push.util.ToastUtil;
import com.grimos.push.widget.WeChatSkipDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_download_activty)
public class DownloadActivty extends BaseActivity {


    @ViewInject(R.id.name_tv)
    private TextView name_tv;
    @ViewInject(R.id.vip_bg_img)
    private ImageView vip_bg_img;
    @ViewInject(R.id.wechat_radio)
    private ImageView wechat_radio;
    @ViewInject(R.id.alipay_radio)
    private ImageView alipay_radio;
    @ViewInject(R.id.pay_btn)
    private Button pay_btn;
    @ViewInject(R.id.money_et)
    private EditText money_et;
    @ViewInject(R.id.month_ctv)
    private CheckedTextView month_ctv;
    @ViewInject(R.id.quarter_ctv)
    private CheckedTextView quarter_ctv;
    @ViewInject(R.id.year_ctv)
    private CheckedTextView year_ctv;

    @ViewInject(R.id.layout_not_vip)
    private LinearLayout layout_not_vip;
    @ViewInject(R.id.layout_vip)
    private LinearLayout layout_vip;
    @ViewInject(R.id.tv_vip_time)
    private TextView tv_vip_time;
    @ViewInject(R.id.tv_recharge_record)
    private TextView tv_recharge_record;



    int vip_level,payType,id;
    String order_url,msg_url,download_url;
    float month_money,quarter_money,year_money;
    String money,name;
    @Override
    protected void init() {
        wechat_radio.setOnClickListener(this);
        alipay_radio.setOnClickListener(this);
        month_ctv.setOnClickListener(this);
        quarter_ctv.setOnClickListener(this);
        year_ctv.setOnClickListener(this);
        pay_btn.setOnClickListener(this);
        tv_recharge_record.setOnClickListener(this);
        id=getIntent().getIntExtra("id",0);
        name=getIntent().getStringExtra("name");
        order_url=getIntent().getStringExtra("order_url");
        msg_url=getIntent().getStringExtra("msg_url");
        download_url=getIntent().getStringExtra("download_url");
        vip_level=getIntent().getIntExtra("vip",0);
        month_money=getIntent().getFloatExtra("month",0);
        quarter_money=getIntent().getFloatExtra("quarter",0);
        year_money=getIntent().getFloatExtra("year",0);
        month_ctv.setText("一个月"+month_money+"元");
        quarter_ctv.setText("一季度"+quarter_money+"元");
        year_ctv.setText("一年"+year_money+"元");
        name_tv.setText(name);
        setTitleBar("VIP安装");
        if (vip_level>0){
            pay_btn.setText("进入下载页");
        }

        money=month_money+"";

        if (vip_level==0){
//            vip_bg_img.setImageLevel(0);
            layout_not_vip.setVisibility(View.VISIBLE);
            layout_vip.setVisibility(View.GONE);

        }else {
//            vip_bg_img.setImageLevel(1);
            layout_not_vip.setVisibility(View.GONE);
            layout_vip.setVisibility(View.VISIBLE);
            HomeObj.HomeData data=new Gson().fromJson(sharedpreferencesUtil.getString("homeData"), HomeObj.HomeData.class);
            tv_vip_time.setText(data.vip_time);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.msg_img:
                AutoWedActivity.startAty(mActivity,msg_url);
                break;
            case R.id.order_img:
                AutoWedActivity.startAty(mActivity,order_url);
                break;
            case R.id.tv_recharge_record:
                AutoWedActivity.startAty(mActivity,order_url);
                break;
            case R.id.month_ctv:
                if (month_ctv.isChecked())
                    break;
                month_ctv.setChecked(true);
                quarter_ctv.setChecked(false);
                year_ctv.setChecked(false);
                money=month_money+"";
                break;
            case R.id.quarter_ctv:
                if (quarter_ctv.isChecked())
                    break;
                quarter_ctv.setChecked(true);
                month_ctv.setChecked(false);
                year_ctv.setChecked(false);
                money=quarter_money+"";
                break;
            case R.id.year_ctv:
                if (year_ctv.isChecked())
                    break;
                year_ctv.setChecked(true);
                month_ctv.setChecked(false);
                quarter_ctv.setChecked(false);
                money=year_money+"";
                break;
            case R.id.wechat_radio:
                wechat_radio.setImageLevel(1);
                alipay_radio.setImageLevel(0);
                payType=2;
                break;
            case R.id.alipay_radio:
                wechat_radio.setImageLevel(0);
                alipay_radio.setImageLevel(1);
                payType=1;
                break;
            case R.id.pay_btn:
                if (vip_level>0){
                    Intent intent=new Intent(mActivity,DownloadListActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    break;
                }
                if (payType==2){
                    ToastUtil.show("微信支付");
                }else if (payType==1){
                    ToastUtil.show("支付宝");
                }else {
                    ToastUtil.show("您还未选择支付方式");
                }
                commit();
                break;
        }
    }

    private void commit() {
        LoadingDialogUtils.createLoadingDialog(mActivity).show();
        HttpUtil.recharge(payType, money, new HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                String code= JSONParser.getStringFromJsonString("status",result);
                String msg= JSONParser.getStringFromJsonString("msg",result);
                if ("1".equals(code)){
                    String data= JSONParser.getStringFromJsonString("data",result);
                    if (payType==1){
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse(data);
//                        intent.setData(content_url);
//                        startActivity(intent);
                        showAliPayDialog(JSONParser.getStringFromJsonString("notice",result));
                    }
                    else if (payType==2){
                        String wechat_imgurl=JSONParser.getStringFromJsonString("wechat_img",result);
                        String skip=JSONParser.getStringFromJsonString("notice",result);
                        showDialog(skip,wechat_imgurl);
                    }
                }else {
                    ToastUtil.show(msg);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }


    /**
     * 支付宝捐赠dialog
     */
    private void showAliPayDialog(String skip) {
        new WeChatSkipDialog(mActivity,R.style.custom_dialog, User.getInstance().identifier,skip)
                .setOnAffirmListener(new WeChatSkipDialog.OnAffirmListener() {
                    @Override
                    public void onAffirm(String code) {
                        SystemUtil.toAlipay(mActivity,"FKX06561QGEKOFQCMZLO79");
                    }
                })
                .show();
//        new AlertDialog.Builder(mActivity)
//                .setTitle("温馨提示")
//                .setMessage("即将跳转到支付宝转账界面，请在转账备注时填写设备码，以保证服务顺利开通。如有疑问，请咨询客服!")
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                        // 将文本内容放到系统剪贴板里。
//                        cm.setPrimaryClip(ClipData.newPlainText(null,"#吱口令#长按复制此条消息，打开支付宝给我转账8iYTf1880e"));
//                        SystemUtil.jumpAlipay(mActivity);
//                    }
//                })
//                .create().show();
    }

    private void downloadWechatImg(String wechat_imgurl) {
        LoadingDialogUtils.createLoadingDialog(mActivity).show();
        String fileName = null;
        //系统相册目录
        String galleryPath= Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                +File.separator+"Camera"+File.separator;
        //保存到系统相册目录，避免微信读取本地图片的时候找不到该图片（放包名下就找不到）
        String path=galleryPath+"/wechat_code.jpg";
        DownLoadFile(wechat_imgurl, path, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                LoadingDialogUtils.closeLoadingDialog();
                //发送系统通知让相册管理程序及时更新
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(result);
                intent.setData(uri);
                sendBroadcast(intent);
                SystemUtil.openWeixinToQE_Code(mActivity);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LoadingDialogUtils.closeLoadingDialog();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LoadingDialogUtils.closeLoadingDialog();
            }

            @Override
            public void onFinished() {
                LoadingDialogUtils.closeLoadingDialog();
            }
        });
    }

    private void showDialog(String skip,final String wechat_imgurl) {
        new WeChatSkipDialog(mActivity,R.style.custom_dialog, User.getInstance().identifier,skip)
                .setOnAffirmListener(new WeChatSkipDialog.OnAffirmListener() {
                    @Override
                    public void onAffirm(String code) {
                        downloadWechatImg(wechat_imgurl);
                    }
                })
                .show();
    }



    /**
     * 下载文件
     */
    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }
}
