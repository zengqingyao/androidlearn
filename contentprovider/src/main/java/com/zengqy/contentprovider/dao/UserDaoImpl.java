package com.zengqy.contentprovider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.zengqy.contentprovider.db.UserDatabaseHelper;
import com.zengqy.contentprovider.pojo.User;
import com.zengqy.contentprovider.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @包名: com.zengqy.contentprovider.dao
 * @USER: zengqy
 * @DATE: 2022/4/8 13:47
 * @描述:
 */
public class UserDaoImpl implements IUserDao {

    private static final String TAG = "UserDaoImpl";
    private final UserDatabaseHelper mUserDatabaseHelper;

    public UserDaoImpl(Context context) {

        mUserDatabaseHelper = new UserDatabaseHelper(context);
    }

    @Override
    public long addUser(User user) {

        SQLiteDatabase db = mUserDatabaseHelper.getWritableDatabase();

        long res;

        // 开启事务
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(Constants.FIELD_USERNAME, user.getUserName());
            values.put(Constants.FIELD_PASSWORD, user.getUserPassowrd());
            values.put(Constants.FIELD_AGE, user.getAge());

            res = db.insert(Constants.TABLE_NAME, null, values);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException("添加用户出问题了");
        } finally {
            db.endTransaction();
            db.close();
        }

        return res;
    }

    @Override
    public int delUserById(int id) {

        SQLiteDatabase db = mUserDatabaseHelper.getWritableDatabase();

        db.delete(Constants.TABLE_NAME, Constants.FIELD_ID+"=?", new String[]{id + ""});

        db.close();
        return 0;
    }

    @Override
    public int updateUser(User user) {
        Log.e(TAG, "updateUser: "+user);

        SQLiteDatabase db = mUserDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        if (user.getUserName() != null) {
            values.put(Constants.FIELD_USERNAME, user.getUserName());
        }
        if (user.getUserPassowrd() != null) {
            values.put(Constants.FIELD_PASSWORD, user.getUserPassowrd());
        }
        if (user.getAge() >= 0) {
            values.put(Constants.FIELD_AGE, user.getAge());
        }

        int result = db.update(Constants.TABLE_NAME, values, Constants.FIELD_ID+"=?", new String[]{user.get_id() + ""});

        db.close();

        return result;
    }

    @Override
    public User getUserById(int id) {

        SQLiteDatabase db = mUserDatabaseHelper.getReadableDatabase();

        String sql = "select * from " + Constants.TABLE_NAME + " where " + Constants.FIELD_ID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{id + ""});

        User user = null;
        if (cursor.moveToNext()) {
            user = new User();

            int _id = cursor.getInt(cursor.getColumnIndex(Constants.FIELD_ID));
            String userName = cursor.getString(cursor.getColumnIndex(Constants.FIELD_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(Constants.FIELD_PASSWORD));
            int age = cursor.getInt(cursor.getColumnIndex(Constants.FIELD_AGE));

            user.set_id(_id);
            user.setAge(age);
            user.setUserName(userName);
            user.setUserPassowrd(password);
        }

        db.close();
        return user;
    }

    @Override
    public List<User> listAllUser() {

        SQLiteDatabase db = mUserDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, null, null, null, null, null, null, null);

        List<User> users = new ArrayList<>();

        while (cursor.moveToNext()) {

            User user = new User();

            int _id = cursor.getInt(cursor.getColumnIndex(Constants.FIELD_ID));
            String userName = cursor.getString(cursor.getColumnIndex(Constants.FIELD_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(Constants.FIELD_PASSWORD));
            int age = cursor.getInt(cursor.getColumnIndex(Constants.FIELD_AGE));

            user.set_id(_id);
            user.setAge(age);
            user.setUserName(userName);
            user.setUserPassowrd(password);

            users.add(user);
        }

        db.close();
        return users;
    }
}
