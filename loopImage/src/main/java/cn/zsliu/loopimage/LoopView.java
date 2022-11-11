package cn.zsliu.loopimage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * title：LoopView
 * author: zsliu
 * created by Administrator on 2022/11/1 11:00
 * description: 轮播视图  备注再无别的场景需求可当正常RecyclerView使用
 */
public class LoopView extends RelativeLayout {
    private LoopAdapter loopAdapter;
    private RecyclerView recyclerView;
    private ImageView leftImageView;
    private ImageView rightImageView;
    private static final Handler handler = new Handler();
    private boolean dragging;
    private LoopLayoutManager loopLayoutManager;
    private int index = 0;
    private long delayTime;
    private LoopListener listener;

    public LoopView(@NonNull Context context) {
        this(context, null);
    }

    public LoopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundColor(Color.BLACK);
        initAttribute(context, attrs);
    }

    private void initAttribute(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LoopView);
        //横 、 竖
        @SuppressLint("ResourceType") int orientation = typedArray.getInteger(R.styleable.LoopView_android_orientation, 0);
        //是否显示左右按钮  默认不显示
        boolean isShowAbout = typedArray.getBoolean(R.styleable.LoopView_lv_isShowAbout, false);
        //自动切换
        boolean isAutomatic = typedArray.getBoolean(R.styleable.LoopView_lv_isAutomatic, true);
        delayTime = typedArray.getInteger(R.styleable.LoopView_lv_isAutomaticTime, 1500);
        //是否无限滑动
        boolean isLoop = typedArray.getBoolean(R.styleable.LoopView_lv_isLoop, true);
        //内部间距
        int padding = typedArray.getDimensionPixelSize(R.styleable.LoopView_lv_padding, 15);
        //按钮间距
        int distance = typedArray.getDimensionPixelSize(R.styleable.LoopView_lv_distance, 30);
        //左侧按钮属性
        int left = typedArray.getResourceId(R.styleable.LoopView_lv_leftImage, R.drawable.ic_loop_left);
        int leftBackground = typedArray.getResourceId(R.styleable.LoopView_lv_leftBackground, R.drawable.ic_loop_background);
        int leftHeight = typedArray.getDimensionPixelSize(R.styleable.LoopView_lv_leftHeight, 90);
        int leftWidth = typedArray.getDimensionPixelSize(R.styleable.LoopView_lv_leftWidth, 90);
        //右侧按钮属性
        int right = typedArray.getResourceId(R.styleable.LoopView_lv_rightImage, R.drawable.ic_loop_right);
        int rightBackground = typedArray.getResourceId(R.styleable.LoopView_lv_rightBackground, R.drawable.ic_loop_background);
        int rightHeight = typedArray.getDimensionPixelSize(R.styleable.LoopView_lv_rightHeight, 90);
        int rightWidth = typedArray.getDimensionPixelSize(R.styleable.LoopView_lv_rightWidth, 90);
        //释放回收
        typedArray.recycle();
        // 创建RecyclerView实例
        recyclerView = new RecyclerView(context);
        createRecyclerView(orientation, isAutomatic, isLoop);
        //横向滑动   显示的情况下
        if (orientation == 0 && isShowAbout) {
            //创建的左侧按钮
            leftImageView = new ImageView(context);
            addLeftButton(left, leftBackground, leftHeight, leftWidth, padding, distance);
            //创建的右侧按钮
            rightImageView = new ImageView(context);
            addRightButton(right, rightBackground, rightHeight, rightWidth, padding, distance);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addLeftButton(int left, int leftBackground, int leftHeight, int leftWidth, int padding, int distance) {
        if (leftImageView == null) return;
        LayoutParams layoutParams = new LayoutParams(leftWidth, leftHeight);
        layoutParams.leftMargin = distance;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        leftImageView.setBackground(this.getContext().getResources().getDrawable(leftBackground));
        leftImageView.setImageDrawable(this.getContext().getResources().getDrawable(left));
        leftImageView.setPadding(padding, padding, padding, padding);
        this.addView(leftImageView, layoutParams);
        setOnLeftClickListener();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addRightButton(int right, int rightBackground, int rightHeight, int rightWidth, int padding, int distance) {
        if (rightImageView == null) return;
        LayoutParams layoutParams = new LayoutParams(rightWidth, rightHeight);
        layoutParams.rightMargin = distance;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        rightImageView.setBackground(this.getContext().getResources().getDrawable(rightBackground));
        rightImageView.setImageDrawable(this.getContext().getResources().getDrawable(right));
        rightImageView.setPadding(padding, padding, padding, padding);
        this.addView(rightImageView, layoutParams);
        setOnRightClickListener();
    }

    /**
     * 添加 RecyclerView实例
     */
    private void createRecyclerView(int orientation, boolean isAutomatic, boolean isLoop) {
        if (recyclerView == null) return;
        loopLayoutManager = new LoopLayoutManager(this.getContext(), orientation, false, isLoop);
        recyclerView.setLayoutManager(loopLayoutManager);
        loopAdapter = new LoopAdapter(this.getContext());
        recyclerView.setAdapter(loopAdapter);
        addView(recyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //开启自动轮播
        startLoopScrollListener(isAutomatic);
    }

    private void startLoopScrollListener(boolean isAutomatic) {
        if (recyclerView == null) return;
        if (loopLayoutManager == null) return;
        if (isAutomatic && !dragging) handler.postDelayed(loopRun, delayTime);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        index = loopLayoutManager.findFirstVisibleItemPosition();
                        if (isAutomatic)
                            removeLoop();
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        index = loopLayoutManager.findLastVisibleItemPosition();
                        if (null != listener)
                            listener.onLayoutPageListener(index, loopAdapter.getImageList().get(index));
                        if (isAutomatic)
                            addLoop();
                        break;
                }
            }

            private void removeLoop() {
                handler.removeCallbacks(loopRun);
                dragging = true;
            }

            private void addLoop() {
                if (dragging) {
                    dragging = false;
                    handler.postDelayed(loopRun, delayTime);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    //添加单条数据
    public void addImageUrl(String url) {
        if (loopAdapter == null) return;
        loopAdapter.addUrl(url);
    }

    //添加所有数据
    public void addImageUrls(List<String> urlList) {
        loopAdapter.addAllUrl(urlList);
    }

    //重新添加数据
    public void addAgainImageUrl(List<String> urlList) {
        //先清除残余数据后添加新数据
        loopAdapter.clear();
        loopAdapter.addAllUrl(urlList);
    }

    /**
     * 设置轮播时间
     */
    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    private void setOnLeftClickListener() {
        if (leftImageView == null) return;
        leftImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                towardsLeft();
            }
        });
    }

    private void setOnRightClickListener() {
        if (rightImageView == null) return;
        rightImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                towardsRight();
            }
        });
    }

    public void setOnPagerListener(LoopListener listener) {
        this.listener = listener;
    }

    //启动轮播
    public void start() {
        if (dragging) {
            handler.postDelayed(loopRun, delayTime);
            dragging = false;
        }
    }

    //停止轮播
    public void stop() {
        handler.removeCallbacks(loopRun);
        dragging = true;
    }

    private void loop() {
        if (recyclerView == null) return;
        if (index >= loopAdapter.getItemCount() - 1) {
            recyclerView.smoothScrollToPosition(0);
            index = 0;
        } else {
            recyclerView.smoothScrollToPosition(index += 1);
        }
    }

    //向左移动
    private void towardsLeft() {
        if (recyclerView == null) return;
        //点击的时候把轮播停止
        stop();
        if (index > 0) {
            recyclerView.smoothScrollToPosition(index -= 1);
        }
    }

    //向右移动
    private void towardsRight() {
        if (recyclerView == null) return;
        //点击的时候把轮播停止
        stop();
        if (index < loopAdapter.getItemCount() - 1) {
            recyclerView.smoothScrollToPosition(index += 1);
        }
    }

    private final Runnable loopRun = new Runnable() {
        @Override
        public void run() {
            if (recyclerView == null) return;
            if (loopAdapter == null) return;
            handler.postDelayed(loopRun, delayTime);
            loop();
        }
    };
}
