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

    /**
     * 充值
     * @param pay_type 支付方式 1 支付宝 。 2 微信支付
     * @param amount 金额
     * @param callBack
     */
    public static void recharge( int pay_type,String amount,
                                    HttpCallBack callBack) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pay_type", pay_type);
        map.put("amount", amount);
        HttpRequestUtil.send("充值",HttpMethod.POST, ConstantParser.RECHARGE,
                map, callBack);
    }

}













