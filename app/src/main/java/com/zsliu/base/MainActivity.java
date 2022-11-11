package com.zsliu.base;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zsliu.baselib.activity.SimpleActivity;

import cn.zsliu.loopimage.LoopListener;
import cn.zsliu.loopimage.LoopView;

public class MainActivity extends SimpleActivity {

    private LoopView mLoopview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView tv = findViewById(R.id.tv);
        mLoopview = findViewById(R.id.loopview);
        mLoopview.addImageUrl("https://www.yh31.com/uploadfile/pzsmk5/202005292301478313.jpg");
        mLoopview.addImageUrl("https://www.yh31.com/uploadfile/pzsmk5/202006282322229709.jpg");
        mLoopview.setOnPagerListener(new LoopListener() {
            @Override
            public void onLayoutPageListener(int position, String url) {
                Log.e("lzs", "onLayoutPageListener: " + position + "," + url);
            }
        });
       /* loopAdapter.addUrl("https://www.yh31.com/uploadfile/pzsmk5/202005292301478313.jpg");
        loopAdapter.addUrl("https://www.yh31.com/uploadfile/pzsmk5/202006282322229709.jpg");

        loopAdapter.addUrl("https://www.yh31.com/uploadfile/pzsmk5/202104141816268071.jpg");
        loopAdapter.addUrl("https://www.yh31.com/uploadfile/pzsmk5/202005292320403174.jpg");
        loopAdapter.addUrl("https://www.yh31.com/uploadfile/pzsmk5/202003092114237883.jpg");
        loopAdapter.addUrl("https://www.yh31.com/uploadfile/PZSMK5/201707102133116035.jpg");*/
//        LoopManager.init(null,new LoopView(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoopview.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoopview.stop();
    }
}