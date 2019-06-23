package com.cheng.budgetplanner.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityManagerUtils {
	
	/** foreground Activity */
	public static Activity mForegroundActivity = null;
	/** all Activity */
	public static final List<Activity> mActivities = new LinkedList<Activity>();

	
	/** stack top activity */
	public static Activity getCurrentActivity() {
		List<Activity> copy;
		synchronized (mActivities) {
			copy = new ArrayList<Activity>(mActivities);
		}
		if (copy.size() > 0) {
			return copy.get(copy.size() - 1);
		}
		return null;
	}


	public static boolean hasActivity() {
		return mActivities.size() > 0;
	}


	public static Activity getForegroundActivity() {
		return mForegroundActivity;
	}

	/** close all Activity，except except Activity */
	public static void finishAll(Class except) {
		List<Activity> copy;
		synchronized (mActivities) {
			copy = new ArrayList<Activity>(mActivities);
		}
		for (Activity activity : copy) {
			if (activity.getClass() != except)
				activity.finish();
		}
	}

	/** close all Activity */
	public static void finishAll() {
		List<Activity> copy;
		synchronized (mActivities) {
			copy = new ArrayList<Activity>(mActivities);
		}
		for (Activity activity : copy) {
			activity.finish();
		}
	}
	
	

}
