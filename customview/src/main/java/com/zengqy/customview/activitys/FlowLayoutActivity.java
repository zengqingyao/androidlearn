package com.zengqy.customview.activitys;

import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zengqy.customview.R;
import com.zengqy.customview.viewgroup.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class FlowLayoutActivity extends AppCompatActivity {

    public static final String TAG = "FlowLayoutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        FlowLayout flowLayout = this.findViewById(R.id.flow_layout);

        List<String> data = new ArrayList<>();
        data.add("键盘");
        data.add("显示器");
        data.add("鼠标");
        data.add("iPad");
        data.add("Air pod");
        data.add("Android手机");
        data.add("Mac pro");
        data.add("耳机");
        data.add("手机");
        data.add("鞋子大");
        data.add("春夏秋冬超帅装春夏秋冬超帅装春夏秋冬超帅装春夏秋冬超帅装春夏秋冬超帅装春夏秋冬超帅装春夏秋冬超帅装春夏秋冬超帅装");
        data.add("男鞋");
        data.add("女装");
        data.add("我是曾庆耀");
        data.add("你好啊我在这里");
        data.add("工管系");

        flowLayout.setTextList(data);
        flowLayout.setOnItemClicklistener(new FlowLayout.OnItemClicklistener() {
            @Override
            public void onItemClickListener(View v, String text) {
                Log.e(TAG, "点击Item-->" + text);
            }
        });
    }


}