package com.cheng.budgetplanner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.cheng.budgetplanner.bean.BillBean;
import com.cheng.budgetplanner.utils.DateUtils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;



public class DBOperation {
    Context context;
    SQLiteDatabase db;
    DBHelper dbhelper;

    public DBOperation(Context context)
    {
        this.context = context;
        this.dbhelper = new DBHelper(context);
        this.db = this.dbhelper.getWritableDatabase();
    }



    public void closeDB()
    {
        this.db.close();
    }



    public ArrayList<BillBean> getAllBills()
    {
        ArrayList<BillBean> list = new ArrayList();
        try
        {
            Cursor cur = this.db.rawQuery("select * from Bills", null);
            if(cur.getCount() != 0)
            {
                while (cur.moveToNext())
                {
                    long _id = cur.getLong(0);
                    int id = cur.getInt(1);
                    float cost = cur.getFloat(2);
                    String content = cur.getString(3);
                    int userid = cur.getInt(4);
                    String payName = cur.getString(5);
                    String payImg = cur.getString(6);
                    int payid = cur.getInt(7);
                    int sortid = cur.getInt(8);
                    long crdate = cur.getLong(9);
                    boolean income = (cur.getInt(10) == 0)? false: true;
                    String sortName = cur.getString(11);
                    String sortImg = cur.getString(12);


                    BillBean Bills = new BillBean(_id, id,  cost, content, userid, payid, sortid, crdate, income, payName, payImg, sortName, sortImg);
                    list.add(Bills);
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this.context, "Error : " + e, Toast.LENGTH_LONG).show();
        }
        return list;
    }


    public ArrayList<BillBean> getAllBills(int year, int month)
    {
        String startDate = year + "-" + month + "-01 00:00:00";
        String endDate;
        if(12 != month)
        {
            endDate = year + "-" + (month+1) + "-01 00:00:00";
        }
        else
        {
            endDate = (year+1) + "-01-01 00:00:00";
        }

        long startMillis = DateUtils.getMillis(startDate);
        long endMillis = DateUtils.getMillis(endDate);

        ArrayList<BillBean> list = new ArrayList();
        try
        {
            Cursor cur = this.db.rawQuery("select * from Bills where crdate>=" + startMillis + " and crdate<" + endMillis, null);
            if(cur.getCount() != 0)
            {
                while (cur.moveToNext())
                {
                    long _id = cur.getLong(0);
                    int id = cur.getInt(1);
                    float cost = cur.getFloat(2);
                    String content = cur.getString(3);
                    int userid = cur.getInt(4);
                    String payName = cur.getString(5);
                    String payImg = cur.getString(6);
                    int payid = cur.getInt(7);
                    int sortid = cur.getInt(8);
                    long crdate = cur.getLong(9);
                    boolean income = (cur.getInt(10) == 0)? false: true;
                    String sortName = cur.getString(11);
                    String sortImg = cur.getString(12);


                    BillBean Bills = new BillBean(_id, id,  cost, content, userid, payid, sortid, crdate, income, payName, payImg, sortName, sortImg);
                    list.add(Bills);
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this.context, "Error : " + e, Toast.LENGTH_LONG).show();
        }
        return list;
    }


    public long addBills(ArrayList<BillBean> list)
    {
        try
        {
            this.db.beginTransaction();
            Iterator<BillBean> iterator = list.iterator();
            while(iterator.hasNext())
            {
                BillBean aBill = (BillBean) iterator.next();
                ContentValues values1 = new ContentValues();
                values1.put(Tables.Bills.id, aBill.getId());
                values1.put(Tables.Bills.cost, aBill.getCost());
                values1.put(Tables.Bills.content, aBill.getContent());
                values1.put(Tables.Bills.userid, aBill.getUserid());
                values1.put(Tables.Bills.payName, aBill.getPayName());
                values1.put(Tables.Bills.payImg, aBill.getPayImg());
                values1.put(Tables.Bills.payid, aBill.getPayid());
                values1.put(Tables.Bills.sortid, aBill.getSortid());
                values1.put(Tables.Bills.crdate, aBill.getCrdate());
                values1.put(Tables.Bills.income, (aBill.isIncome()? 1: 0));
                values1.put(Tables.Bills.sortName, aBill.getSortName());
                values1.put(Tables.Bills.sortImg, aBill.getSortImg());


                if (this.db.insert(Tables.Bills.tableName, null, values1) == -1)
                {
                    this.db.endTransaction();
                    return -1;
                }
            }
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
            return 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }



    public boolean deleteBill(long _id)
    {
        this.db.beginTransaction();
        int n = this.db.delete(Tables.Bills.tableName, "_id=" + _id, null);
        this.db.setTransactionSuccessful();
        this.db.endTransaction();
        if (n > 0)
        {
            return true;
        }
        return false;
    }

    public SQLiteDatabase getDB()
    {
        return this.db;
    }


    public void updateBill(long _id, float cost, String content, int userid, int sortid, String sortName, String sortImg, int payid, String payName, String payImg, long crdate, boolean income)
    {
        try
        {
            this.db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(Tables.Bills.cost, cost);
            values.put(Tables.Bills.content, content);
            values.put(Tables.Bills.userid, userid);
            values.put(Tables.Bills.sortid, sortid);
            values.put(Tables.Bills.sortName, sortName);
            values.put(Tables.Bills.sortImg, sortImg);
            values.put(Tables.Bills.payid, payid);
            values.put(Tables.Bills.payName, payName);
            values.put(Tables.Bills.payImg, payImg);
            values.put(Tables.Bills.crdate, crdate);
            values.put(Tables.Bills.income, (income? 1: 0));



            this.db.update(Tables.Bills.tableName, values, "_id=" + _id, null);
            Log.d("values", values.toString());

            this.db.setTransactionSuccessful();
            this.db.endTransaction();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this.context, "Error : " + e, Toast.LENGTH_LONG).show();
            this.db.endTransaction();
        }
    }
}
