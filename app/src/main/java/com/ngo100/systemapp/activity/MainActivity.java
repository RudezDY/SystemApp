package com.ngo100.systemapp.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
import com.ngo100.systemapp.util.ToastUtil;
import com.ngo100.systemapp.widget.BaseSwipeRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.swipeLayout)
    BaseSwipeRefreshLayout swipeLayout;
    @ViewInject(R.id.home_rv)
    RecyclerView home_rv;

    HomeObj.HomeData homedData;
    HomeAdapter adapter;

    String order_url,msg_url;

    @Override
    protected void init() {
        setTitleBar("SystemAPP");
        backImg.setVisibility(View.GONE);
        User.getInstance().identifier= DeviceUtil.getDeiceID(mActivity);
        User.getInstance().model= Build.MODEL;
        User.getInstance().firm= Build.MANUFACTURER;
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


    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
//        HttpUtil.getHomeList(User.getInstance().model, new HttpCallBack() {
        HttpUtil.getHomeList("Samsung galaxy c9 pro", new HttpCallBack() {
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
        }
    }
}
