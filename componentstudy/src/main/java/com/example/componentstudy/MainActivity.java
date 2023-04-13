package com.example.componentstudy;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.componentstudy.activitys.BroadcastActivity;
import com.example.componentstudy.activitys.ParcelableActivity;
import com.example.componentstudy.activitys.ServiceActivity;
import com.example.componentstudy.pojo.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CMP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startlearnActivity(View view) {
        Log.d(TAG, "startlearnActivity: ");

        Intent intent = new Intent(this, ParcelableActivity.class);

        User user = new User("曾庆耀", 30);

        Bundle bundle = new Bundle();
        bundle.putParcelable("USER", user);
        bundle.putString("NAME", "啥也不是");

        intent.putExtra("BUNDLE", bundle);

        startActivityForResult(intent, 1);

    }

    /**
     * 启动activity返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            Toast.makeText(this,"启动Activity学习的返回值: "+data.getStringExtra("NAME")
            ,Toast.LENGTH_SHORT).show();

        }
    }

    public void startlearnBroadcast(View view) {
        Log.d(TAG, "startlearnBroadcast: ");
        Intent intent = new Intent(this, BroadcastActivity.class);
        startActivity(intent);
    }

    public void startlearnService(View view) {
        Log.d(TAG, "startlearnService: ");
        Intent intent = new Intent(this, ServiceActivity.class);
        startActivity(intent);
    }

    public void startlearnContentProvide(View view) {
    }
}