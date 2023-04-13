package com.example.componentstudy.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.componentstudy.R;
import com.example.componentstudy.pojo.User;

public class ParcelableActivity extends AppCompatActivity {

    private static final String TAG = "CMP";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable);

        mTextView = findViewById(R.id.parcelableTv);

        // 为什么Intent不能传递大数据？底层binder, 因为mmap映射的虚拟内存大小最大为1M-8K，不能超过这个数值。
        Intent intent = this.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("BUNDLE");
            String name = bundle.getString("NAME");
            User user = bundle.getParcelable("USER");

            mTextView.setText("名字:" + user.getName() + " 年龄:" + user.getAge());
            Toast.makeText(this, "额外传参:" + name, Toast.LENGTH_SHORT).show();
        }

        Intent resit = new Intent();
        resit.putExtra("NAME","曾庆耀");

        setResult(1,resit);

    }

    /**
     * 隐式意图打开浏览器
     *
     * @param view
     */
    public void startBrowserInvisible(View view) {
        Log.d(TAG, "startBrowserInvisible: ");

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse("http://www.baidu.com");
        intent.setData(content_url);

        startActivity(intent);
    }


    /**
     * 模拟打电话
     * @param view
     */
    public void startCall(View view) {
        Log.d(TAG, "startCall: ");

        Intent intent = new Intent();

        // <data android:scheme="tel"/>  限制格式为tel
        Uri callUri = Uri.parse("tel:10086");
        intent.setData(callUri);

        intent.setAction(Intent.ACTION_CALL);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        // 打电话需要运行时权限，申请
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            try {
                startActivity(intent);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }



        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the phone call
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    /**
     * 发送短信
     * @param view
     */
    public void startSendMsg(View view) {
        Log.d(TAG, "startSendMsg: ");

        //smsto 是
        Uri uri = Uri.parse("smsto:10086");
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Hello World！");

        startActivity(it);
    }


}