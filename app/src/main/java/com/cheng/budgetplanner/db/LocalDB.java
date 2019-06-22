package com.cheng.budgetplanner.db;

import android.content.Context;

public class LocalDB {

    private static volatile LocalDB sInstance;

    private DBOperation dbopr;


    public static LocalDB getInstance() {
        if (sInstance == null) {
            synchronized (LocalDB.class) {
                if (sInstance == null) {
                    sInstance = new LocalDB();
                }
            }
        }
        return sInstance;
    }

    public boolean initDB(Context context)
    {
        if(null == dbopr){
            dbopr = new DBOperation(context);
            return true;
        }

        return false;
    }

    public DBOperation getDBOperation()
    {
        return dbopr;
    }

    public LocalDB()
    {
        dbopr = null;
    }
}
