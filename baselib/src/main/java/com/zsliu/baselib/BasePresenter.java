package com.zsliu.baselib;

import java.lang.ref.WeakReference;

/**
 * title：BasePresenter
 * author: zsliu
 * created by Administrator on 2022/4/12 13:43
 * description: 公共控制P层
 */
public class BasePresenter<V> {
    private WeakReference<V> mWeakReference;
    protected V mView;

    public void addView(V view) {
        //创建弱引用
        mWeakReference = new WeakReference<>(view);
        //获取当前视图View
        mView = mWeakReference.get();
    }

    public void destroy() {
        //弱引用实例不为空的情况
        if (mWeakReference != null) {
            //清楚保存的View视图
            mWeakReference.clear();
        }
    }
}
