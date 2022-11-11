package cn.zsliu.loopimage;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * title：LoopLayoutManager
 * author: zsliu
 * created by Administrator on 2022/11/1 11:01
 * description: 轮播线性布局管理
 */
public class LoopLayoutManager extends LinearLayoutManager {
    private PagerSnapHelper pagerSnapHelper;
    private final boolean isHorizontally;
    private final boolean isVertically;
    private final boolean looperEnable;

    public LoopLayoutManager(Context context, int orientation, boolean reverseLayout, boolean isLoop) {
        super(context, orientation, reverseLayout);
        init();
        this.looperEnable = isLoop;
        //判断的打开某一个滑动
        this.isHorizontally = orientation == 0;
        this.isVertically = orientation == 1;
    }

    private void init() {
        pagerSnapHelper = new PagerSnapHelper();
    }

    @Override
    public boolean canScrollVertically() {
        return isVertically;
    }

    @Override
    public boolean canScrollHorizontally() {
        return isHorizontally;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (looperEnable) {
            int loop = loopVertically(dy, recycler);
            //滚动
            offsetChildrenVertical(loop * -1);
            //回收已经离开界面的
            recyclerVerticallyHideView(dy, recycler);
            return loop;
        } else {
            return super.scrollVerticallyBy(dy, recycler, state);
        }
    }

    private int loopVertically(int dy, RecyclerView.Recycler recycler) {
        if (dy > 0) {
            // 向下滚动
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView == null) return 0;
            int lastPos = getPosition(lastView);
            // 可见的最后一个itemView完全滑进来了，需要补充新的
            if (lastView.getBottom() < getWidth()) {
                View scrap = null;
                // 判断可见的最后一个itemView的索引，
                // 如果是最后一个，则将下一个itemView设置为第一个，否则设置为当前索引的下一个
                if (lastPos == getItemCount() - 1) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(0);
                    } else {
                        dy = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }
                if (scrap == null) {
                    return dy;
                }
                // 将新的itemViewadd进来并对其测量和布局
                addView(scrap, getChildCount() + 1);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, 0, lastView.getBottom(),
                        width, lastView.getBottom() + height);
                return dy;
            }
        } else {
            //向上滚动
            View firstView = getChildAt(0);
            if (firstView == null) return 0;
            int firstPos = getPosition(firstView);
            if (firstView.getTop() >= 0) {
                View scrap = null;
                if (firstPos == 0) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(getItemCount() - 1);
                    } else {
                        dy = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }
                if (scrap == null) {
                    return 0;
                }
                addView(scrap, 0);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, 0, firstView.getTop() - height,
                        width, firstView.getTop());
            }
        }
        return dy;
    }
    /**
     * 回收界面不可见的view
     */
    private void recyclerVerticallyHideView(int dx, RecyclerView.Recycler recycler) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == null) {
                continue;
            }
            if (dx > 0) {
                //向左滚动，移除一个左边不在内容里的view
                if (view.getBottom() < 0) {
                    removeAndRecycleView(view, recycler);
                }
            } else {
                //向右滚动，移除一个右边不在内容里的view
                if (view.getTop() > getWidth()) {
                    removeAndRecycleView(view, recycler);
                }
            }
        }

    }
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (looperEnable) {
            int loop = loopHorizontal(dx, recycler);
            //滚动
            offsetChildrenHorizontal(loop * -1);
            //回收已经离开界面的
            recyclerHorizontalHideView(dx, recycler);
            return loop;
        } else {
            return super.scrollHorizontallyBy(dx, recycler, state);
        }
    }

    private int loopHorizontal(int dx, RecyclerView.Recycler recycler) {
        if (dx > 0) {
            // 向左滚动
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView == null) return 0;
            int lastPos = getPosition(lastView);
            // 可见的最后一个itemView完全滑进来了，需要补充新的
            if (lastView.getRight() < getWidth()) {
                View scrap = null;
                // 判断可见的最后一个itemView的索引，
                // 如果是最后一个，则将下一个itemView设置为第一个，否则设置为当前索引的下一个
                if (lastPos == getItemCount() - 1) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(0);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }
                if (scrap == null) {
                    return dx;
                }
                // 将新的itemViewadd进来并对其测量和布局
                addView(scrap, getChildCount() + 1);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, lastView.getRight(), 0,
                        lastView.getRight() + width, height);
                return dx;
            }
        } else {
            //向右滚动
            View firstView = getChildAt(0);
            if (firstView == null) return 0;
            int firstPos = getPosition(firstView);
            if (firstView.getLeft() >= 0) {
                View scrap = null;
                if (firstPos == 0) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(getItemCount() - 1);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }
                if (scrap == null) {
                    return 0;
                }
                addView(scrap, 0);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, firstView.getLeft() - width, 0,
                        firstView.getLeft(), height);
            }
        }
        return dx;
    }

    /**
     * 回收界面不可见的view
     */
    private void recyclerHorizontalHideView(int dx, RecyclerView.Recycler recycler) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == null) {
                continue;
            }
            if (dx > 0) {
                //向左滚动，移除一个左边不在内容里的view
                if (view.getRight() < 0) {
                    removeAndRecycleView(view, recycler);
                }
            } else {
                //向右滚动，移除一个右边不在内容里的view
                if (view.getLeft() > getWidth()) {
                    removeAndRecycleView(view, recycler);
                }
            }
        }

    }

/*    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }
        //preLayout主要支持动画，直接跳过
        if (state.isPreLayout()) {
            return;
        }
        //将视图分离放入scrap缓存中，以准备重新对view进行排版
        detachAndScrapAttachedViews(recycler);
        int autualWidth = 0;
        for (int i = 0; i < getItemCount(); i++) {
            Log.e("lzs", "onLayoutChildren: " + i);
            //初始化，将在屏幕内的view填充
            View itemView = recycler.getViewForPosition(i);
            addView(itemView);
            //测量itemView的宽高
            measureChildWithMargins(itemView, 0, 0);
            int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);
            //根据itemView的宽高进行布局
            layoutDecorated(itemView, autualWidth, 0, autualWidth + width, height);
            autualWidth += width;
            //如果当前布局过的itemView的宽度总和大于RecyclerView的宽，则不再进行布局
            if (autualWidth > getWidth()) {
                break;
            }
        }
    }*/

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        if (null != pagerSnapHelper) {
            pagerSnapHelper.attachToRecyclerView(view);
        }
    }
}
