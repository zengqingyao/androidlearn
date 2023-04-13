package com.zengqy.customview.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zengqy.customview.App;
import com.zengqy.customview.R;
import com.zengqy.customview.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @包名: com.zengqy.customview.viewgroup
 * @USER: zengqy
 * @DATE: 2022/5/17 16:25
 * @描述: 自定义流式布局
 */
public class FlowLayout extends ViewGroup {

    public static final String TAG = "FlowLayout";

    // 属性，默认的最大行数
    public static final int DEFAULT_LINE = -1;

    // 属性，间距，需要转单位，目前是px适配
    public static final int DEFAULT_HORIZONTAL_MARGIN = SizeUtils.dip2px(App.getAppContext(), 5f);
    public static final int DEFAULT_VERTICAL_MARGIN = SizeUtils.dip2px(App.getAppContext(), 5f);
    public static final int DEFAULT_BORDOR_RADIUS = SizeUtils.dip2px(App.getAppContext(), 5f);
    public static final int DEFAULT_TEXT_MAX_LENGTH = -1;


    private int mMaxLines;

    // 单位为px像素，可以通过 SizeUtils.dip2px 把dp转为px
    private int mHorizontalMargin;
    private int mVerticalMargin;

    private int mTextMaxLength;
    private int mTextColor;
    private int mBorderColor;
    private float mBorderRadius;

    // 保存外面传进来的数据
    private List<String> mData = new ArrayList<>();

    // 搜索布局的行数，textView
    private List<List<View>> mLines = new ArrayList<>();

