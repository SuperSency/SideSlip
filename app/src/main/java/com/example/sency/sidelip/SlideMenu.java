package com.example.sency.sidelip;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by sency on 2016/8/6.
 */
public class SlideMenu extends HorizontalScrollView {

    private LinearLayout mWapper;
    //菜单栏
    private ViewGroup mMenu;
    //内容
    private ViewGroup mContent;
    //屏幕宽度
    private int mScreenWidth;
    //menu与右边距离
    private int mMenuRightPadding = 50;

    private boolean once = false;

    //菜单的宽度
    private int mMenuWidth;

    private boolean isOpen;

    /**
     * 未使用自定义属性时调用
     *
     * @param context
     * @param attrs
     */
    public SlideMenu(Context context, AttributeSet attrs) {
        //不调用
        this(context, attrs, 0);
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        //给outMetrics赋值
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//
//        mScreenWidth = outMetrics.widthPixels;
//
//        //将50dp转化为一个像素值
//        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
    }

    /**
     * 设置子View的宽和高
     * 设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //是否为第一次调用
        if (!once) {
            //只有一个元素
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            //获取宽度
            mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mMenuWidth = mMenu.getLayoutParams().width;
            //Log.i("tag","mMenuWidth:"+mMenuWidth);
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量将Menu隐藏
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            //抬起时
            case MotionEvent.ACTION_UP:
                //获取移动时显示出来的菜单宽度
                int scrollX = getScrollX();
                Log.i("tag","scroll:"+scrollX);
                Log.i("tag","mMenuWidth / 2:"+mMenuWidth / 2);
                //如果隐藏的宽度大于菜单的二分之一,即显示小于隐藏,则让他滑动隐藏
                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 使用自定义属性时调用此构造方法
     * 第三个参数为定义样式
     */
    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //通过TypedArray类获取我们的自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlideMenu, defStyleAttr, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SlideMenu_rightPadding:
                    mMenuRightPadding = a.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension
                                    (TypedValue.COMPLEX_UNIT_DIP, 50,
                                            context.getResources().getDisplayMetrics())
                    );
                   // Log.i("tag","rightPadding:"+mMenuRightPadding);
                    break;
                default:
                    break;
            }
        }
        //释放
        a.recycle();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        //给outMetrics赋值
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mScreenWidth = outMetrics.widthPixels;

        //将50dp转化为一个像素值
//        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());

    }

    public SlideMenu(Context context) {
        this(context, null);
    }

    //打开菜单
    public void openMenu() {
        //if (isOpen) return;
        this.smoothScrollTo(mMenuWidth, 0);
        //Log.i("tag","Open");
        isOpen = false;
    }

    //关闭菜单
    public void closeMenu() {
       // if (!isOpen) return;
        this.smoothScrollTo(0, 0);
        //Log.i("tag", "Close");
        isOpen = true;
    }

    public void toggle() {
        //Log.i("tag","boolean:"+isOpen);
        if (!isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

}
