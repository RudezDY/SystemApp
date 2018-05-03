package com.grimos.push.adapter;

import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.CheckedTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.grimos.push.R;

import java.util.List;

/**
 * Created by homework on 2018/3/12.
 */

public class BrandSelectAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    int curItem=-1;
    public BrandSelectAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public BrandSelectAdapter(@Nullable List<String> data) {
        super(data);
    }

    public BrandSelectAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        CheckedTextView ctv=helper.getView(R.id.tv_name);
        ctv.setText(item);
        ctv.setGravity(Gravity.CENTER);
        if (curItem==helper.getAdapterPosition()){
            ctv.setChecked(true);
        }else {
            ctv.setChecked(false);
        }

    }

    //设置选中的哪一个
    public void setCurSelect(int position) {
        this.curItem = position;
        notifyDataSetChanged();
    }

    public int getCurSelect(){
        return curItem;
    }
}
