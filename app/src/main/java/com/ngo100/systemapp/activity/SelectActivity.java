package com.ngo100.systemapp.activity;

import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ngo100.systemapp.R;
import com.ngo100.systemapp.adapter.BrandSelectAdapter;
import com.ngo100.systemapp.base.BaseActivity;
import com.ngo100.systemapp.bean.HomeObj;
import com.ngo100.systemapp.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

@ContentView(R.layout.activity_select)
public class SelectActivity extends BaseActivity {

    @ViewInject(R.id.rv_brand)
    RecyclerView rv_brand;
    @ViewInject(R.id.iv_next)
    ImageView iv_next;
    @ViewInject(R.id.layout_type)
    LinearLayout layout_type;
    @ViewInject(R.id.tv_brand_hint)
    TextView tv_brand_hint;
    @ViewInject(R.id.tv_brand)
    TextView tv_brand;
    @ViewInject(R.id.tv_type)
    TextView tv_type;
    @ViewInject(R.id.title_tv)
    TextView title_tv;
    @ViewInject(R.id.btn_comfirm)
    Button btn_comfirm;

    HomeObj.HomeData homeData;

    BrandSelectAdapter mAdapter;
    List<String> brandList;
    List<String> typeList;

    int type_adapter=0;//0品牌1机型

    String jixing_str;

    @Override
    protected void init() {
        if (Build.VERSION.SDK_INT>=LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        title_tv.setText("选择您的机型");
        iv_next.setOnClickListener(this);
        btn_comfirm.setOnClickListener(this);
        homeData= (HomeObj.HomeData) getIntent().getSerializableExtra("data");

        brandList=new ArrayList<>();
        typeList=new ArrayList<>();
        mAdapter =new BrandSelectAdapter(R.layout.item_jixing);
        rv_brand.setLayoutManager(new GridLayoutManager(mActivity,3));
        rv_brand.setAdapter(mAdapter);

        for (HomeObj.Category category:homeData.category){
            brandList.add(category.name);
        }
        type_adapter=0;
        mAdapter.setNewData(brandList);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.setCurSelect(position);
                if (type_adapter==0){
                    getTypeData(position);
                }
                else if (type_adapter==1){
                    jixing_str=typeList.get(position);
                    tv_type.setText(jixing_str);
                }

            }
        });

    }

    private void getTypeData(int position) {
        typeList=homeData.category.get(position).chidren;
        tv_brand.setText(homeData.category.get(position).name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_next:
                if (mAdapter.getCurSelect()>-1)
                    setTypeAdapter();
                else
                    ToastUtil.show("您还没有选择品牌");
                break;
            case R.id.btn_comfirm:
                //TODO 选择完成之后的处理
                if (mAdapter.getCurSelect()<=0){
                    ToastUtil.show("您还没有选择机型");
                    break;
                }
                ToastUtil.show("选择机型为："+jixing_str);
                sharedpreferencesUtil.putString("jixing",jixing_str);
                sharedpreferencesUtil.putBoolean("notFirst",true);
                finish();
                break;
        }
    }

    private void setTypeAdapter() {
        mAdapter.setNewData(typeList);
        type_adapter=1;
        tv_brand_hint.setVisibility(View.GONE);
        layout_type.setVisibility(View.VISIBLE);
        iv_next.setVisibility(View.GONE);
        mAdapter.setCurSelect(-1);

    }

    @Override
    public void onBackPressed() {

    }
}
