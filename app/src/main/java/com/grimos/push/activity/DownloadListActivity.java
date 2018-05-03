package com.grimos.push.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.grimos.push.R;
import com.grimos.push.adapter.DownloadListAdapter;
import com.grimos.push.base.BaseActivity;
import com.grimos.push.bean.DownloadListObj;
import com.grimos.push.http.HttpCallBack;
import com.grimos.push.http.HttpUtil;
import com.grimos.push.util.JSONParser;
import com.grimos.push.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

@ContentView(R.layout.activity_download_list)
public class DownloadListActivity extends BaseActivity {

    @ViewInject(R.id.rv_download_out)
    RecyclerView rv_download_out;

    DownloadListAdapter adapter;
    int id;
    List<DownloadListObj.Data> data;

    @Override
    protected void init() {
        setTitleBar("下载地址");
        msg_img.setVisibility(View.GONE);
        order_img.setVisibility(View.GONE);
        id=getIntent().getIntExtra("id",0);
        getData();
    }

    private void getData() {
        HttpUtil.getDownloadList(id, new HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                String code= JSONParser.getStringFromJsonString("status",result);
                String msg= JSONParser.getStringFromJsonString("msg",result);
                if ("1".equals(code)){
                    DownloadListObj obj=new Gson().fromJson(result,DownloadListObj.class);
                    data=obj.data;
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
        adapter=new DownloadListAdapter(mActivity,R.layout.item_downlod_out,data);
        rv_download_out.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_download_out.setItemAnimator(new SlideInUpAnimator());
        rv_download_out.setAdapter(adapter);
    }
    @Override
    public void onClick(View view) {

    }
}
