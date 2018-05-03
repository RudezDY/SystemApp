package com.grimos.push.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.grimos.push.R;
import com.grimos.push.bean.DownloadListObj;
import com.grimos.push.util.AnimatorUtil;
import com.grimos.push.util.ToastUtil;
import com.grimos.push.util.selectabletexthelper.OnSelectListener;
import com.grimos.push.util.selectabletexthelper.SelectableTextHelper;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by homework on 2018/3/15.
 */

public class DownloadListAdapter extends BaseQuickAdapter<DownloadListObj.Data,BaseViewHolder> {
    Activity context;

    public DownloadListAdapter(Activity context,int layoutResId, @Nullable List<DownloadListObj.Data> data) {
        super(layoutResId, data);
        this.context=context;
    }

    public DownloadListAdapter(Activity context,@Nullable List<DownloadListObj.Data> data) {
        super(data);
        this.context=context;
    }

    public DownloadListAdapter(Activity context,int layoutResId) {
        super(layoutResId);
        this.context=context;
    }

    int height;
    int mPositon;
    @Override
    protected void convert(final BaseViewHolder helper, final DownloadListObj.Data item) {
        helper.setText(R.id.tv_version,item.version);
        final RecyclerView rv_download_in=helper.getView(R.id.rv_download_in);
        rv_download_in.setLayoutManager(new LinearLayoutManager(context));
//        final InAdapter adapter=new InAdapter(context,R.layout.item_download_in,item.info);
        final InAdapter adapter=new InAdapter(context,R.layout.item_download_in,new ArrayList<DownloadListObj.Data.Info>());
        rv_download_in.setFocusable(false);
        rv_download_in.setAdapter(adapter);
        final ImageView iv=helper.getView(R.id.iv_arrow);
        rv_download_in.setVisibility(View.VISIBLE);
//        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.openLoadAnimation(new BaseAnimation() {//自定义自上而下的动画
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "translationY", -view.getMeasuredHeight(), 0)
                };
            }
        });

        helper.getView(R.id.tv_version).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (adapter.getData().size()!=0){
                    AnimatorUtil.rotation(iv,90,0,500);
                    adapter.setNewData(new ArrayList<DownloadListObj.Data.Info>());
                }else {
                    AnimatorUtil.rotation(iv,0,90,500);
                    adapter.setNewData(item.info);
                }

            }
        });

    }

}

class InAdapter extends BaseQuickAdapter<DownloadListObj.Data.Info,BaseViewHolder> implements View.OnClickListener {
    Activity context;
    public InAdapter(Activity context,int layoutResId, @Nullable List<DownloadListObj.Data.Info> data) {
        super(layoutResId, data);
        this.context=context;
    }

    public InAdapter(Activity context,@Nullable List<DownloadListObj.Data.Info> data) {
        super(data);
        this.context=context;
    }

    public InAdapter(Activity context,int layoutResId) {
        super(layoutResId);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final DownloadListObj.Data.Info item) {
        helper.setText(R.id.tv_channel,item.channel);
        helper.setText(R.id.tv_adds,"下载链接:"+item.url);
        helper.setText(R.id.tv_pwd,"密码:"+item.pwd);
        helper.setText(R.id.tv_update_time,item.time);

        TextView tv_url_copy=helper.getView(R.id.tv_url_copy);
        TextView tv_pwd_copy=helper.getView(R.id.tv_pwd_copy);
        TextView tv_download=helper.getView(R.id.tv_download);

        setCopyText((TextView) helper.getView(R.id.tv_adds));


        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                switch (v.getId()){
                    case R.id.tv_url_copy:
                        // 将文本内容放到系统剪贴板里。
                        cm.setPrimaryClip(ClipData.newPlainText(null,item.url));
                        ToastUtil.show("已复制下载链接到剪切板");
                        break;
                    case R.id.tv_pwd_copy:
                        cm.setPrimaryClip(ClipData.newPlainText(null,item.pwd));
                        ToastUtil.show("已复制密码到剪切板");
                        break;
                    case R.id.tv_download:
                        try {
                            Uri content_uri = Uri.parse(item.url);
//                        Uri content_uri = Uri.parse("http://www.qq.com");
                            Intent intent = new Intent(Intent.ACTION_VIEW,content_uri);
                            context.startActivity(intent);
                        }catch (ActivityNotFoundException ae){
                            LogUtil.e(ae.getMessage());
                            ToastUtil.show("下载地址有误");
                        }

                        break;
                }
            }
        };
        tv_url_copy.setOnClickListener(onClickListener);
        tv_pwd_copy.setOnClickListener(onClickListener);
        tv_download.setOnClickListener(onClickListener);

    }

    @Override
    public void onClick(View v) {

    }

    private void setCopyText(TextView tv){
        SelectableTextHelper mSelectableTextHelper = new SelectableTextHelper.Builder(tv)
                .setSelectedColor(mContext.getResources().getColor(R.color.blue_51cbf4))
                .setCursorHandleSizeInDp(20)
                .setCursorHandleColor(mContext.getResources().getColor(R.color.color_titlebar))
                .build();

        mSelectableTextHelper.setSelectListener(new OnSelectListener() {
            @Override
            public void onTextSelected(CharSequence content) {

            }
        });
    }
}
