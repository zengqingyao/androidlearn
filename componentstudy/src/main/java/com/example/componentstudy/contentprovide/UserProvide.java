package com.example.componentstudy.contentprovide;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.componentstudy.contentprovide.db.UserDatabaseHelper;
import com.example.componentstudy.contentprovide.utils.Constants;


/**
 * @包名: com.zengqy.contentprovider
 * @USER: zengqy
 * @DATE: 2022/4/8 19:53
 * @描述:
 */
public class UserProvide extends ContentProvider {

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int MATCH_CODE = 1;
    private static final String TAG = "CMP_UserProvide";

    static {
        // 申请的时候为 Uri.parse("content://com.zengqy.contentprovider/user")
        // path 通常为表的名字
        sURIMatcher.addURI("com.example.componentstudy.contentprovider", "user",MATCH_CODE );
        sURIMatcher.addURI("sobUserProvide", null,MATCH_CODE );
    }

    private UserDatabaseHelper mUserDatabaseHelper;

    @Override
    public boolean onCreate() {

        mUserDatabaseHelper = new UserDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e(TAG, "query: "+uri );
        int result = sURIMatcher.match(uri);
        if(result == MATCH_CODE){
            // uri 匹配
            SQLiteDatabase db = mUserDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(Constants.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        }else{
            throw new IllegalArgumentException("参数错误");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e(TAG, "insert: "+values);
        int result = sURIMatcher.match(uri);
        if(result == MATCH_CODE){
            SQLiteDatabase db = mUserDatabaseHelper.getWritableDatabase();
            long id = db.insert(Constants.TABLE_NAME, null, values);

            Uri resultUri = Uri.parse("content://com.example.componentstudy.contentprovider/user/"+id);
            getContext().getContentResolver().notifyChange(resultUri,null);
            return resultUri;

        }else{
            throw new IllegalArgumentException("参数错误");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
