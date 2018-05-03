package com.grimos.push.service;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.grimos.push.activity.MainActivity;
import com.grimos.push.app.App;

/**
 * Created by homework on 2018/3/19.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BackService extends JobService {
    public static final int jobId = 3000;
    private static final String TAG = "BackService";

    public BackService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.i(TAG, "onStartJob");
        if (!App.getInstance().isCreat){
//            startActivity(new Intent(this, MainActivity.class));
            Intent intent=new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        jobFinished(params,false);

        // 返回true，表示该工作耗时，同时工作处理完成后需要调用onStopJob销毁（jobFinished）
        // 返回false，任务运行不需要很长时间，到return时已完成任务处理
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");

        // 有且仅有onStartJob返回值为true时，才会调用onStopJob来销毁job
        // 返回false来销毁这个工作
        return false;
    }


    public static void startScheduler(Context context) {
        JobInfo jobInfo = new JobInfo.Builder(jobId, new ComponentName(context, BackService.class))
                .setPeriodic(10000)//重复间隔(毫秒)
                .setPersisted(true)//开机自动重启
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)//任何网络可用
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .build();
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null){
            int schedule = jobScheduler.schedule(jobInfo);
            Log.i(TAG," == "+schedule);
        }

    }

    public static void startBackService(Context context) {
        Intent intent = new Intent(context,BackService.class);
        context.startService(intent);
        startScheduler(context);
    }


    public static void stop(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null){
            jobScheduler.cancelAll();
        }
        context.stopService(new Intent(context,BackService.class));

    }
}
