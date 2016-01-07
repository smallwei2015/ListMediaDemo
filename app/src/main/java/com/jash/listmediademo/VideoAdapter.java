package com.jash.listmediademo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Collection;
import java.util.List;

/**
 * Created by jash
 * Date: 16-1-7
 * Time: 上午11:19
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private List<Entity.ItemsEntity> list;

    public VideoAdapter(Context context, List<Entity.ItemsEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Entity.ItemsEntity entity = list.get(position);
        holder.content.setText(entity.getContent());
        holder.pic.setImageURI(Uri.parse(entity.getPic_url()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends Entity.ItemsEntity> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }
    public static class VideoViewHolder extends RecyclerView.ViewHolder{

        private TextView content;
        private SimpleDraweeView pic;
        private SurfaceView video;

        public VideoViewHolder(View itemView) {
            super(itemView);
            content = ((TextView) itemView.findViewById(R.id.item_content));
            pic = ((SimpleDraweeView) itemView.findViewById(R.id.item_pic));
            video = ((SurfaceView) itemView.findViewById(R.id.item_video));
            pic.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
            pic.setAspectRatio(1);
        }
    }
}
