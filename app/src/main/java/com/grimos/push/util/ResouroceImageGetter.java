package com.grimos.push.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;

import com.grimos.push.BuildConfig;

/**
 * Created by homework on 2018/3/19.
 */

public class ResouroceImageGetter implements Html.ImageGetter {
    Context context;

    public ResouroceImageGetter(Context context) {
        this.context = context;
    }

    // Constructor takes a Context
    public Drawable getDrawable(String source) {
        int path = context.getResources().getIdentifier(source, "drawable", BuildConfig.APPLICATION_ID);
        Drawable drawable = context.getResources().getDrawable(path);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }
}

