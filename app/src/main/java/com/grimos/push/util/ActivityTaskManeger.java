package com.grimos.push.util;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity任务栈
 *
 */
public class ActivityTaskManeger {
	
	private static ActivityTaskManeger taskManeger;
	private Stack<Activity> activityStack;

	private ActivityTaskManeger(){
		
	}
	
	public static ActivityTaskManeger getInstance(){
		if (taskManeger == null) {
			taskManeger = new ActivityTaskManeger();
		}
		return taskManeger;
	}
	
	/**
	 * 添加活动
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 移除activity
	 * @param activity
     */
	public void removeActivity(Activity activity) {
		if (activityStack != null) {
			activityStack.remove(activity);
		}
	}

	/**
	 * 关闭一个指定的Acitity
	 * @param activity
	 */
	public void closeActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}
	
	/**
	 * 关闭一个指定的Acitity
	 * @param cls
	 */
	public void closeActivity(Class<?> cls) {
		int i = 0;
		while (activityStack.size() > i) {
			Activity activity = activityStack.get(i);
			if (activity != null && activity.getClass().getName().equals(cls.getName())) {
				closeActivity(activity);
			}
			i++;
		}
	}
	
	/**
	 * 关闭最后一个Acitity
	 *
	 */
	public void closeActivity() {
		Activity activity = activityStack.lastElement();
		if (activity != null) {
			closeActivity(activity);
		}
	}

	
	/**
	 * 退出程序 关闭所以Acitity
	 */
	public void closeAllActivity() {
		while (activityStack.size() > 0) {
			Activity activity = activityStack.lastElement();
			if (activity == null) {
				break;
			}
			closeActivity(activity);
		}
	}
}
