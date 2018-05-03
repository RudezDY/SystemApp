package com.grimos.push.util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by donghui on 2017/11/21.
 */

public class TextUtil{
    /**
     *
     * SpannableStringBuilder和SpannableString主要通过使用setSpan(Object what, int start, int end, int flags)改变文本样式。
     对应的参数：

     start： 指定Span的开始位置
     end： 指定Span的结束位置，并不包括这个位置。
     flags：取值有如下四个
     Spannable. SPAN_INCLUSIVE_EXCLUSIVE：前面包括，后面不包括，即在文本前插入新的文本会应用该样式，而在文本后插入新文本不会应用该样式
     Spannable. SPAN_INCLUSIVE_INCLUSIVE：前面包括，后面包括，即在文本前插入新的文本会应用该样式，而在文本后插入新文本也会应用该样式
     Spannable. SPAN_EXCLUSIVE_EXCLUSIVE：前面不包括，后面不包括
     Spannable. SPAN_EXCLUSIVE_INCLUSIVE：前面不包括，后面包括
     what： 对应的各种Span，不同的Span对应不同的样式。已知的可用类有：
     BackgroundColorSpan: 文本背景色
     ForegroundColorSpan: 文本颜色
     MaskFilterSpan: 修饰效果，如模糊(BlurMaskFilter)浮雕
     RasterizerSpan: 光栅效果
     StrikethroughSpan: 删除线
     SuggestionSpan: 相当于占位符
     UnderlineSpan: 下划线
     AbsoluteSizeSpan: 文本字体（绝对大小）
     DynamicDrawableSpan: 设置图片，基于文本基线或底部对齐。
     ImageSpan: 图片
     RelativeSizeSpan: 相对大小（文本字体）
     ScaleXSpan: 基于x轴缩放
     StyleSpan: 字体样式：粗体、斜体等
     SubscriptSpan: 下标（数学公式会用到）
     SuperscriptSpan: 上标（数学公式会用到）
     TextAppearanceSpan: 文本外貌（包括字体、大小、样式和颜色）
     TypefaceSpan: 文本字体
     URLSpan: 文本超链接
     ClickableSpan: 点击事件
     */

    /**
     * 设置部分字体颜色
     * @param tv TextView
     * @param str 文字
     * @param start 开始位置
     * @param end 结束为止
     * @param color 颜色（16进制）
     * @return
     */
    public TextUtil setTextColor(TextView tv, String str, int start, int end, String color){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
        return this;
    }


    public TextUtil setTextBackgroundColor(TextView tv, String str, int start, int end, String color){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.parseColor(color));
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
        return this;
    }


    public TextUtil setTextSize(TextView tv, String str, int start, int end, int size){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(size);
        spannableString.setSpan(absoluteSizeSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
        return this;
    }

    /**
     *
     * @param tv TextView
     * @param str 文字
     * @param start 开始位置
     * @param end 结束为止
     * @param style (Typeface.BOLD);//粗体
     *              (Typeface.ITALIC);//斜体
     *              (Typeface.BOLD_ITALIC);//粗斜体
     * @return
     */
    public TextUtil setTextStyle(TextView tv, String str, int start, int end, int style){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        StyleSpan styleSpan=new StyleSpan(style);
        spannableString.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
        return this;
    }



    public TextUtil setStrikethrough(TextView tv, String str, int start, int end){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
        return this;
    }


    public TextUtil setUnderline(TextView tv, String str, int start, int end){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
        return this;
    }


    public TextUtil setSpanClick(TextView tv, String str, int start, int end, final OnSpanClickListener onSpanClickListener ){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                onSpanClickListener.onSpanClick();
            }
        };
        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }

    public TextUtil setSpanClick(final TextView tv, String str, int start, int end, final View.OnClickListener onClickListener ){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        };
        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }


    public TextUtil insertImg(TextView tv, String str, int index, ImageSpan imageSpan ) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        //在index插入图片
        spannableString.setSpan(imageSpan, index,index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
        return this;
    }


    public TextUtil insertImgClick(TextView tv, String str, int start, int end, ImageSpan imageSpan, final OnSpanClickListener onSpanClickListener ) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(str);
        //在index插入图片
        spannableString.setSpan(imageSpan, start,end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                onSpanClickListener.onSpanClick();
            }
        };
        spannableString.setSpan(clickableSpan,start,end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }






   public interface OnSpanClickListener{
        void onSpanClick();
    }
}
