package com.zengqy.customview.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.zengqy.customview.R;

import java.lang.reflect.Field;

/**
 * @包名: com.zengqy.customview.customview
 * @USER: zengqy
 * @DATE: 2022/5/12 17:22
 * @描述: 登陆界面的组合控件view
 * <p>
 * 1. 点击获取验证码 --> 条件：手机号码是否正确
 * 2. 点击登陆 --> 条件：正确的手机号码+验证码+同意了协议
 */
public class LoginPageView extends FrameLayout {

    public static final String TAG = "CView_LoginPageView";

    public static final int SIZE_VERIFY_CODE_DEFAULT = 4;


    // 手机号码的规则
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";


    private int mColor;

    // 校验码的长度
    private int mVerifyCodeSize = SIZE_VERIFY_CODE_DEFAULT;

    // 组合控件的监听器
    private OnLoginPageActionListener mActionListener = null;

    // 数字键盘的view
    private LoginKeyboardView mLoginKeyboardView = null;

    // 电话输入框的view
    private EditText mPhoneNumInput;

    // 校验码输入框的view
    private EditText mVerifyCodeInput;
    private TextView mGetVerifyCodeBtn;
    private CheckBox mIsAgreement;
    private Button mLoginBtn;


    // 电话号码是否正确，协议是否勾选，校验码是否正确
    private boolean isPhoneNumOk = false;
    private boolean isAgreementOk = false;
    private boolean isVerifyCodeOk = false;
    private boolean isCountDowning = false;


    // 默认获取验证码倒计时时间
    public static final int DURATION_DEFAULT = 60 * 1000;

    private int mCountDownDuration = DURATION_DEFAULT;
    private CountDownTimer mCountDownTimer;


    public LoginPageView(@NonNull Context context) {
        this(context, null);
    }

    public LoginPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化属性
        initAttrs(context, attrs, defStyleAttr);

        initView();

