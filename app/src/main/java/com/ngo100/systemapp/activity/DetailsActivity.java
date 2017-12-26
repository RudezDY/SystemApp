package com.ngo100.systemapp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ngo100.systemapp.R;
import com.ngo100.systemapp.base.BaseActivity;
import com.ngo100.systemapp.bean.ExlporerDetailsObj;
import com.ngo100.systemapp.http.HttpCallBack;
import com.ngo100.systemapp.http.HttpUtil;
import com.ngo100.systemapp.util.JSONParser;
import com.ngo100.systemapp.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_details)
public class DetailsActivity extends BaseActivity {

    @ViewInject(R.id.name_tv)
    private TextView name_tv;
    @ViewInject(R.id.developer_tv)
    private TextView developer_tv;
    @ViewInject(R.id.updatetime_tv)
    private TextView updatetime_tv;
    @ViewInject(R.id.versionName_tv)
    private TextView versionName_tv;
    @ViewInject(R.id.describe_title_tv)
    private TextView describe_title_tv;
    @ViewInject(R.id.describe_tv)
    private TextView describe_tv;
    @ViewInject(R.id.download_btn)
    private Button download_btn;

    int id;
    int vip_level;
    String order_url,msg_url,download_url;

    ExlporerDetailsObj.ExplorerDetails data;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_details);
//    }

    @Override
    protected void init() {
        download_btn.setOnClickListener(this);
        order_url=getIntent().getStringExtra("order_url");
        msg_url=getIntent().getStringExtra("msg_url");
        id=getIntent().getIntExtra("id",0);
        getData();
    }

    private void getData() {
        HttpUtil.getExplorerDetails(id, new HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                String code = JSONParser.getStringFromJsonString("status",result);
                String msg = JSONParser.getStringFromJsonString("msg",result);
                if ("1".equals(code)){
                    data=new Gson().fromJson(result,ExlporerDetailsObj.class).data;
                    vip_level=new Gson().fromJson(result,ExlporerDetailsObj.class).vip;
                    download_url=data.download_url;
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
        setTitleBar(data.name);
        name_tv.setText(data.name);
        developer_tv.setText("开发者："+data.developer);
        updatetime_tv.setText("更新时间："+data.upload_time);
        versionName_tv.setText("版本号："+data.version);

        describe_title_tv.setText(data.introduction);
        describe_tv.setText(data.description);
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
            case R.id.download_btn:
                Intent intent=new Intent(mActivity,DownloadActivty.class);
                intent.putExtra("vip",vip_level);
                intent.putExtra("order_url",order_url);
                intent.putExtra("msg_url",msg_url);
                intent.putExtra("download_url",msg_url);
                startActivity(intent);
                break;
        }
    }
}
