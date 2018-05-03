package com.grimos.push.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.grimos.push.R;

/**
 * Created by donghui on 2017/8/21.
 */

public class PopupListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Context context;

    public PopupListAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_tv,item);

    }
}

