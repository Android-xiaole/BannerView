package com.renny.recyclerbanner.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.renny.recyclerbanner.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class BannerView extends RelativeLayout {

    private static final String TAG = "BannerView";

    private ViewPager mViewPager;

    private long VIEWPAGER_SWITCH_DURING = 3000;//轮播时间

    private int MSG_START_SCROLL = 100;//消息的名称

    private BannerAdapter mBannerAdapter;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_START_SCROLL) {
                if (mHandler != null) {
                    mHandler.removeMessages(MSG_START_SCROLL);
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    mHandler.sendEmptyMessageDelayed(MSG_START_SCROLL, VIEWPAGER_SWITCH_DURING);
                }
            }
        }
    };


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewPager = (ViewPager) findViewById(R.id.fragment_recommend_viewPager);
    }

    /**
     * @param dataList
     */
    public void show(List<String> dataList) {

        Log.i(TAG, "start show banner");

        if (dataList == null || dataList.size() == 0) {
            return;
        }

        if (mViewPager.getChildCount() > 0) {
            mViewPager.removeAllViews();
        }

        /**** 重要部分  ******/
        //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。
        mViewPager.setClipChildren(false);
        //父容器一定要设置这个，否则看不出效果

        mViewPager.setOffscreenPageLimit(5);

        mBannerAdapter = new BannerAdapter(dataList);
        mViewPager.setAdapter(mBannerAdapter);

        int currentItem = Integer.MAX_VALUE / 2;

        mViewPager.setCurrentItem(currentItem);

        //设置ViewPager切换效果，即实现画廊效果
        mViewPager.setPageTransformer(true, new ZoomPageTransformer());

        //图片数量大于1的时候，才进行自动轮播
        if (dataList.size() > 1) {
            startAutoScroll();
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopScroll();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                startAutoScroll();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 开始自动切换
     */
    public void startAutoScroll() {
        if (mHandler != null) {
            mHandler.removeMessages(MSG_START_SCROLL);
            mHandler.sendEmptyMessageDelayed(MSG_START_SCROLL, VIEWPAGER_SWITCH_DURING);
        }
    }

    /**
     * 停止自动切换
     */
    public void stopScroll() {
        if (mHandler != null) {
            mHandler.removeMessages(MSG_START_SCROLL);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAutoScroll();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopScroll();
    }

    /**
     * 数据清理
     */
    public void clear() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
