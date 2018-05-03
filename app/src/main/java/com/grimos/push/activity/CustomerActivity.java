package com.grimos.push.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grimos.push.R;
import com.grimos.push.base.BaseActivity;
import com.grimos.push.util.SystemUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_customer)
public class CustomerActivity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.backImg)
    ImageView backImg;

    @ViewInject(R.id.tv_qq_num)
    TextView tv_qq_num;
    @ViewInject(R.id.tv_contact_qq)
    TextView tv_contact_qq;
    @ViewInject(R.id.tv_weixin_num)
    TextView tv_weixin_num;
    @ViewInject(R.id.tv_contact_weixin)
    TextView tv_contact_weixin;
    @ViewInject(R.id.tv_email_num)
    TextView tv_email_num;
    @ViewInject(R.id.tv_contact_email)
    TextView tv_contact_email;

    @Override
    protected void init() {
        backImg.setOnClickListener(this);
        tv_contact_qq.setOnClickListener(this);
        tv_contact_weixin.setOnClickListener(this);
        tv_contact_email.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backImg:
                finish();
                break;
            case R.id.tv_contact_qq:
                SystemUtil.joinqq(mActivity,tv_qq_num.getText().toString().trim());
                break;
            case R.id.tv_contact_weixin:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText(null, tv_weixin_num.getText().toString().trim()));
                SystemUtil.toWechat(mActivity);
                break;
            case R.id.tv_contact_email:
                SystemUtil.sendEmail(mActivity,new String[]{tv_email_num.getText().toString().trim()},"意见反馈","您想说的话");
                break;
        }
    }
}
