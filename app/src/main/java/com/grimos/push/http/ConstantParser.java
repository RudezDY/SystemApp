package com.grimos.push.http;

/**
 * 网络请求常量类
 */
public class ConstantParser {
    // 接口请求前面部分
    public static final String HTTP_HANDER = "http://systemapp.laoshi888.com/";

    // 中间部分
    public static final String HTTP_CENTER = "System/";
    // 接口前面部分
    public static final String HTTP_URI = HTTP_HANDER + HTTP_CENTER;
    public static final String DNS = "http://systemapp.laoshi888.com/";

    //获取首页列表
    public static final String GETHOMELIST = "Resourceslist/resources_list";
    //资源详情
    public static final String EXPLORERDETAILS = "Resourceslist/resources_info";
    //充值
    public static final String RECHARGE = "Resourceslist/get_order_number";
    //获取下载列表
    public static final String GETDOWNLOADLIST = "Resourceslist/resources_sort";
    //解绑
    public static final String UNBIND = "Resourceslist/untie_model";
    //读消息
    public static final String READ = "Resourceslist/clear_not_read_number";


}