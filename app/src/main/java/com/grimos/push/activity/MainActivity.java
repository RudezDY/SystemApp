package com.grimos.push.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.grimos.push.R;
import com.grimos.push.adapter.HomeAdapter;
import com.grimos.push.app.App;
import com.grimos.push.base.BaseActivity;
import com.grimos.push.bean.HomeObj;
import com.grimos.push.http.HttpCallBack;
import com.grimos.push.http.HttpUtil;
import com.grimos.push.user.User;
import com.grimos.push.util.AnimatorUtil;
import com.grimos.push.util.DensityUtil;
import com.grimos.push.util.JSONParser;
import com.grimos.push.util.SystemUtil;
import com.grimos.push.util.TextUtil;
import com.grimos.push.util.ToastUtil;
import com.grimos.push.widget.BaseSwipeRefreshLayout;
import com.grimos.push.widget.WeChatSkipDialog;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.swipeLayout)
    BaseSwipeRefreshLayout swipeLayout;
    @ViewInject(R.id.home_rv)
    RecyclerView home_rv;
    @ViewInject(R.id.homepage_layout)
    LinearLayout homepage_layout;
    @ViewInject(R.id.other_layout)
    LinearLayout other_layout;


    //SlidMenu相关
    @ViewInject(R.id.simple_navigation_drawer)
    DrawerLayout simple_navigation_drawer;
    @ViewInject(R.id.iv_vip)
    ImageView iv_vip;
    @ViewInject(R.id.tv_date)
    TextView tv_date;
    @ViewInject(R.id.tv_jixing)
    TextView tv_jixing;
    @ViewInject(R.id.tv_deviceID)
    TextView tv_deviceID;
    @ViewInject(R.id.layout_alipay)
    LinearLayout layout_alipay;
    @ViewInject(R.id.layout_wechat)
    LinearLayout layout_wechat;
    @ViewInject(R.id.layout_about)
    LinearLayout layout_about;
    @ViewInject(R.id.layout_developer)
    LinearLayout layout_developer;
    @ViewInject(R.id.layout_customer)
    LinearLayout layout_customer;
    @ViewInject(R.id.layout_content)
    LinearLayout layout_content;
    @ViewInject(R.id.img_unbind)
    ImageView img_unbind;

    HomeObj.HomeData homedData;//首页所有数据
    HomeAdapter adapter;//资源列表adapter

    String order_url,msg_url;
    String jixing;
    boolean menuIsOpen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().isCreat=true;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    protected void init() {
        setTitleBar("SystemAPP");
        qq_img.setVisibility(View.VISIBLE);
        menuImg.setVisibility(View.VISIBLE);
        qq_img.setOnClickListener(this);
        menuImg.setOnClickListener(this);
        homepage_layout.setOnClickListener(this);
        other_layout.setOnClickListener(this);
        backImg.setVisibility(View.GONE);
        jixing= User.getInstance().model;
        adapter=new HomeAdapter(R.layout.item_homelist,mActivity);
        home_rv.setLayoutManager(new LinearLayoutManager(mActivity));
        home_rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(mActivity,DetailsActivity.class);
                Log.i("position",position+"");
                intent.putExtra("id",homedData.list.get(position).id);
                intent.putExtra("order_url",order_url);
                intent.putExtra("msg_url",msg_url);
                startActivity(intent);
            }
        });

        setSwipeRefreshLayout(swipeLayout);
        setOnRefreshListener(new BaseSwipeRefreshLayout.OnRefresh() {
            @Override
            public void refresh() {
                getData();
            }
        });

        initSlid();
    }

    float mPosX,mCurPosX;
    //处理侧滑菜单
    private void initSlid() {
        layout_alipay.setOnClickListener(onMenuClickListener);
        layout_wechat.setOnClickListener(onMenuClickListener);
        layout_about.setOnClickListener(onMenuClickListener);
        layout_developer.setOnClickListener(onMenuClickListener);
        layout_customer.setOnClickListener(onMenuClickListener);
        tv_deviceID.setOnClickListener(onMenuClickListener);
        tv_jixing.setOnClickListener(onMenuClickListener);
        img_unbind.setOnClickListener(onMenuClickListener);
        String sbm="我的设备码："+User.getInstance().identifier+"\n(点击复制)";
        new TextUtil().setTextSize(tv_deviceID,sbm,sbm.length()-6,sbm.length(), DensityUtil.dp2px(mActivity,14));

        //处理滑动展开侧滑栏，不加的话只能从边缘划出
        home_rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getRawX();//这句不知道为啥没执行，所以在下面重新处理了
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mPosX==0)
                            mPosX = event.getRawX();
                        mCurPosX = event.getRawX();

                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.i("pox:"+mPosX+"    curX:"+mCurPosX);
                        if (mPosX<screenWidth/2&&mCurPosX - mPosX >40) {
                            mPosX=0;
                            simple_navigation_drawer.openDrawer(Gravity.START);
                            AnimatorUtil.rotation(menuImg,0,-90,400);
                        }
                        break;
                }
                return false;
            }
        });

        //监听侧边栏打开状态
        simple_navigation_drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (Build.VERSION.SDK_INT>=LOLLIPOP)
                    getWindow().setStatusBarColor(Color.parseColor("#F38B58"));
                menuIsOpen=true;

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (Build.VERSION.SDK_INT>=LOLLIPOP)
                    getWindow().setStatusBarColor(getResources().getColor(R.color.color_titlebar));
                menuIsOpen=false;
                AnimatorUtil.rotation(menuImg,-60,0,200);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    //侧滑栏控件的点击事件
    View.OnClickListener onMenuClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            switch (view.getId()){
                case R.id.layout_alipay://支付宝捐赠
                    String skip_ali="感谢您的捐赠，我们会用心为您提供更好的Rom服务！即将跳转到支付宝转账，如有疑问请联系客服！";
//                    showAliPayDialog(skip_ali);
                    showJzDialog(skip_ali,1);

                    break;
                case R.id.layout_wechat://微信捐赠
                    String skip="感谢您的捐赠，我们会用心为您提供更好的Rom服务！即将跳转到微信扫码支付，请确保手机已有开发者收款码。如有疑问请联系客服！";
//                    showWexinPayDialog(skip);
                    showJzDialog(skip,2);
                    break;
                case R.id.layout_about://关于软件
                    startAty(AboutUsActivity.class);
                    break;
                case R.id.layout_developer://开发者选项
                    startAty(VipActivity.class);
                    break;
                case R.id.layout_customer://联系客服
                    startAty(CustomerActivity.class);
                    break;
                case R.id.tv_deviceID: //复制设备码
                    cm.setPrimaryClip(ClipData.newPlainText(null,User.getInstance().identifier));
                    ToastUtil.show("已复制设备码到剪切板");

                    break;
                case R.id.tv_jixing://解绑机型
                    unBind();
                    break;
                case R.id.img_unbind://解绑机型
                    unBind();
                    break;
            }
        }
    };


    //解绑
    private void unBind() {
        HttpUtil.unBind(new HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                String code=JSONParser.getStringFromJsonString("status",result);
                String msg=JSONParser.getStringFromJsonString("msg",result);
                ToastUtil.show(mActivity,msg, Toast.LENGTH_LONG);
                if ("1".equals(code)){//解绑成功
                    sharedpreferencesUtil.putString("jixing","");
                    if (homedData!=null){//首页有数据，跳转到选择机型界面
                        Intent intent=new Intent(mActivity,SelectActivity.class);
                        intent.putExtra("data",homedData);
                        startActivity(intent);
                    }else {
                        getData();
                    }
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    /**
     * 微信捐赠dialog
     * @param skip 提示字段
     */
    private void showWexinPayDialog(String skip) {
        WeChatSkipDialog dialog=new WeChatSkipDialog(mActivity, R.style.custom_dialog, User.getInstance().identifier,skip);
        dialog.setOnAffirmListener(new WeChatSkipDialog.OnAffirmListener() {
            @Override
            public void onAffirm(String code) {
                SystemUtil.openWeixinToQE_Code(mActivity);
            }
        });
        dialog.show();
    }

    /**
     * 支付宝捐赠dialog
     */
    private void showAliPayDialog(String skip) {
        WeChatSkipDialog dialog=new WeChatSkipDialog(mActivity, R.style.custom_dialog, User.getInstance().identifier,skip);
        dialog.setOnAffirmListener(new WeChatSkipDialog.OnAffirmListener() {
            @Override
            public void onAffirm(String code) {
                SystemUtil.toAlipay(mActivity,"FKX06561QGEKOFQCMZLO79");
            }
        });
        dialog.show();

    }

    /**
     *
     * @param skip
     * @param payType 1支付宝2微信
     */
    public void showJzDialog(String skip,final int payType){
        new AlertDialog.Builder(mActivity)
                .setTitle("温馨提示")
                .setMessage(skip)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (payType){
                            case 1://支付宝
                                SystemUtil.toAlipay(mActivity,"FKX06561QGEKOFQCMZLO79");
                                break;
                            case 2://微信
                                SystemUtil.openWeixinToQE_Code(mActivity);
                                break;
                            default:
                                break;

                        }

                    }
                })
                .create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        jixing=sharedpreferencesUtil.getString("jixing");
        getData();
    }

    //获取首页数据
    private void getData() {
        HttpUtil.getHomeList(jixing, new HttpCallBack() {
//        HttpUtil.getHomeList("", new HttpCallBack() {
            @Override
            public void onSuccess(String result) {

                String code = JSONParser.getStringFromJsonString("status",result);
                String msg = JSONParser.getStringFromJsonString("msg",result);
                if ("1".equals(code)){
                    HomeObj obj=new Gson().fromJson(result,HomeObj.class);
                    homedData=obj.data;
                    order_url=homedData.link.order_link;
                    msg_url=homedData.link.notice_link;

                    //未绑定进行机型选择
                    if (TextUtils.isEmpty(homedData.usermode)){
                        //进行品牌机型选择
                        Intent intent=new Intent(mActivity,SelectActivity.class);
                        intent.putExtra("data",homedData);
                        startActivity(intent);
                    }
                    sharedpreferencesUtil.putString("jixing",homedData.usermode);
                    //保存首页信息到本地
                    sharedpreferencesUtil.putString("homeData",new Gson().toJson(homedData));
                    setView();
                }else {//失败暂时使用本地缓存数据
                    LogUtil.e(msg);
                    if (!TextUtils.isEmpty(sharedpreferencesUtil.getString("homeData"))){
                        homedData=new Gson().fromJson(sharedpreferencesUtil.getString("homeData"), HomeObj.HomeData.class);
                        setView();
                    }
                }
            }

            @Override
            public void onFailure(String error) {//失败暂时使用本地缓存数据
                if (!TextUtils.isEmpty(sharedpreferencesUtil.getString("homeData"))){
                    homedData=new Gson().fromJson(sharedpreferencesUtil.getString("homeData"), HomeObj.HomeData.class);
                    setView();
                }
            }
        });
    }

    private void setView() {
        adapter.setNewData(homedData.list);
        tv_date.setText(homedData.vip_time);
        iv_vip.setImageLevel(homedData.vip);
        String jixing=sharedpreferencesUtil.getString("jixing");
        if (!TextUtils.isEmpty(jixing)){
            String jxStr="我的机型:"+jixing+"(解绑)";
//            String jxStr="我的机型:"+jixing;
//            new TextUtil().setSpanClick(tv_jixing,jxStr,jxStr.length()-4,jxStr.length(),onMenuClickListener);
            new TextUtil().setTextColor(tv_jixing,jxStr,jxStr.length()-4,jxStr.length(),"#0099e5");

        }else {
            tv_jixing.setText("");
        }

        if (homedData.link.not_read>0){
            String msg="您有"+homedData.link.not_read+"条未读消息！";
            showMsgDialog(msg);
        }

    }

    @Override
    public void onClick(View view) {
        if (homedData==null){
            ToastUtil.show("当前未有数据，请检查网络或联系客服");
            return;
        }
        switch (view.getId()){
            case R.id.msg_img:
//                AutoWedActivity.startAty(mActivity,msg_url);
                SystemUtil.jumpMsg(mActivity,msg_url);
                break;
            case R.id.order_img:
                AutoWedActivity.startAty(mActivity,order_url);
                break;
            case R.id.qq_img:
                AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
                builder.setTitle("QQ群")
                        .setMessage("qq群号："+homedData.link.qq_link)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SystemUtil.joinQQGroup(mActivity,homedData.link.qq_key);
                            }
                        })
                        .create().show();
                break;
            case R.id.homepage_layout:
                AutoWedActivity.startAty(mActivity,homedData.link.information_link);
                break;
            case R.id.other_layout:
                SystemUtil.doStartApplicationWithPackageName(mActivity,homedData.link.thirdparty);
                break;
            case R.id.menuImg:
                openMenu();
                break;
        }
    }

    private void openMenu() {
        if (!menuIsOpen){
            AnimatorUtil.rotation(menuImg,0,-90,400);
            new Thread(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                simple_navigation_drawer.openDrawer(Gravity.START);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }else {
            simple_navigation_drawer.closeDrawer(Gravity.START);
        }
    }

    private void showMsgDialog(String msg){
        new AlertDialog.Builder(mActivity)
                .setTitle("温馨提示")
                .setMessage(msg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("去看看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        AutoWedActivity.startAty(mActivity,msg_url);
                        SystemUtil.jumpMsg(mActivity,msg_url);
                    }
                })
                .create().show();
    }


    @Override
    public void onBackPressed() {
        if (simple_navigation_drawer.isDrawerOpen(GravityCompat.START))
            simple_navigation_drawer.closeDrawer(Gravity.START);
        else
            super.onBackPressed();
    }
}
