package com.cheng.budgetplanner;

import android.app.Application;
import android.content.Context;

import com.cheng.budgetplanner.db.LocalDB;

public class MyApplication extends Application {

    public static MyApplication application;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
        LocalDB.getInstance().initDB(context);
    }
}
