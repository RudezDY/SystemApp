package com.ngo100.systemapp.http;

import android.text.TextUtils;
import android.util.Log;

import com.ngo100.systemapp.app.App;
import com.ngo100.systemapp.user.User;
import com.ngo100.systemapp.util.JSONParser;
import com.ngo100.systemapp.util.LoadingDialogUtils;
import com.ngo100.systemapp.util.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Map;


/**
 * Created by donghui on 2017/5/25.网络请求工具类，基于xutil3.0
 */
public class HttpRequestUtil {
    public static final String URL = "";
    static HttpCallBack mHttpCallBack;
    public static String logType;
    /**
     * 发送请求
     *
     * @param method
     *            请求类型 Post或Get
     * @param url
     *            请求地址
     * @param map
     *            请求参数
     * @param mHttpCallBack
     *            回调接口
     */
    public static void send(String logType, HttpMethod method,
                            String url, Map<String, Object> map, HttpCallBack mHttpCallBack) {
        HttpRequestUtil.mHttpCallBack=mHttpCallBack;

        HttpRequestUtil.logType=logType;
        url = ConstantParser.HTTP_URI + url;
        Log.i("url", url);
        RequestParams params=parserMap(method,url, map);
        if (!TextUtils.isEmpty(User.getInstance().identifier)){
            params.addBodyParameter("identifier",User.getInstance().identifier);
            Log.i("params","key= identifier"  + " and value= "
                    +User.getInstance().identifier);
        }
        params.setConnectTimeout(8000);
        if (method== HttpMethod.POST){
            x.http().post(params,callback);
        }
        else if (method== HttpMethod.GET){
            x.http().get(params,callback);
        }

    }


    /**
     * 解析map集合 并封装到RequestParams对象
     *
     * @param map
     * @return
     */
    private static RequestParams parserMap(HttpMethod method, String url,
                                           Map<String, Object> map) {
        // 设置请求参数
        RequestParams params = new RequestParams(url);
        if (map == null || map.size() <= 0) {
            return params;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Log.i("params","key= " + entry.getKey() + " and value= "
                    + entry.getValue());
            if (entry.getValue() instanceof File) {
                params.addBodyParameter(entry.getKey(),
                        (File) entry.getValue());
            } else if (method== HttpMethod.POST) {
                params.addBodyParameter(entry.getKey(), entry.getValue() + "");
            } else if (method== HttpMethod.GET) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue()
                        + "");
            }
        }
        return params;
    }


    /**
     * xutil的网络请求回调
     */
    public static Callback.CommonCallback<String> callback=new Callback.CommonCallback<String>(){
        @Override
        public void onSuccess(String result) {
            mHttpCallBack.onSuccess(result);
            LoadingDialogUtils.closeLoadingDialog();
            Log.i(logType,result);
            String code= JSONParser.getStringFromJsonString("code",result);
            if ("101".equals(code)){
                Log.d("异地登录","101");
//                EventBus.getDefault().post(new LogoutEvent());//通知baseActivity弹出异地登录dialog
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            mHttpCallBack.onFailure(ex.toString()+"      isOnCallback:"+isOnCallback);
            ToastUtil.show(App.getInstance().getApplicationContext(),"网络请求失败");
            Log.e(logType,ex.toString());
            LoadingDialogUtils.closeLoadingDialog();
        }

        @Override
        public void onCancelled(CancelledException cex) {
            mHttpCallBack.onFailure(cex.toString()+  "请求取消");
            ToastUtil.show(App.getInstance().getApplicationContext(),"网络请求取消");
            LoadingDialogUtils.closeLoadingDialog();
        }

        @Override
        public void onFinished() {//成功或失败都会调用此方法
            Log.i("onFinished","请求结束");
            LoadingDialogUtils.closeLoadingDialog();
        }
    };


    /**
     * 上传图片（加请求头）
     */

    public static void upload(HttpMethod method,
                              String url, Map<String, Object> map, HttpCallBack mHttpCallBack) {
        HttpRequestUtil.mHttpCallBack=mHttpCallBack;

        url = ConstantParser.HTTP_URI + url;
        Log.i("url", url);
        RequestParams params=parserMap(method,url, map);
        params.setMultipart(true);
        params.addHeader("Content-Type", "multipart/form-data");
        if (method== HttpMethod.POST){
            x.http().post(params,callback);
        }
        else if (method== HttpMethod.GET){
            x.http().get(params,callback);
        }

    }


}
