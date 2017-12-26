package com.ngo100.systemapp.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ngo100.systemapp.R;
import com.ngo100.systemapp.bean.HomeObj;

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
    }
}