        initEvents();
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {

        // 获取登陆界面的属性值
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoginPageView, defStyleAttr, 0);
        mColor = a.getColor(R.styleable.LoginPageView_mainColor, -1);
        mVerifyCodeSize = a.getInt(R.styleable.LoginPageView_verifyCodeSize, SIZE_VERIFY_CODE_DEFAULT);
        mCountDownDuration = a.getInt(R.styleable.LoginPageView_countDownDuration, DURATION_DEFAULT);
        a.recycle();
    }


    /**
     * 初始化view
     */
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.login_page_view, this);

        mIsAgreement = this.findViewById(R.id.report_check_box);

        mPhoneNumInput = this.findViewById(R.id.phone_num_input_box);
        mVerifyCodeInput = this.findViewById(R.id.veriry_code_input_box);

        mGetVerifyCodeBtn = this.findViewById(R.id.get_verify_code_btn);

        if (mColor != -1) {
            Log.e(TAG, "View Attrs, mColor---> 0x" + Integer.toHexString(mColor));
            mIsAgreement.setTextColor(mColor);
        }

        Log.e(TAG, "View Attrs, verify code size is---> " + mVerifyCodeSize);
        // 设置 输入验证码 输入框的长度
        mVerifyCodeInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mVerifyCodeSize)});

        // 打开页面后，电话输入框请求焦点
        mPhoneNumInput.requestFocus();

        // 不再弹出软输入框
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPhoneNumInput.setShowSoftInputOnFocus(false);
            mVerifyCodeInput.setShowSoftInputOnFocus(false);
        }

        mLoginKeyboardView = this.findViewById(R.id.number_keyboard);

        mLoginBtn = this.findViewById(R.id.login_page_btn);

        // 验证码EditText 禁止复制粘贴
        disableCopyAndPaste(mVerifyCodeInput);

        // 第一次启动的时候，获取验证码和登陆按钮 状态为false
        this.updateAllBtnState();
    }

    /**
     * 初始化点击事件
     */
    private void initEvents() {

        // 数字键盘输入事件
        mLoginKeyboardView.setOnKeyPressListener(new LoginKeyboardView.onKeyPressListener() {
            @Override
            public void onNumberPress(int number) {
                // 数字被点击
                EditText focusEdt = getFocusEdt();
                if (focusEdt != null) {
                    Editable text = focusEdt.getText();
                    int index = focusEdt.getSelectionEnd();
//                    Log.e(TAG, "onNumberPress---> number-->" + number + " index:" + index);
                    text.insert(index, String.valueOf(number));
                }
            }

            @Override
            public void onBackPress() {
                EditText focusEdt = getFocusEdt();
                if (focusEdt != null) {
                    Editable text = focusEdt.getText();
                    int index = focusEdt.getSelectionEnd();
//                    Log.e(TAG, "onBackPress--> index ---> " + index);
                    if (index > 0) {
                        text.delete(index - 1, index);
                    }
                }
            }
        });

        // 获取验证码输入事件
        mGetVerifyCodeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //监听事件不为空
                if (mActionListener != null) {
                    // 拿到手机号码
                    String phoneNum = mPhoneNumInput.getText().toString().trim();
                    Log.e(TAG, "phoneNum is -->" + phoneNum);
                    mActionListener.onGetVerifyCodeClick(phoneNum);

                    // 开始倒计时
                    verifyCodeStartCountDown();
                } else {
                    throw new IllegalArgumentException("no listener to get verify code");
                }
            }
        });


        // 电话号码输入框监听
        mPhoneNumInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 变化的时候检测手机号码是否符合格式
                String phoneNum = s.toString();
                isPhoneNumOk = (phoneNum.length() == 11) && phoneNum.matches(REGEX_MOBILE_EXACT);
                updateAllBtnState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 验证码监听
        mVerifyCodeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 验证码是否符合格式
                isVerifyCodeOk = s.length() == mVerifyCodeSize;
                updateAllBtnState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 同意协议 监听
        mIsAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgreementOk = isChecked;
                updateAllBtnState();
                if (mActionListener != null) {
                    mActionListener.onOpenAgreementClick();
                }
            }
        });


        // 登陆按钮监听事件
        mLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 登陆
                if (mActionListener != null) {
                    mActionListener.onConfirmClick(getVerifyCode(), getPhoneNum());
                }
            }
        });

    }

    // 获取当前的电话号码
    private String getPhoneNum() {
        return mPhoneNumInput.getText().toString().trim();
    }

    // 获取验证码
    private String getVerifyCode() {
        return mVerifyCodeInput.getText().toString().trim();
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getVerifyCodeSize() {
        return mVerifyCodeSize;
    }

    public void setVerifyCodeSize(int verifyCodeSize) {
        mVerifyCodeSize = verifyCodeSize;
    }

    public int getCountDownDuration() {
        return mCountDownDuration;
    }

    public void setCountDownDuration(int countDownDuration) {
        mCountDownDuration = countDownDuration;
    }


    private void verifyCodeStartCountDown() {

        isCountDowning = true;
        mGetVerifyCodeBtn.setEnabled(false);

        // 实现倒计时
        mCountDownTimer = new CountDownTimer(mCountDownDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 通知UI更新
                int resSeconds = (int) (millisUntilFinished / 1000);
                mGetVerifyCodeBtn.setText(resSeconds + "秒");
            }

            @Override
            public void onFinish() {
                // 倒计时结束
                isCountDowning = false;
                mGetVerifyCodeBtn.setEnabled(true);
                mGetVerifyCodeBtn.setText("获取验证码");
                updateAllBtnState();
                mCountDownTimer = null;
            }
        }.start();

    }


    /**
     * 验证码错误
     */
    public void onVerifyCodeError() {
        Log.e(TAG, "登陆错误");

        // 1. 清空验证码输入框
        mVerifyCodeInput.getText().clear();

        // 2.停止倒计时
        if (isCountDowning && mCountDownTimer != null) {
            isCountDowning = false;
            mCountDownTimer.cancel();
            mCountDownTimer.onFinish(); // 调用本例实现的onFinsh函数
        }
    }



    /**
     * 更新所有btn的状态，如获取验证码按钮、登陆按钮
     */
    private void updateAllBtnState() {

        // 如果已经开始倒计时了，则不需要enable状态了
        if (!isCountDowning) {
            mGetVerifyCodeBtn.setEnabled(isPhoneNumOk);
        }
        mLoginBtn.setEnabled(isPhoneNumOk && isAgreementOk && isVerifyCodeOk);
    }


    /**
     * 获取当前有焦点的输入框
     * <p>
     * 使用注意判空
     *
     * @return
     */
    private EditText getFocusEdt() {
        View view = this.findFocus();
        if (view instanceof EditText) {
            return (EditText) view;
        }
        return null;
    }


    /**
     * 禁止EditText选中复制粘贴
     *
     * @param editText
     */
    @SuppressLint("ClickableViewAccessibility")
    public void disableCopyAndPaste(final EditText editText) {
        try {
            if (editText == null) {
                return;
            }

            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            editText.setLongClickable(false);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // setInsertionDisabled when user touches the view
                        setInsertionDisabled(editText);
                    }

                    return false;
                }
            });
            editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInsertionDisabled(EditText editText) {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(editText);

            // if this view supports insertion handles
            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

            // if this view supports selection handles
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setOnloginPageActionListener(OnLoginPageActionListener listener) {
        this.mActionListener = listener;
    }

    /**
     * 对外暴露的接口
     */
    public interface OnLoginPageActionListener {

        void onGetVerifyCodeClick(String phoneNum);

        void onOpenAgreementClick();

        void onConfirmClick(String verifyCode, String phoneNum);

    }


}
