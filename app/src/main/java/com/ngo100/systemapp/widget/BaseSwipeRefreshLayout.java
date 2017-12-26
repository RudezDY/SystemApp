package com.ngo100.systemapp.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by donghui on 2017/8/22.
 *   下拉刷新官方控件 重写两个方法解决与listview和viewpager的滑动冲突
 */

public class BaseSwipeRefreshLayout extends SwipeRefreshLayout {
    private View view;
    public BaseSwipeRefreshLayout(Context context) {
        super(context);
    }

    public BaseSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setViewGroup(View view) {
        this.view = view;
    }

    /**
     * ListView被嵌套，解决互动冲突
     */
    @Override
    public boolean canChildScrollUp() {
        if (view != null && view instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) view;
            return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
        }
        return super.canChildScrollUp();
    }



    private int mLastX;
    private int mLastY;

    /**
     * 解决和viewpager的滑动冲突问题
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                int a = x - mLastX;
                int b = y - mLastY;
                if(Math.abs(a) > Math.abs(b)){ //横向互动距离大于纵向时取消刷新效果
                    setEnabled(false);
                }else{
                    setEnabled(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                setEnabled(true);
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onInterceptTouchEvent(ev);
    }


    public interface OnRefresh{
        void refresh();
    }

}
