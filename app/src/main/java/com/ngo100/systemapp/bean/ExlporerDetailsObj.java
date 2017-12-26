package com.ngo100.systemapp.bean;

/**
 * Created by homework on 2017/12/26.
 */

public class ExlporerDetailsObj {

    /**
     * status : 1
     * msg : 恭喜您，找到您的ID所对应的资源啦！
     * data : {"id":26,"mode":"Samsung galaxy c9 pro","name":"111","introduction":"111","description":"111","download_url":"111","version":"111","packname":"","upload_time":"2017-12-19 19:12:01","developer":"111"}
     * vip : 0
     */

    public int status;
    public String msg;
    public ExplorerDetails data;
    public int vip;

    public static class ExplorerDetails {
        /**
         * id : 26
         * mode : Samsung galaxy c9 pro
         * name : 111
         * introduction : 111
         * description : 111
         * download_url : 111
         * version : 111
         * packname :
         * upload_time : 2017-12-19 19:12:01
         * developer : 111
         */

        public int id;
        public String mode;
        public String name;
        public String introduction;
        public String description;
        public String download_url;
        public String version;
        public String packname;
        public String upload_time;
        public String developer;
    }
}
