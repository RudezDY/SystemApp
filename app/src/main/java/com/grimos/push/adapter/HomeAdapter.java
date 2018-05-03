package com.grimos.push.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.grimos.push.R;
import com.grimos.push.bean.HomeObj;

/**
 * Created by homework on 2017/12/26.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeObj.HomeData.AppsList,BaseViewHolder> {
    Context context;

    public HomeAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeObj.HomeData.AppsList item) {
        helper.setText(R.id.title_tv,item.name);
        helper.setText(R.id.describe_tv,item.introduction);
        helper.setText(R.id.time_tv,item.upload_time);
        helper.setText(R.id.status_tv,"下载");

        ImageView img_new=helper.getView(R.id.img_new);
        //if //判断是否需要显示new图标
        if (item.newest==0){
            img_new.setVisibility(View.GONE);
        }else {
            img_new.setVisibility(View.VISIBLE);
        }

    }
}
