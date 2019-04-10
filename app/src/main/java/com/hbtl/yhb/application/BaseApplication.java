package com.hbtl.yhb.application;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.hbtl.yhb.BuildConfig;
import com.hbtl.yhb.R;
import com.hbtl.yhb.configs.Config;


import java.util.Stack;

public class BaseApplication extends Application {
    private Stack<Activity> activityStack;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initServer();
    }

    private void initServer() {
        Config.server_domain = getResources().getString(BuildConfig.DEBUG ? R.string.DEFAULT_URL_TEST : R.string.DEFAULT_URL);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null && activityStack != null && !activityStack.isEmpty()) {
            activityStack.remove(activity);
        }
    }

    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    public void clearAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    public void exitApp() {
        clearAllActivity();
    }
}
