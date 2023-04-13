package com.zengqy.customview.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zengqy.customview.R;


/**
 * @包名: com.zengqy.customview.customview
 * @USER: zengqy
 * @DATE: 2022/4/23 14:36
 * @描述: 自定义组合控件
 */
public class InputNumberView extends RelativeLayout {

    public static final String TAG = "CView_InputNumberView";

    private int mCurrentNumber = 0;

    private Button mMinusBtn;
    private TextView mValueTextView;
    private Button mPlusBtn;
    private int mMax;
    private int mMin;
    private int mStep;
    private int mDefaultValue;
    private boolean mDisable;
    private int mBtnBgRes;
    private OnNumberChangeListener mOnNumberChangeListener = null;


    public InputNumberView(Context context) {
        this(context, null);
    }

    public InputNumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 自定义组合控件的步骤
         * 1. InputNumberView 继承自一个 RelativeLayout 或者 LinearLayout
         *
         * 2. 获取相关属性
         *
         * 3. 把其他子view加载进来， 以下代码是一样的。
         *   1. View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this, false);
         *      addView(view);
         *
         *   2. View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this);
         *
         *
         * 4. 处理相关事件和数据
         *
         * */
        View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this, true);


        initViewAttrs(context, attrs, defStyleAttr);
        initView(context);

        setupEvent();
    }

    private void initViewAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InputNumberView, defStyleAttr, 0);
        mMax = a.getInt(R.styleable.InputNumberView_max, 0);
        mMin = a.getInt(R.styleable.InputNumberView_min, 0);
        mStep = a.getInt(R.styleable.InputNumberView_step, 1);
        mDefaultValue = a.getInt(R.styleable.InputNumberView_defaultValue, 0);
        this.mCurrentNumber = mDefaultValue;

        mDisable = a.getBoolean(R.styleable.InputNumberView_disable, false);

        // 设置 background ，知道怎么使用就行
//        mMinusBtn.setBackgroundResource(mBtnBgRes);
        mBtnBgRes = a.getResourceId(R.styleable.InputNumberView_btnBackground, -1);

        Log.e(TAG, "mMax--> " + mMax);
        Log.e(TAG, "mMin--> " + mMin);
        Log.e(TAG, "mStep--> " + mStep);
        Log.e(TAG, "mDefaultValue--> " + mDefaultValue);
        Log.e(TAG, "mDisable--> " + mDisable);
        Log.e(TAG, "mBtnBgRes--> " + mBtnBgRes);
        a.recycle();
    }


    private void initView(Context context) {
        mMinusBtn = this.findViewById(R.id.minus_btn);
        mValueTextView = this.findViewById(R.id.value_edt);
        mPlusBtn = this.findViewById(R.id.plus_btn);

        // 初始化控件的默认值
        this.updateText();
        mMinusBtn.setEnabled(!mDisable);

        mPlusBtn.setEnabled(!mDisable);

    }

    private void setupEvent() {
        mMinusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumber -= mStep;

                // 每 - 一次， +按键一定能 enable
                mPlusBtn.setEnabled(true);
                if (mMin != 0 && mCurrentNumber <= mMin) {
                    mCurrentNumber = mMin;
                    v.setEnabled(false);

                    if (mOnNumberChangeListener != null) {
                        mOnNumberChangeListener.onNumberMin(mMin);
                    }

                }
                updateText();
            }
        });


        mPlusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumber += mStep;

                // +的时候，-键就一定能再按
                mMinusBtn.setEnabled(true);
                if (mMax != 0 && mCurrentNumber >= mMax) {
                    mCurrentNumber = mMax;
                    v.setEnabled(false);

                    if (mOnNumberChangeListener != null) {
                        mOnNumberChangeListener.onNumberMax(mMax);
                    }

                }
                updateText();
            }
        });

    }

    private void updateText() {
        if (mValueTextView != null) {
            mValueTextView.setText(String.valueOf(mCurrentNumber));

            if (mOnNumberChangeListener != null) {
                mOnNumberChangeListener.onNumberChange(this.mCurrentNumber);
            }

        }
    }


    public int getNumber() {
        return mCurrentNumber;
    }

    public void setNumber(int Number) {
        this.mCurrentNumber = Number;
    }


    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public int getMin() {
        return mMin;
    }

    public void setMin(int min) {
        mMin = min;
    }

    public int getStep() {
        return mStep;
    }

    public void setStep(int step) {
        mStep = step;
    }

    public int getDefaultValue() {
        return mDefaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        mDefaultValue = defaultValue;
        this.mCurrentNumber = mDefaultValue;
        this.updateText();
    }

    public boolean isDisable() {
        return mDisable;
    }

    public void setDisable(boolean disable) {
        mDisable = disable;
    }

    public int getBtnBgRes() {
        return mBtnBgRes;
    }

    public void setBtnBgRes(int btnBgRes) {
        mBtnBgRes = btnBgRes;
    }


    public void setOnNumberChangeListener(OnNumberChangeListener listener) {
        this.mOnNumberChangeListener = listener;
    }

    public interface OnNumberChangeListener {

        // 当number改变的时候通知
        void onNumberChange(int number);

        // 最大值的时候通知
        void onNumberMax(int number);

        // 最小值的时候通知
        void onNumberMin(int number);

    }
}

