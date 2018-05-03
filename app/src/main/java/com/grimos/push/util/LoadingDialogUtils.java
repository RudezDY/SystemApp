package com.grimos.push.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grimos.push.R;


/**
 * 加载通用对话框
 */
public class LoadingDialogUtils {

    private static Dialog loadingDialog;

    // 进度
    private static ProgressBar progressBar;
    private static TextView progressContent;
    public static final int MAX = 100;

    /**
     * 显示加载框
     *
     * @param context
     * @return
     */
    public static Dialog createLoadingDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_loading, null);
        loadingDialog = new Dialog(context,
                R.style.custom_dialog);// 创建自定义样式
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        return loadingDialog;
    }
    public static Dialog createLoadingDialog(Context context, View view) {
        loadingDialog = new Dialog(context,
                R.style.custom_dialog);// 创建自定义样式
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        return loadingDialog;
    }


    /**
     * 关闭对话框
     */
    public static void closeLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

}
