package com.cheng.budgetplanner.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;




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
//            ContentValues values1 = new ContentValues();;
//            values1.put(Tables.Bills.userId, Integer.valueOf(0));
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
