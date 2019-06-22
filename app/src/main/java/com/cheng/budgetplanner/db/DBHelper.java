package com.cheng.budgetplanner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;



class DBHelper extends SQLiteOpenHelper {
    Context context;

    public DBHelper(Context context)
    {
        super(context, "budgetplannerdb", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.beginTransaction();
            db.execSQL(Tables.Bills.definition);
//            ContentValues values1 = new ContentValues();
//            values1.put(Tables.Bills.showalert, Integer.valueOf(1));
//            values1.put(Tables.Bills.tonalert, Integer.valueOf(1));
//            values1.put(Tables.Bills.timeout, Integer.valueOf(3));
//            values1.put(Tables.Bills.isactivated, Integer.valueOf(0));
//            values1.put(Tables.Bills.userId, Integer.valueOf(0));
//            values1.put(Tables.Bills.backgroundchecker, Integer.valueOf(1));
//            values1.put(Tables.Bills.backgroundemail, Integer.valueOf(0));
//            values1.put(Tables.Bills.backgroundNotification, Integer.valueOf(1));
//            values1.put(Tables.Bills.backchecktimespan, Integer.valueOf(2));
//            values1.put(Tables.Bills.autorun, Integer.valueOf(1));
//            db.insert(Tables.Bills.tableName, null, values1);
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        catch(Exception e)
        {
            db.endTransaction();
            e.printStackTrace();
            Toast.makeText(this.context, "Error : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
