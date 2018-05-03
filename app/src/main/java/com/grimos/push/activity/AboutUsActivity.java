package com.grimos.push.activity;

import android.view.View;

import com.grimos.push.R;
import com.grimos.push.base.BaseActivity;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_about_us)
public class AboutUsActivity extends BaseActivity {


    @Override
    protected void init() {
        setTitleBar("关于软件");
        msg_img.setVisibility(View.GONE);
        order_img.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {

    }
}
