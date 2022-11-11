package cn.zsliu.loopimage;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * title：LoopViewHolder
 * author: zsliu
 * created by Administrator on 2022/11/1 10:54
 * description: 视图
 */
public class LoopViewHolder extends RecyclerView.ViewHolder {
    private final ImageView imageView;

    public LoopViewHolder(@NonNull ImageView itemView) {
        super(itemView);
        itemView.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                itemView.setLayoutParams(params);
            }
        });
        itemView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.imageView = itemView;
    }
}
