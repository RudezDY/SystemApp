package com.grimos.push.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import org.xutils.common.util.LogUtil;

/**
 * Created by homework on 2018/3/15.
 */

public class AnimatorUtil {
    /**
     * 旋转
     * @param v
     * @param start
     * @param end
     */
    public static void  rotation(View v,int start,int end,int duration){
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", start, end); //旋转动画（0-360度）
        anim.setDuration(duration); //持续时间
        anim.setRepeatCount(0); //循环次数（-1 为无限循环）
        anim.setInterpolator(new OvershootInterpolator());
        anim.start();
    }

    /**
     * Y轴平移
     * @param v
     * @param start
     * @param end
     */
    public static void translatY(View v,int start,int end){
        ObjectAnimator.ofFloat(v,"translationY",start,end)
                .setDuration(500)
                .start();

    }

    /**
     * Y轴缩放
     * @param v
     * @param start
     * @param end
     */
    public static void scaleY(View v,int start,int end){
        ObjectAnimator anim =ObjectAnimator.ofFloat(v,"scaleY",start,end)
                .setDuration(500);
        anim.setInterpolator(new OvershootInterpolator());
        anim.start();

    }

    /**
     * Y轴折叠
     * @param v
     * @param start
     * @param end
     */
    public static void foldY(final View v, int start, int end){
        ValueAnimator valueAnimator=ValueAnimator.ofInt(start,end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int y= (int) valueAnimator.getAnimatedValue();
                LogUtil.i("y:"+y);
                ViewGroup.LayoutParams params=v.getLayoutParams();
                params.height=y;
                v.setLayoutParams(params);
            }
        });
        valueAnimator.setDuration(500);
        //5.为ValueAnimator设置目标对象并开始执行动画
        valueAnimator.setTarget(v);
        valueAnimator.start();
    }
}
