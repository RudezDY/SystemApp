package com.ngo100.systemapp.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ngo100.systemapp.R;
import com.ngo100.systemapp.adapter.PopupListAdapter;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by donghui on 2017/10/11.
 */

public class PopupList extends BasePopupWindow {
    Context context;
    List<String> data;

    private RecyclerView popup_rv;

    public PopupList(Context context, List<String> data) {
        super(context);
        this.context=context;
        this.data=data;
        init();
    }

    public PopupList(Context context, int w, int h, List<String> data) {
        super(context, w, h);
        this.context=context;
        this.data=data;
        init();
    }

    PopupListAdapter adapter;
    private void init() {
        popup_rv= (RecyclerView) findViewById(R.id.popup_rv);


        adapter=new PopupListAdapter(R.layout.item_popup_list,context);
        popup_rv.setLayoutManager(new LinearLayoutManager(context));
        popup_rv.setAdapter(adapter);
        adapter.setNewData(data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onItemSelectedListener.onItemSelected(position);
                dismiss();
            }
        });
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
        if (onItemSelectedListener!=null){
            this.onItemSelectedListener=onItemSelectedListener;
        }
    }

    public OnItemSelectedListener onItemSelectedListener;
    public interface OnItemSelectedListener{
        void onItemSelected(int position);
    }


    @Override
    protected Animation initShowAnimation() {
        AnimationSet set=new AnimationSet(false);
        Animation shakeAnima=new RotateAnimation(0,15, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        shakeAnima.setInterpolator(new CycleInterpolator(5));
        shakeAnima.setDuration(400);
        set.addAnimation(getDefaultAlphaAnimation());
        set.addAnimation(shakeAnima);
        return set;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_list);
    }

    @Override
    public View initAnimaView() {
        return null;
    }


    public void showPopupWindowWithData(View v, List<String> data){
        this.data=data;
        adapter.setNewData(data);
        showPopupWindow(v);

    }
}
