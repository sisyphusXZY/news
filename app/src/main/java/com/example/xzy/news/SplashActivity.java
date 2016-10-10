package com.example.xzy.news;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import utils.PrefUtils;

public class SplashActivity extends Activity {

    private RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

        //旋转动画
        RotateAnimation animation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);//time
        animation.setFillAfter(true);

        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        //动画集合
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(animation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        //启动动画
        rlRoot.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //跳转页面
                //如果是第一次进入，跳新手引导
                //否则跳主页面

//                SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
//                boolean isFirstEnter = sp.getBoolean("is_first_enter", true);
                boolean isFirstEnter = PrefUtils.getBoolean(SplashActivity.this, "is_first_enter", true);
                Intent intent;
                if (isFirstEnter) {
                    //新手因引导
                    intent = new Intent(getApplicationContext(), GuideActivity.class);
                } else  {
                    //主页
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }

                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
