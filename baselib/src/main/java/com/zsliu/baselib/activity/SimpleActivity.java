package com.zsliu.baselib.activity;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * title：SimpleActivity
 * author: zsliu
 * created by Administrator on 2022/2/8 14:32
 * description: 简单activity
 */
public abstract class SimpleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化控件监听
     */
    private void initListener() {
    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    /**
     * 初始化view控件
     */
    private void initView() {
    }

    /**
     * @return xml文件id（layout）
     */
    @LayoutRes
    protected abstract int getLayoutId();
}