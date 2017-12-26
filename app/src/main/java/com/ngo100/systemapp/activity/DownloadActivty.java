package com.ngo100.systemapp.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngo100.systemapp.R;
import com.ngo100.systemapp.base.BaseActivity;
import com.ngo100.systemapp.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

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

    int vip_level,payType;
    String order_url,msg_url;
    @Override
    protected void init() {
        wechat_radio.setOnClickListener(this);
        alipay_radio.setOnClickListener(this);
        pay_btn.setOnClickListener(this);
        order_url=getIntent().getStringExtra("order_url");
        msg_url=getIntent().getStringExtra("msg_url");
        vip_level=getIntent().getIntExtra("vip",0);
        setTitleBar("VIP安装");

        if (vip_level==0){
            vip_bg_img.setImageLevel(0);

        }else {
            vip_bg_img.setImageLevel(1);
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
            case R.id.wechat_radio:
                wechat_radio.setImageLevel(1);
                alipay_radio.setImageLevel(0);
                payType=1;
                break;
            case R.id.alipay_radio:
                wechat_radio.setImageLevel(0);
                alipay_radio.setImageLevel(1);
                payType=2;
                break;
            case R.id.pay_btn:
                if (payType==1){
                    ToastUtil.show("微信支付");
                }else if (payType==2){
                    ToastUtil.show("支付宝");
                }else {
                    ToastUtil.show("您还未选择支付方式");
                }
                break;
        }
    }
}
