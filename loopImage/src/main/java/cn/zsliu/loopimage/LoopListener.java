package cn.zsliu.loopimage;

/**
 * title：LoopListener
 * author: zsliu
 * created by Administrator on 2022/11/1 11:12
 * description: 轮播监听
 */
public interface LoopListener {

    /**
     * 滑动监听
     */
    void onLayoutPageListener(int position, String url);
}
