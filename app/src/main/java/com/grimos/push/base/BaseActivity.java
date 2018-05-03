package com.grimos.push.base;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grimos.push.R;
import com.grimos.push.app.App;
import com.grimos.push.user.User;
import com.grimos.push.util.ActivityTaskManeger;
import com.grimos.push.util.ScreenUtils;
import com.grimos.push.util.SharedpreferencesUtil;
import com.grimos.push.widget.BaseSwipeRefreshLayout;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by donghui on 2017/6/16.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected BaseActivity mActivity;
    protected App app;
    protected User user;
    Intent startAtyIntent;
    public int screenWidth, screenHeight;//屏幕宽高

    protected SharedpreferencesUtil sharedpreferencesUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        PushAgent.getInstance(this).onAppStart();//友盟推送统计需要
        EventBus.getDefault().register(this);
        ActivityTaskManeger.getInstance().addActivity(mActivity);
        app = App.getInstance();
        x.view().inject(this);
        user= User.getInstance();
        sharedpreferencesUtil=new SharedpreferencesUtil(mActivity,"systemApp");
        screenWidth = ScreenUtils.getScreenWidth(mActivity);
        screenHeight = ScreenUtils.getScreenHeight(mActivity);
        if (Build.VERSION.SDK_INT>=LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_titlebar));
        init();
    }

    protected ImageView menuImg;
    protected ImageView backImg;
    protected ImageView msg_img;
    protected ImageView order_img;
    protected ImageView qq_img;
    protected TextView title_tv;
    protected RelativeLayout title_layout;
    protected void setTitleBar(String title){
        if (null==title_layout)
            title_layout= (RelativeLayout) findViewById(R.id.title_layout);
        if (null==backImg)
            backImg= (ImageView) findViewById(R.id.backImg);
        if (null==menuImg)
            menuImg= (ImageView) findViewById(R.id.menuImg);
        if (null==msg_img)
            msg_img= (ImageView) findViewById(R.id.msg_img);
        if (null==order_img)
            order_img= (ImageView) findViewById(R.id.order_img);
        if (null==qq_img)
            qq_img= (ImageView) findViewById(R.id.qq_img);
        if (null==title_tv)
            title_tv= (TextView) findViewById(R.id.title_tv);

        title_layout.setVisibility(View.VISIBLE);
        title_tv.setText(title);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTaskManeger.getInstance().closeActivity(mActivity);
            }
        });
        msg_img.setOnClickListener(this);
        order_img.setOnClickListener(this);
    }

    protected void setMsgIcon(Drawable drawable){
        msg_img.setImageDrawable(drawable);
    }

    /**
     * Activity初始化方法
     */
    protected abstract void init();

    @Override
    protected void onDestroy() {
        ActivityTaskManeger.getInstance().removeActivity(mActivity);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected void startAty(Class aty){
        if (startAtyIntent ==null)
            startAtyIntent =new Intent();
        startAtyIntent.setClass(mActivity,aty);
        startActivity(startAtyIntent);
    }

    BaseSwipeRefreshLayout.OnRefresh refresh;//设置下拉刷新监听
    protected void setOnRefreshListener(BaseSwipeRefreshLayout.OnRefresh refresh){
        if (refresh!=null){
            this.refresh=refresh;
        }
    }
    boolean isRefresh;//是否在下拉刷新中

    /**
     * 初始化下拉刷新控件
     * @param swiperefreshlayout
     */
    protected void setSwipeRefreshLayout(final BaseSwipeRefreshLayout swiperefreshlayout) {
        swiperefreshlayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swiperefreshlayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        swiperefreshlayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        swiperefreshlayout.setSize(SwipeRefreshLayout.LARGE);

        //设置下拉刷新的监听
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //检查是否处于刷新状态
                if (!isRefresh) {
                    isRefresh = true;
                    //模拟加载网络数据，这里设置4秒，正好能看到4色进度条

                    //是否隐藏刷新进度条
                    swiperefreshlayout.setRefreshing(false);
                    //刷新页面数据
                    refresh.refresh();
                    //刷新完成重置刷新状态为false
                    isRefresh = false;
                }

            }
        });

    }





    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object event){
//        new LogoutDialog(mActivity, R.style.custom_dialog).show();
    }

}
