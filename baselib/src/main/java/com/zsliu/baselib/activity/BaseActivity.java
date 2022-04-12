package com.zsliu.baselib.activity;

import com.zsliu.baselib.BasePresenter;

/**
 * title：BaseActivity
 * author: zsliu
 * created by Administrator on 2022/2/8 14:30
 * description: BaseActivity
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends SimpleActivity {
    protected P mPresenter;

    @Override
    protected void initMap() {
        mPresenter = initPresenter();
        //控制器不为空的情况下
        if (mPresenter != null) {
            //添加视图View
            mPresenter.addView((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //控制器不为空的情况下
        if (mPresenter != null) {
            //去销毁清除
            mPresenter.destroy();
        }
    }

    /**
     * @return 创建控制器
     */
    protected abstract P initPresenter();
}
