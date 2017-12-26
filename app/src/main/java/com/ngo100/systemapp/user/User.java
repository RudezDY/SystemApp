package com.ngo100.systemapp.user;

/**
 * Created by donghui on 2017/8/10.
 */

public class User {
    static User user;
    public static User getInstance(){
        if (user==null)
            user=new User();
        return user;
    }

    /**
     * 当User类被回收之后，在baseActivity中恢复数据时需将保存的user对象还原到User类，不然单例将返回空user
     * @param user
     * @return
     */
    public User setUser(User user){
        this.user=null;
        this.user=user;
        return user;
    }
    public User cleanUser(){
        user=null;
        user=new User();
        return user;
    }

    public boolean isLogin;
    public String identifier;//设备识别码
    public String model;//机型
    public String firm;//厂商
}
