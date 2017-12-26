package com.ngo100.systemapp.bean;

import java.util.List;

/**
 * Created by homework on 2017/12/26.
 */

public class HomeObj {

    /**
     * status : 1
     * msg : 对不起，您还不是VIP
     * vip : 0
     * data : {"link":{"order_link":"http://systemapp.laoshi888.com/System/Resourcesapp/order/serial_number/866963022748422.html","notice_link":"http://systemapp.laoshi888.com/System/Resourcesapp/notice/serial_number/866963022748422.html"},"list":[{"id":1,"name":"汤姆猫跑酷-会说话1","introduction":"探索无穷无尽的跑酷世界","upload_time":"2017-12-15 14:28:02","version":"v2.0.1","packname":""},{"id":26,"name":"111","introduction":"111","upload_time":"2017-12-19 19:12:01","version":"111","packname":""}]}
     */

    public int status;
    public String msg;
    public int vip;
    public HomeData data;

    public static class HomeData {
        /**
         * link : {"order_link":"http://systemapp.laoshi888.com/System/Resourcesapp/order/serial_number/866963022748422.html","notice_link":"http://systemapp.laoshi888.com/System/Resourcesapp/notice/serial_number/866963022748422.html"}
         * list : [{"id":1,"name":"汤姆猫跑酷-会说话1","introduction":"探索无穷无尽的跑酷世界","upload_time":"2017-12-15 14:28:02","version":"v2.0.1","packname":""},{"id":26,"name":"111","introduction":"111","upload_time":"2017-12-19 19:12:01","version":"111","packname":""}]
         */

        public LinkBean link;
        public List<AppsList> list;

        public static class LinkBean {
            /**
             * order_link : http://systemapp.laoshi888.com/System/Resourcesapp/order/serial_number/866963022748422.html
             * notice_link : http://systemapp.laoshi888.com/System/Resourcesapp/notice/serial_number/866963022748422.html
             */

            public String order_link;
            public String notice_link;
        }

        public static class AppsList {
            /**
             * id : 1
             * name : 汤姆猫跑酷-会说话1
             * introduction : 探索无穷无尽的跑酷世界
             * upload_time : 2017-12-15 14:28:02
             * version : v2.0.1
             * packname :
             */

            public int id;
            public String name;
            public String introduction;
            public String upload_time;
            public String version;
            public String packname;
        }
    }
}
