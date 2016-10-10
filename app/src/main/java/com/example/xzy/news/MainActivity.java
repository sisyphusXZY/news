package com.example.xzy.news;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by XZY on 2016/10/10.
 */

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
    }
}
