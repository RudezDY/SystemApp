package com.grimos.push.bean;

import java.util.List;

/**
 * Created by homework on 2018/3/15.
 */

public class DownloadListObj {

    /**
     * status : 1
     * msg : 恭喜您，找到您的ID所对应的资源啦！
     * data : [{"name":"红米5Plus开发版","version":"vs2.1.45","info":[{"id":14,"resources_id":32,"url":"weiyun.fgg.ddd","channel":"微云网盘","pwd":"34567","time":"2018年03月14日"}]},{"name":"红米5Plus开发版","version":"vs2.1.4544","info":[{"id":13,"resources_id":32,"url":"www.baidu.wangpan/dddd","channel":"百度网盘","pwd":"dfedl","time":"2018年03月14日"}]}]
     */

    public int status;
    public String msg;
    public List<Data> data;

    public static class Data {
        /**
         * name : 红米5Plus开发版
         * version : vs2.1.45
         * info : [{"id":14,"resources_id":32,"url":"weiyun.fgg.ddd","channel":"微云网盘","pwd":"34567","time":"2018年03月14日"}]
         */

        public String name;
        public String version;
        public List<Info> info;

        public static class Info {
            /**
             * id : 14
             * resources_id : 32
             * url : weiyun.fgg.ddd
             * channel : 微云网盘
             * pwd : 34567
             * time : 2018年03月14日
             */

            public int id;
            public int resources_id;
            public String url;
            public String channel;
            public String pwd;
            public String time;
        }
    }
}
