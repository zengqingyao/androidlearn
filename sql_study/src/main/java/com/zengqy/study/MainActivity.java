package com.zengqy.study;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final String FIELD_USERNAME = "userName";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_AGE = "age";

    public static final int PERMISSION_REUQEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.zengqy.contentprovider/user");

        // 监听数据库的值
        contentResolver.registerContentObserver(uri, true, new ContentObserver(new Handler(Looper.myLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.e(TAG, "监听的用户数据发生了变化");
            }
        });

        // 动态申请日历的权限
        checkCalendarPermisson();
    }

    /**
     * 获取远程 contentProvider数据库的值
     */
    public void getRemoteProvide(View view) {
        Log.e(TAG, "getRemoteProvide:");
        ContentResolver contentResolver = getContentResolver();

        Uri uri = Uri.parse("content://com.zengqy.contentprovider/user");
//        Uri uri = Uri.parse("content://sobUserProvide");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        String[] columnNames = cursor.getColumnNames();

        //遍历
        while (cursor.moveToNext()) {
            Log.e(TAG, "========================");
            for (String columnName : columnNames) {
                String values = cursor.getString(cursor.getColumnIndex(columnName));
                Log.e(TAG, columnName + " = " + values);
            }
            Log.e(TAG, "========================");
        }
    }

    /**
     * 插入远程数据库的值
     */
    public void insertRemoteProvide(View view) {
        ContentResolver contentResolver = getContentResolver();

        Uri uri = Uri.parse("content://com.zengqy.contentprovider/user");

        ContentValues values = new ContentValues();
        values.put(FIELD_AGE, 99);
        values.put(FIELD_USERNAME, "qwer");
        values.put(FIELD_PASSWORD, "123456");

        contentResolver.insert(uri, values);

    }

    public void requestCalendar(View view) {
        Log.e(TAG, "requestCalendar: ");

        ContentResolver contentResolver = getContentResolver();

        Uri uri = Uri.parse("content://com.android.calendar/calendars");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        String[] columnNames = cursor.getColumnNames();

        while (cursor.moveToNext()) {
            Log.e(TAG, "===================");
            for (String columnName : columnNames) {
                String values = cursor.getString(cursor.getColumnIndex(columnName));
                Log.e(TAG, columnName+" = "+values );
            }
            Log.e(TAG, "===================");
        }
    }

    /**
     * 动态申请日历的读写权限
     */
    private void checkCalendarPermisson() {
        Log.e(TAG, "checkCalendarPermisson: ");
        int writePermission = checkSelfPermission(Manifest.permission.WRITE_CALENDAR);
        int readPermission = checkSelfPermission(Manifest.permission.READ_CALENDAR);

        if (writePermission == PackageManager.PERMISSION_GRANTED && readPermission == PackageManager.PERMISSION_GRANTED) {
            //表示有权限

        } else {
            Log.e(TAG, "requestPermisson: " ); 
            // 申请权限
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR},PERMISSION_REUQEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 权限申请结果
        if(requestCode == PERMISSION_REUQEST_CODE)
        {
            if(permissions.length == 2 &&
                    grantResults[0]== PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED )
            {
                // 权限申请通过
                Toast.makeText(this,"权限获取通过！",Toast.LENGTH_SHORT).show();
            }else{
                finish();
            }
        }
    }
}