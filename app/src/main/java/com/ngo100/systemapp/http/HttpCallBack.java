package com.ngo100.systemapp.http;

/**
 * 
 * @date 2015-8-10 网络请求返回结果 回调接口
 */
public interface HttpCallBack {

	void onSuccess(String result);

	void onFailure(String error);
}
