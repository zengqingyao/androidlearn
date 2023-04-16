package com.example.componentstudy.contentprovide.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.componentstudy.contentprovide.utils.Constants;

/**
 * @包名: com.zengqy.contentprovider.db
 * @USER: zengqy
 * @DATE: 2022/4/8 13:28
 * @描述:
 */
public class UserDatabaseHelper extends SQLiteOpenHelper {


    private static final String TAG = "CMP_UserDatabaseHelper";

    public UserDatabaseHelper(@Nullable Context context) {

        super(context, Constants.DB_NAME,null,Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        Log.e(TAG, "创建数据库: ");

        String sql = "create table "+
                Constants.TABLE_NAME+"("+
                Constants.FIELD_ID+" integer primary key autoincrement,"+
                Constants.FIELD_USERNAME+" varchar(30),"+
                Constants.FIELD_PASSWORD+" varchar(32),"+
                Constants.FIELD_AGE+" integer)";

        db.execSQL(sql);
    }

    /**
     * 需要更改version版本号才会调用这个
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
