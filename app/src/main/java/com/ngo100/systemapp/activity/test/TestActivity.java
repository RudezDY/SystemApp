package com.ngo100.systemapp.activity.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.hyr0318.mediaselect_library.Constans.Constans;
import com.github.hyr0318.mediaselect_library.Constans.MediaType;
import com.github.hyr0318.mediaselect_library.ui.MediaSelectActivity;
import com.github.hyr0318.mediaselect_library.ui.Photo;
import com.meetic.marypopup.MaryPopup;
import com.ngo100.systemapp.R;
import com.ngo100.systemapp.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.design_bottom_sheet)
    LinearLayout design_bottom_sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        init();
    }

    BottomSheetBehavior behavior;
    private void init() {
        behavior = BottomSheetBehavior.from(design_bottom_sheet);
        //默认设置为隐藏
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        new Thread(){
            @Override
            public void run() {
                final Bitmap bitmap=getNetVideoBitmap("http://gslb.miaopai.com/stream/PPKlk0dTgnoXfN1QT0DmE6DRF0bwpHRHigY-TQ__.mp4?vend=miaopai&");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img.setImageBitmap(bitmap);
                    }
                });
            }
        }.start();

    }

    private void showBottomSheet(BottomSheetBehavior behavior) {
        if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            button.setText("hide_bottom_sheet");
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            button.setText("show_bottom_sheet");
        }
    }


    @OnClick({R.id.button,R.id.fragmentbutton,R.id.first_text,R.id.second_text,R.id.third_text})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                showBottomSheet(behavior);
                break;
            case R.id.fragmentbutton:
                View contentView=new View(this);
                MaryPopup.with(this)
                        .content(contentView)
                        .cancellable(true)
                        .backgroundColor(0x12000000)
                        .from(button)
                        .show();
//                hahaha();
                break;
            case R.id.first_text:
                ToastUtil.show("first_text");
                break;
            case R.id.second_text:
                ToastUtil.show("second_text");
                break;
            case R.id.third_text:
                ToastUtil.show("third_text");

                break;
        }
    }

    List<Photo> audioList;
    private void hahaha() {
        if (audioList==null)
            audioList=new ArrayList<>();
        MediaSelectActivity.openActivity(this, MediaType.AUDIO_SELECT_TYPE,6,audioList,250);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==250){
            if (null!=data){
                audioList=data.getParcelableArrayListExtra(Constans.RESULT_LIST);
                for(Photo audio:audioList){
                    Log.i("文件信息","地址:"+audio.getPath()+"    duration"+audio.getDuration());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     *  服务器返回url，通过url去获取视频的第一帧
     *  Android 原生给我们提供了一个MediaMetadataRetriever类
     *  提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     *  @param videoUrl
     *  @return
     */
    public Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
            String duration=retriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
            Log.i("duration",duration+"    "+formatTime(Long.valueOf(duration)));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    public String formatTime(long ms){
        SimpleDateFormat formatter=null;
        if (ms/1000/60/60>0)
            formatter = new SimpleDateFormat("HH:mm:ss");
        else
            formatter=new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms);
        return hms;
    }
}
