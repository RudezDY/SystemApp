package com.ngo100.systemapp.http;



import org.xutils.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Donghui 网络请求方法类
 */
public class HttpUtil {

    /**
     * 获取首页信息
     *
     * @param mode 机型
     * @param callBack
     */
    public static void getHomeList( String mode,
                                    HttpCallBack callBack) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mode", mode);
        HttpRequestUtil.send("首页",HttpMethod.POST, ConstantParser.GETHOMELIST,
                map, callBack);
    }

    /**
     * 获取资源详情
     * @param id
     * @param callBack
     */
    public static void getExplorerDetails( int id,
                                    HttpCallBack callBack) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        HttpRequestUtil.send("资源详情",HttpMethod.POST, ConstantParser.EXPLORERDETAILS,
                map, callBack);
    }

}













