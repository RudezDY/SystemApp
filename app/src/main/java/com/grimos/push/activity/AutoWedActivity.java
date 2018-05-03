package com.grimos.push.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grimos.push.R;
import com.grimos.push.base.BaseActivity;
import com.grimos.push.http.CheckNetwork;
import com.grimos.push.util.ActivityTaskManeger;
import com.grimos.push.util.NetworkUtils;
import com.grimos.push.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
@ContentView(R.layout.activity_auto_wed)
public class AutoWedActivity extends BaseActivity {
    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.pb_progress)
    private ProgressBar pb_progress;

    private String url;


    protected ImageView closeImg;

    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;

    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR=1;



    protected void initTitleBar(){
        if (null==title_layout)
            title_layout= (RelativeLayout) findViewById(R.id.title_layout);
        if (null==closeImg)
            closeImg= (ImageView) findViewById(R.id.closeImg);
        if (null==backImg)
            backImg= (ImageView) findViewById(R.id.backImg);

        closeImg.setVisibility(View.VISIBLE);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTaskManeger.getInstance().closeActivity(mActivity);
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void setTitleBar(String title){

        if (null==title_tv)
            title_tv= (TextView) findViewById(R.id.title_tv);

        title_layout.setVisibility(View.VISIBLE);
        title_tv.setText(title);

    }





    @Override
    protected void init() {
        url=getIntent().getStringExtra("url");
        initTitleBar();
        initWeb();
    }



    private void initWeb() {
        startProgress();
        Log.i("loadurl",url);
        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);//DOM Storage
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setDatabaseEnabled(true);


        //设置本地调用对象及其接口
//        webView.addJavascriptInterface(new LibraJavaScriptInterface(mActivity), "libra");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                startProgress();
                Log.i("loadurl",url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hindProgressBar();
                if (!CheckNetwork.isNetworkConnected(mActivity)) {
                    hindProgressBar();
                }
                // html加载完成之后，添加监听图片的点击js函数
                setTitleBar(view.getTitle());
                super.onPageFinished(view, url);
            }

            // 视频全屏播放按返回页面被放大的问题
            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
                if (newScale - oldScale > 7) {
                    view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
                }
            }


        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitleBar(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i("进度",newProgress+"");
                pb_progress.setProgress(newProgress);

            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }




        });


    }

    // Create an image file
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_"+timeStamp+"_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName,".jpg",storageDir);
    }

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }


    /**
     * 开始进度条
     */
    private void startProgress() {
        pb_progress.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏进度条
     */
    private void hindProgressBar() {
        pb_progress.setVisibility(View.GONE);
    }

    public static void startAty(Context context, String url){
        if (!NetworkUtils.isAvailable(context)){
            ToastUtil.show("网络不可用，请检查网络后重试");
            return;
        }
        Intent intent=new Intent(context,AutoWedActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

    }
}
