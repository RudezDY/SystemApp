package com.ngo100.systemapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ngo100.systemapp.user.User;


/**
 * Created by donghui on 2017/8/7.
 */

public class BaseFragment extends Fragment {

    protected BaseActivity mActivity;
    protected User user;
    Intent startAtyIntent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=(BaseActivity) getActivity();
        user=User.getInstance();
    }


    protected void startAty(Class aty){
        if (startAtyIntent ==null)
            startAtyIntent =new Intent();
        startAtyIntent.setClass(mActivity,aty);
        mActivity.startActivity(startAtyIntent);
    }

    protected static String ojl(){
        return "123ssa";
    }

}
