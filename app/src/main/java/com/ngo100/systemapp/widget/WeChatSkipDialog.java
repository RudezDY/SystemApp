package com.ngo100.systemapp.widget;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ngo100.systemapp.R;
import com.ngo100.systemapp.user.User;
import com.ngo100.systemapp.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by homework on 2018/1/23.
 */

public class WeChatSkipDialog extends Dialog {
    @BindView(R.id.serial_tv)
    TextView serial_tv;
    @BindView(R.id.copy_tv)
     TextView copy_tv;
    @BindView(R.id.skip_tv)
     TextView skip_tv;
    @BindView(R.id.cancle_tv)
     TextView cancle_tv;
    @BindView(R.id.affirm_tv)
     TextView affirm_tv;

    boolean isCopy;
    Context context;
    String serialCode,skip;

    public WeChatSkipDialog(@NonNull Context context,String serialCode,String skip) {
        super(context);
        this.context=context;
        this.serialCode=serialCode;
        this.skip=skip;
    }

    public WeChatSkipDialog(@NonNull Context context, int themeResId,String serialCode,String skip) {
        super(context, themeResId);
        this.context=context;
        this.serialCode=serialCode;
        this.skip=skip;
    }

    protected WeChatSkipDialog(@NonNull Context context,String serialCode,String skip, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context=context;
        this.serialCode=serialCode;
        this.skip=skip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_skip);
        ButterKnife.bind(this);
        setCancelable(false);
        serial_tv.setText("序列码："+serialCode);
        skip_tv.setText(skip);

    }

    @OnClick({R.id.copy_tv,R.id.cancle_tv,R.id.affirm_tv})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.copy_tv:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", User.getInstance().identifier);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                isCopy=true;
                ToastUtil.show("已复制到剪切板");
                break;
            case R.id.cancle_tv:
                if (!isCopy){
                    ToastUtil.show("您还没复制序列码噢");
                    break;
                }
                dismiss();
                break;
            case R.id.affirm_tv:
                if (!isCopy){
                    ToastUtil.show("您还没复制序列码噢");
                    break;
                }
                if (onAffirmListener!=null) {
                    dismiss();
                    onAffirmListener.onAffirm(serialCode);
                }
                break;
        }
    }

    OnAffirmListener onAffirmListener;
    public WeChatSkipDialog setOnAffirmListener(OnAffirmListener onAffirmListener){
        if (onAffirmListener!=null){
            this.onAffirmListener=onAffirmListener;
        }
        return this;
    }
    public interface OnAffirmListener{
        void onAffirm(String code);
    }
}
