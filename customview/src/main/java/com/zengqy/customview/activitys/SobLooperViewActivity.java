package com.zengqy.customview.activitys;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zengqy.customview.R;
import com.zengqy.customview.customview.SobLooperView;
import com.zengqy.customview.utils.LooperItem;

import java.util.ArrayList;
import java.util.List;

public class SobLooperViewActivity extends AppCompatActivity {

    private List<LooperItem> mData;
    private SobLooperView mLooperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sob_looper_view);
        initTestData();
        initView();
    }

    private void initView() {
        mLooperView = this.findViewById(R.id.sob_looper);
        mLooperView.setData(new SobLooperView.InnerPageAdapter() {
            @Override
            public int getDataSize() {
                return mData.size();
            }

            @Override
            protected View getItemView(ViewGroup container, int itemPosition) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //设置图片
                imageView.setImageResource(mData.get(itemPosition).getImgRsId());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                return imageView;
            }
        },new SobLooperView.TitleBindListener() {
            @Override
            public String getTitle(int position) {
                return mData.get(position).getTitle();
            }
        });
    }

    private void initTestData() {
        mData = new ArrayList<>();
        mData.add(new LooperItem("图片1的标题",R.mipmap.pic1));
        mData.add(new LooperItem("图片2的标题",R.mipmap.pic2));
        mData.add(new LooperItem("图片3的标题",R.mipmap.pic3));
        mData.add(new LooperItem("图片4的标题",R.mipmap.pic4));
    }
}