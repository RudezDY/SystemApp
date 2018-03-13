package com.ngo100.systemapp.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ngo100.systemapp.R;
import com.ngo100.systemapp.adapter.HomeAdapter;
import com.ngo100.systemapp.base.BaseActivity;
import com.ngo100.systemapp.bean.HomeObj;
import com.ngo100.systemapp.http.HttpCallBack;
import com.ngo100.systemapp.http.HttpUtil;
import com.ngo100.systemapp.user.User;
import com.ngo100.systemapp.util.DeviceUtil;
import com.ngo100.systemapp.util.JSONParser;
import com.ngo100.systemapp.util.SystemUtil;
import com.ngo100.systemapp.util.ToastUtil;
import com.ngo100.systemapp.widget.BaseSwipeRefreshLayout;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

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
    @ViewInject(R.id.layout_alipay)
    LinearLayout layout_alipay;
    @ViewInject(R.id.layout_wechat)
    LinearLayout layout_wechat;
    @ViewInject(R.id.layout_about)
    LinearLayout layout_about;
    @ViewInject(R.id.layout_developer)
    LinearLayout layout_developer;
    @ViewInject(R.id.layout_content)
    LinearLayout layout_content;

    HomeObj.HomeData homedData;
    HomeAdapter adapter;

    String order_url,msg_url;
    String jixing;


    @Override
    protected void init() {
        setTitleBar("SystemAPP");
        qq_img.setVisibility(View.VISIBLE);
        qq_img.setOnClickListener(this);
        homepage_layout.setOnClickListener(this);
        other_layout.setOnClickListener(this);
        backImg.setVisibility(View.GONE);
        User.getInstance().identifier= DeviceUtil.getDeiceID(mActivity);
        User.getInstance().model= Build.MODEL;
        User.getInstance().firm= Build.MANUFACTURER;
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

    float mPosX=0,mCurPosX=0;
    //处理侧滑菜单
    private void initSlid() {
        layout_alipay.setOnClickListener(onMenuClickListener);
        layout_wechat.setOnClickListener(onMenuClickListener);
        layout_about.setOnClickListener(onMenuClickListener);
        layout_developer.setOnClickListener(onMenuClickListener);

        home_rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();

                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.i("pox:"+mPosX+"    curX:"+mCurPosX);
                        if (mPosX<screenWidth/2&&mCurPosX - mPosX >25) {
                            simple_navigation_drawer.openDrawer(Gravity.START);
                        }
                        break;
                }
                return false;
            }
        });

        simple_navigation_drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (Build.VERSION.SDK_INT>=LOLLIPOP)
                    getWindow().setStatusBarColor(Color.parseColor("#F38B58"));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (Build.VERSION.SDK_INT>=LOLLIPOP)
                    getWindow().setStatusBarColor(getResources().getColor(R.color.color_titlebar));
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    View.OnClickListener onMenuClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.layout_alipay:
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setPrimaryClip(ClipData.newPlainText(null,"#吱口令#长按复制此条消息，打开支付宝给我转账8iYTf1880e"));
                    SystemUtil.jumpAlipay(mActivity);
                    break;
                case R.id.layout_wechat:
                    SystemUtil.openWeixinToQE_Code(mActivity);
                    break;
                case R.id.layout_about:

                    break;
                case R.id.layout_developer:

                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        jixing=sharedpreferencesUtil.getString("jixing");
        getData();
    }

    private void getData() {
        HttpUtil.getHomeList(jixing, new HttpCallBack() {
            @Override
            public void onSuccess(String result) {

                String code = JSONParser.getStringFromJsonString("status",result);
                String msg = JSONParser.getStringFromJsonString("msg",result);
                if ("1".equals(code)){
                    HomeObj obj=new Gson().fromJson(result,HomeObj.class);
                    homedData=obj.data;
                    order_url=homedData.link.order_link;
                    msg_url=homedData.link.notice_link;
                    setView();
                    //首次进入app进行机型选择
                    if (!sharedpreferencesUtil.getBoolean("notFirst")){
                        //进行品牌机型选择
                        Intent intent=new Intent(mActivity,SelectActivity.class);
                        intent.putExtra("data",homedData);
                        startActivity(intent);
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

    private void setView() {
        adapter.setNewData(homedData.list);
        tv_date.setText(homedData.vip_time);
        iv_vip.setImageLevel(homedData.vip);

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
                                SystemUtil.joinQQGroup(mActivity,homedData.link.qq_link+"");
                            }
                        })
                        .create().show();
                break;
            case R.id.homepage_layout:
                AutoWedActivity.startAty(mActivity,homedData.link.information_link);
                break;
            case R.id.other_layout:
                doStartApplicationWithPackageName(homedData.link.thirdparty);
                break;
        }
    }



    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            ToastUtil.show("未找到对应包名");
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
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
            startActivity(intent);
        }
    }

}
