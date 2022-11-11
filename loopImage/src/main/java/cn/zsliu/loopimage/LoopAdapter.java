package cn.zsliu.loopimage;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * title：LoopAdapter
 * author: zsliu
 * created by Administrator on 2022/11/1 10:54
 * description: 适配器
 */
public class LoopAdapter extends RecyclerView.Adapter<LoopViewHolder> {
    private List<String> imageList;
    private final Context context;

    public LoopAdapter(Context context) {
        this.context = context;
        this.imageList = new ArrayList<>();
    }

    @NonNull
    @Override
    public LoopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LoopViewHolder(new ImageView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull LoopViewHolder holder, int position) {
        String s = imageList.get(position);
        try {
            ImageView imageView = (ImageView) holder.itemView;
            Glide.with(context).load(s).into(imageView);
        } catch (ClassCastException e) {
            System.out.println("loop adapter 类型转换异常:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("loop adapter 未知的异常:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void addUrl(@NonNull String url) {
        //添加一条图片地址 并在最后刷新
        imageList.add(url);
        notifyItemChanged(imageList.size() - 1);
    }

    public void addAllUrl(@NonNull List<String> lists) {
        //添加集合图片数据并刷新
        imageList.addAll(lists);
        notifyDataSetChanged();
    }

    public void clear() {
        //清楚集合当中数据
        imageList.clear();
    }

    /**
     * 返回当前展示集合数据
     */
    public List<String> getImageList() {
        return imageList;
    }
}
