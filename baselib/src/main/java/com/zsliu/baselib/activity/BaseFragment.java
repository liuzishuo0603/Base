package com.zsliu.baselib.activity;

import com.zsliu.baselib.BasePresenter;

/**
 * title：BaseFragment
 * author: zsliu
 * created by Administrator on 2022/4/12 14:16
 * description: BaseFragment
 */
public abstract class BaseFragment<V, P extends BasePresenter<V>> extends SimpleFragment {
    private P mPresenter;
    protected V mView;

    @Override
    protected void initMap() {
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.addView((V) this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }

    protected abstract P initPresenter();
}
