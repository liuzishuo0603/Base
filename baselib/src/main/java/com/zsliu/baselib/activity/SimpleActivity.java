package com.zsliu.baselib.activity;

import android.os.Bundle;
import android.view.WindowManager;

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
        isFullScreen(isFullScreen());
        initMap();
        setContentView(getLayoutId());
        initView(savedInstanceState);
        initData();
        initNetwork();
        initListener();
    }

    /**
     * 初始化MVP
     */
    protected void initMap() {
    }

    /**
     * 网络加载
     */
    protected void initNetwork() {
        loadingWindow();
    }

    /**
     * 加载视图
     */
    protected void loadingWindow() {
    }

    /**
     * 关闭加载窗口
     */
    protected void dismissLoadingWindow() {

    }

    /**
     * 初始化控件监听
     */
    protected void initListener() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化view控件
     *
     * @param savedInstanceState 实例状态
     */
    protected void initView(Bundle savedInstanceState) {
    }

    /**
     * @return xml文件id（layout）
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 返回是否应用全屏
     *
     * @return 返回应用参数 true ||  false
     */
    protected abstract boolean isFullScreen();

    /**
     * 设置是否全屏
     *
     * @param fullScreen 是否设置为全屏  默认false（不全屏）true （全屏）
     */
    private void isFullScreen(boolean fullScreen) {
        if (fullScreen) {
            //将屏幕设置为全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}