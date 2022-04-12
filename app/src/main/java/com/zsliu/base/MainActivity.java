package com.zsliu.base;

import com.zsliu.baselib.activity.BaseActivity;
import com.zsliu.baselib.activity.SimpleActivity;

public class MainActivity extends SimpleActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }
}