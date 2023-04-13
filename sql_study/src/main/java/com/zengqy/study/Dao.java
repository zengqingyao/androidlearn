package com.zengqy.study;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @包名: com.zengqy.study
 * @USER: zengqy
 * @DATE: 2022/4/6 19:55
 * @描述: 该类用于操作数据库的增删改查
 */
public class Dao {

    private static final String TAG = "SQL_Dao";

    private final DatebaseHelper mDatebaseHelper;

    public Dao(Context context) {
        mDatebaseHelper = new DatebaseHelper(context);
    }

    public void insert() {
        Log.e(TAG, "insert: ");
        SQLiteDatabase db = mDatebaseHelper.getWritableDatabase();

        // 1. SQL命令执行插入，防止SQL注入
        String sql = "insert into " + Constants.TABLE_NAME +
                "(_id,name,age,phone,address) values(?,?,?,?,?)";

        db.execSQL(sql, new Object[]{1, "Zengqy", 60, 13570, "USA"});

        //2. API 增删改查
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id",2);
        contentValues.put("name","LLCD");
        contentValues.put("age",18);
        contentValues.put("phone",1652);
        contentValues.put("address","CHN");
        db.insert(Constants.TABLE_NAME,null,contentValues);



        db.close();

    }

    public void delete() {

        SQLiteDatabase db = mDatebaseHelper.getWritableDatabase();

        // 防止SQL注入
        String sql = "delete from " + Constants.TABLE_NAME +" where age = 60";

        db.execSQL(sql);
        db.close();
    }

    public void update() {

        SQLiteDatabase db = mDatebaseHelper.getWritableDatabase();

        // 防止SQL注入
        String sql = "update " + Constants.TABLE_NAME +" set phone = 999 where age = 60";
        db.execSQL(sql);

        ContentValues contentValues = new ContentValues();
        contentValues.put("phone",412);

        // update test_table_name set phone = 412 where age=18
        db.update(Constants.TABLE_NAME,contentValues,"age=?",new String[]{"18"});

        db.close();
    }

    public void query() {

        SQLiteDatabase db = mDatebaseHelper.getWritableDatabase();

        // 防止SQL注入
        String sql = "select * from " + Constants.TABLE_NAME;

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex("name");
            String name = cursor.getString(index);
            Log.e(TAG, "name = "+name );
        }

        cursor.close();
        db.close();

    }
}
