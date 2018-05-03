package com.grimos.push.activity;

import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.grimos.push.R;
import com.grimos.push.base.BaseActivity;
import com.grimos.push.bean.ExlporerDetailsObj;
import com.grimos.push.http.HttpCallBack;
import com.grimos.push.http.HttpUtil;
import com.grimos.push.util.JSONParser;
import com.grimos.push.util.LoadingDialogUtils;
import com.grimos.push.util.SystemUtil;
import com.grimos.push.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_details)
public class DetailsActivity extends BaseActivity {

//    @ViewInject(R.id.name_tv)
//    private TextView name_tv;
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


    @Override
    protected void init() {
        download_btn.setOnClickListener(this);
        order_url=getIntent().getStringExtra("order_url");
        msg_url=getIntent().getStringExtra("msg_url");
        id=getIntent().getIntExtra("id",0);
        getData();
    }

    private void getData() {
        LoadingDialogUtils.createLoadingDialog(mActivity).show();
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
//        name_tv.setText(data.name);
        developer_tv.setText("开发者："+data.developer);
        updatetime_tv.setText("更新时间："+data.upload_time);
        versionName_tv.setText("版本号："+data.version);

        describe_title_tv.setText(data.introduction);
//        data.description="&lt;p&gt;&lt;span class=&quot;font16&quot; style=&quot;font-size: 18px; color: rgb(102, 102, 102); font-family: &amp;quot;Microsoft Yahei&amp;quot;, simsun, sans-serif, Arial, Helvetica;&quot;&gt;ROM 介绍&lt;/span&gt;&lt;span style=&quot;color: rgb(102, 102, 102); font-family: &amp;quot;Microsoft Yahei&amp;quot;, simsun, sans-serif, Arial, Helvetica; font-size: 12px; background-color: rgb(255, 255, 255);&quot;&gt;&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span class=&quot;font16&quot; style=&quot;font-size: 18px; color: rgb(102, 102, 102); font-family: &amp;quot;Microsoft Yahei&amp;quot;, simsun, sans-serif, Arial, Helvetica;&quot;&gt;你好&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span class=&quot;font16&quot; style=&quot;font-size: 18px; color: rgb(102, 102, 102); font-family: &amp;quot;Microsoft Yahei&amp;quot;, simsun, sans-serif, Arial, Helvetica;&quot;&gt;你好&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span class=&quot;font16&quot; style=&quot;font-size: 18px; color: rgb(102, 102, 102); font-family: &amp;quot;Microsoft Yahei&amp;quot;, simsun, sans-serif, Arial, Helvetica;&quot;&gt;你好&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span class=&quot;font16&quot; style=&quot;font-size: 18px; color: rgb(102, 102, 102); font-family: &amp;quot;Microsoft Yahei&amp;quot;, simsun, sans-serif, Arial, Helvetica;&quot;&gt;你好&lt;/span&gt;&lt;/p&gt;&lt;div class=&quot;clean&quot; style=&quot;padding: 0px; margin: 0px; color: rgb(102, 102, 102); font-family: &amp;quot;Microsoft Yahei&amp;quot;, simsun, sans-serif, Arial, Helvetica; font-size: 12px; white-space: normal;&quot;&gt;&lt;/div&gt;";
        describe_tv.setMovementMethod(LinkMovementMethod.getInstance());
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                describe_tv.setText(Html.fromHtml(data.description,
                        Html.FROM_HTML_OPTION_USE_CSS_COLORS | Html.FROM_HTML_MODE_COMPACT));
            } else {
                describe_tv.setText(Html.fromHtml(data.description));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.msg_img:
//                AutoWedActivity.startAty(mActivity,msg_url);
                SystemUtil.jumpMsg(mActivity,msg_url);
                break;
            case R.id.order_img:
                AutoWedActivity.startAty(mActivity,order_url);
                break;
            case R.id.download_btn:
                if (vip_level>0){
                    Intent intent=new Intent(mActivity,DownloadListActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    break;
                }
                Intent intent=new Intent(mActivity,DownloadActivty.class);
                intent.putExtra("vip",vip_level);
                intent.putExtra("order_url",order_url);
                intent.putExtra("msg_url",msg_url);
                intent.putExtra("download_url",msg_url);
                intent.putExtra("month",data.month);
                intent.putExtra("quarter",data.quarter);
                intent.putExtra("year",data.year);
                intent.putExtra("id",data.id);
                intent.putExtra("name",data.name);
                startActivity(intent);
                break;
        }
    }
}