    // 监听事件
    private OnItemClicklistener mItemClicklistener = null;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化属性
        initAttrs(context, attrs, defStyleAttr);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout, defStyleAttr, 0);

        // maxLine
        mMaxLines = a.getInt(R.styleable.FlowLayout_maxline, DEFAULT_LINE);
        if (mMaxLines != DEFAULT_LINE && mMaxLines < 1) {
            throw new IllegalArgumentException("maxline can not less than 1");
        }

        // 水平和竖直的间距margin，单位px
        mHorizontalMargin = (int) a.getDimension(R.styleable.FlowLayout_itemHorizontalMargin, DEFAULT_HORIZONTAL_MARGIN);
        mVerticalMargin = (int) a.getDimension(R.styleable.FlowLayout_itemVerticalMargin, DEFAULT_VERTICAL_MARGIN);

        // 文本最大的长度
        mTextMaxLength = a.getInt(R.styleable.FlowLayout_textMaxLength, DEFAULT_TEXT_MAX_LENGTH);
        if (mTextMaxLength != DEFAULT_TEXT_MAX_LENGTH && mTextMaxLength < 1) {
            throw new IllegalArgumentException("textMaxLength can not less than 1");
        }

        // 字体和颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTextColor = a.getColor(R.styleable.FlowLayout_textColor, context.getColor(R.color.text_grey));
            mBorderColor = a.getColor(R.styleable.FlowLayout_borderColor, context.getColor(R.color.text_grey));
        }

        // 圆角的大小
        mBorderRadius = a.getDimension(R.styleable.FlowLayout_borderRadius, DEFAULT_BORDOR_RADIUS);

        Log.e(TAG, "属性: mMaxLines-->" + mMaxLines);
        Log.e(TAG, "属性: mHorizontalMargin-->" + mHorizontalMargin);
        Log.e(TAG, "属性: mVerticalMargin-->" + mVerticalMargin);
        Log.e(TAG, "属性: mTextMaxLength-->" + mTextMaxLength);
        Log.e(TAG, "属性: mTextColor-->" + mTextColor);
        Log.e(TAG, "属性: mBorderColor-->" + mBorderColor);
        Log.e(TAG, "属性: mBorderRadius-->" + mBorderRadius);

        a.recycle();
    }

    /**
     * 根据列表添加子view
     *
     * @param data
     */
    public void setTextList(List<String> data) {

        // 传进来的数据
        this.mData.clear();
        this.mData.addAll(data);

        // 根据数据创建子view，并且添加进来
        setUpChildren();
    }

    /**
     * 添加子view
     */
    private void setUpChildren() {

        // 清空原来的内容
        removeAllViews();

        // 添加子view
        for (String datum : mData) {

            // 加载 textView
            TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(
                    R.layout.item_flowlayout_textview, this, false);

            // 设置 输入框的最大长度
            if (mTextMaxLength != DEFAULT_TEXT_MAX_LENGTH) {
                textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mTextMaxLength)});
            }

            textView.setText(datum);

            //设置监听事件
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClicklistener != null) {
                        mItemClicklistener.onItemClickListener(v, ((TextView) v).getText().toString());
                    }
                }
            });

            //添加 view到 viewGroup
            addView(textView);
        }
    }

    /**
     * 判断view是否允许添加在这一行里面
     *
     * @param line            某一行已经允许添加的view集合
     * @param child           需要添加的view
     * @param parentWidthSize 限制的总宽度大小
     * @return
     */
    private boolean checkChildCanBeAdd(List<View> line, View child, int parentWidthSize) {

        int measuredWidth = child.getMeasuredWidth();

        // 开始的宽度加上 水平的边距
        int totalWidth = mHorizontalMargin + getPaddingLeft();

        for (View view : line) {
            //view 加上边距
            totalWidth += view.getMeasuredWidth() + mHorizontalMargin;
        }

        totalWidth += (measuredWidth + mHorizontalMargin + getPaddingLeft());

        // 如果超出限制宽度，则不可以添加，否则可以添加
        return totalWidth <= parentWidthSize;
    }


    /**
     * 测量子view
     * 参数来自父控件，本案例为LinearLayout，包含值和模式
     * <p>
     * int类型-->32位--> 最高2位为模式，低30位为值
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        /*
         *  测量模式mode有三种:
         *  1. UNSPECIFIED = 0
         *  父容器不对View有限制，要多大给多大，一般用于系统内部测量，再比如说，ScrollView，
         *  它的子View可以随意设置大小，无论多高，都能滚动显示，这个时候，size一般就没什么意义。
         *
         *  2. EXACTLY = 1073741824
         *  父容器已经检测出View所需要的精确大小，此时View的最终大小就是SpecSize所指定的值，
         *  对应于LayoutParams的match_parent 和 具体数值这两种形式。

         *  3.AT_MOST = -2147483648
         *  子View的最终大小是父容器指定的SpecSize值，子View不能超过这个值，具体是什么值，
         *  还是要看View自身，对应了LayoutParams的wrap_content属性。
         */
        int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG, "onMeasure mode--> " + parentWidthMode);
        Log.e(TAG, "onMeasure parentWidthSize--> " + parentWidthSize);
        Log.e(TAG, "onMeasure parentWidthSize--> " + parentHeightSize);


        int childCount = getChildCount();
        if (childCount == 0)
            return;


        // 先清空
        mLines.clear();

        // 添加默认行
        List<View> line = new ArrayList<>();
        mLines.add(line);

        // 宽度和高度都使用 wrap_content
        // 子view的measuredWidth需要在父控件减去两边的margin
        int childWidthSpace = MeasureSpec.makeMeasureSpec(parentWidthSize - 2 * mHorizontalMargin, MeasureSpec.AT_MOST);
        int childHeightSpace = MeasureSpec.makeMeasureSpec(parentHeightSize - 2 * mVerticalMargin, MeasureSpec.AT_MOST);

        for (int i = 0; i < childCount; i++) {

            //获取子view
            View child = getChildAt(i);
            if (child.getVisibility() != VISIBLE) {
                continue;
            }

            //测量子view
            measureChild(child, childWidthSpace, childHeightSpace);

            // 每一行的第一个view总是可以被允许
            if (line.size() == 0) {
                line.add(child);
            } else {

                boolean canBeAdd = checkChildCanBeAdd(line, child, parentWidthSize);

                // 如果不被允许添加，需要换行，创建新的line
                if (!canBeAdd) {
                    if (mMaxLines != DEFAULT_LINE && mLines.size() >= mMaxLines) {
                        break;
                    }
                    line = new ArrayList<>();
                    mLines.add(line);
                }
                line.add(child);
            }
        }

        // 根据尺寸计算高度
        View child = getChildAt(0);
        int childdHeight = child.getMeasuredHeight();

        // view的高度加上 上下边距值，view有3行的情况下，边距值为4
        int parentHeightTargetSize = childdHeight * mLines.size()
                + (mLines.size() + 1) * mVerticalMargin
                + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(parentWidthSize, parentHeightTargetSize);

    }

    /**
     * 布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        // 第一个子view
        View firstChildView = getChildAt(0);

        // 需要校准 padding值，左右坐标都需要加上PaddingLeft的值
        int currentLeft = mHorizontalMargin + getPaddingLeft();
        int currentRight = getPaddingLeft();

        // 需要校准 padding值，上下坐标大小都需要加上PaddingTop的值
        int currentTop = mVerticalMargin + getPaddingTop();
        int currentBottom = firstChildView.getMeasuredHeight() + mVerticalMargin + getPaddingTop();

        for (List<View> line : mLines) {
            for (View view : line) {
                //布局每一行
                int measuredWidth = view.getMeasuredWidth();

                // 计算textView宽度
                currentRight += (measuredWidth + mHorizontalMargin);

                // 判断右边界有没有出界
                if (currentRight > getMeasuredWidth() - mHorizontalMargin) {
                    currentRight = getMeasuredWidth() - mHorizontalMargin;
                }

                view.layout(currentLeft, currentTop, currentRight, currentBottom);

                //布局完某一个view后，重新设置下一个view的left坐标
                currentLeft = currentRight + mHorizontalMargin;
            }

            // 布局完某一行后重新设置
            currentLeft = mHorizontalMargin + getPaddingLeft();
            currentRight = getPaddingLeft();
            currentBottom += firstChildView.getMeasuredHeight() + mVerticalMargin;
            currentTop += firstChildView.getMeasuredHeight() + mVerticalMargin;
        }
    }

    public void setOnItemClicklistener(OnItemClicklistener listener) {
        this.mItemClicklistener = listener;
    }


    public interface OnItemClicklistener {
        void onItemClickListener(View v, String text);
    }


    public int getMaxLines() {
        return mMaxLines;
    }

    public void setMaxLines(int maxLines) {
        mMaxLines = maxLines;
    }

    public int getHorizontalMargin() {
        return mHorizontalMargin;
    }

    public void setHorizontalMargin(int horizontalMargin) {
        mHorizontalMargin = SizeUtils.dip2px(App.getAppContext(), horizontalMargin);
    }

    public int getVerticalMargin() {
        return mVerticalMargin;
    }

    public void setVerticalMargin(int verticalMargin) {
        mVerticalMargin = SizeUtils.dip2px(App.getAppContext(), verticalMargin);
    }

    public int getTextMaxLength() {
        return mTextMaxLength;
    }

    public void setTextMaxLength(int textMaxLength) {
        mTextMaxLength = textMaxLength;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int borderColor) {
        mBorderColor = borderColor;
    }

    public float getBorderRadius() {
        return mBorderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        mBorderRadius = borderRadius;
    }
}
