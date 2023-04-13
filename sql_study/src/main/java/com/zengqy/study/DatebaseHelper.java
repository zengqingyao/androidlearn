package com.zengqy.study;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class DatebaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatebaseHelper";
    private String sql;

    public DatebaseHelper(@Nullable Context context) {
        super(context, Constants.DATEBASE_NAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库时候的回调
        Log.e(TAG, "创建数据库..");

        // 创建表
        // create table table_name(_id integer, name varchar, age integer)
        String sql = "create table "+Constants.TABLE_NAME+
                "(_id integer, name varchar, age integer, phone integer, address varchar)";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级数据库时的回调
        Log.e(TAG, "升级数据库.." );

        String sql;
        switch (oldVersion)
        {
            case 1:
                sql = "alter table "+Constants.TABLE_NAME+ " add phone integer";
                db.execSQL(sql);
                sql = "alter table "+Constants.TABLE_NAME+ " add address varchar";
                db.execSQL(sql);
                break;
            case 2:
                sql = "alter table "+Constants.TABLE_NAME+" add address varchar";
                db.execSQL(sql);
                break;
            case 3:
                break;
        }


    }
}
