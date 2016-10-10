package com.example.xzy.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import utils.PrefUtils;

/**
 * Created by XZY on 2016/10/10.
 */

public class GuideActivity extends Activity {

    private ViewPager mViewPager;
    private ArrayList<ImageView> mImageViewList;

    private int[] mImageIds = new int[]{
            R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private LinearLayout llContainer;
    private ImageView ivRedPoint;
    //    小红点移动距离
    private int mPointDis;
    private Button btStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        ivRedPoint = (ImageView) findViewById(R.id.iv_red_point);
        btStart = (Button) findViewById(R.id.bt_start);

        initData();
        mViewPager.setAdapter(new GuideAdapter());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中的回调
            //参数：当前所在页面（0,1,2,3····），偏移百分比，偏移像素
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //更新小红点当前的左边距
                int leftMargin = (int) (mPointDis * positionOffset) + position * mPointDis;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                params.leftMargin = leftMargin;
                ivRedPoint.setLayoutParams(params);
            }

            //某个页面被选中
            @Override
            public void onPageSelected(int position) {
                if (position == mImageViewList.size() - 1) {
                    btStart.setVisibility(View.VISIBLE);
                } else {
                    btStart.setVisibility(View.INVISIBLE);
                }
            }

            //页面状态发生变化
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //计算圆点间距
        //移动距离 = 第二个圆点left值 - 第一个圆点left值
        //控件绘制流程：measures -> layout -> draw——这个流程只有在activity的onCreate方法执行结束之后才会走。
        //此时下面这一步无法执行
//        mPointDis = llContainer.getChildAt(1).getLeft() - llContainer.getChildAt(0).getLeft();

        //解决方案：监听layout方法结束的事件，位置确定好之后再获取圆点间距
        //获取视图树观测器
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //layout方法执行结束的回调
                ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointDis = llContainer.getChildAt(1).getLeft() - llContainer.getChildAt(0).getLeft();

            }
        });

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新sp
                PrefUtils.setBoolean(getApplicationContext(), "is_first_enter", false);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });
    }

    private void initData() {
        mImageViewList = new ArrayList<ImageView>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(view);

            //初始化小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);

            //初始化小圆点布局参数：宽高包裹内容，从父控件声明布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            if (i > 0) {
                params.leftMargin = 30;
            }

            point.setLayoutParams(params);

            llContainer.addView(point);
        }

    }


    class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
