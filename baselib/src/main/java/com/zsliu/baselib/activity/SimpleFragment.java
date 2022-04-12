package com.zsliu.baselib.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * title：SimpleFragment
 * author: zsliu
 * created by Administrator on 2022/4/12 14:12
 * description: 简单fragment
 */
public abstract class SimpleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initMap();
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(savedInstanceState, view);
        initData();
        initNetwork();
        initListener();
        return view;
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
     * @param view               视图
     */
    protected void initView(Bundle savedInstanceState, View view) {
    }

    /**
     * @return xml文件id（layout）
     */
    @LayoutRes
    protected abstract int getLayoutId();
}
