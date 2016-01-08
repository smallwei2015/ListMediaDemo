package com.jash.listmediademo;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by jash
 * Date: 16-1-7
 * Time: 上午11:19
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private Context context;
    private List<Entity.ItemsEntity> list;
    private MediaPlayer player;
    public VideoAdapter(Context context, List<Entity.ItemsEntity> list) {
        this.context = context;
        this.list = list;
        player = new MediaPlayer();
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        VideoViewHolder holder = new VideoViewHolder(view);
        holder.pic.setOnClickListener(this);
        holder.pic.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        resetHolder(holder);
        Entity.ItemsEntity entity = list.get(position);
        holder.content.setText(entity.getContent());
        holder.pic.setImageURI(Uri.parse(entity.getPic_url()));
    }

    @Override
    public void onViewDetachedFromWindow(VideoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (lastHolder != null && lastHolder.equals(holder)) {
            player.reset();
            lastHolder = null;
        }
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
    private VideoViewHolder lastHolder;
    @Override
    public void onClick(View v) {
        if (lastHolder != null) {
            resetHolder(lastHolder);
        }
        VideoViewHolder tag = (VideoViewHolder) v.getTag();
        try {
            player.reset();
            player.setDataSource(list.get(tag.getAdapterPosition()).getLow_url());
            player.setDisplay(tag.video.getHolder());
            player.prepareAsync();
            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);
            tag.play.setVisibility(View.INVISIBLE);
            tag.progress.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastHolder = tag;
    }

    private void resetHolder(VideoViewHolder holder) {
        holder.pic.setVisibility(View.VISIBLE);
        holder.play.setVisibility(View.VISIBLE);
        holder.progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        lastHolder.pic.setVisibility(View.INVISIBLE);
        lastHolder.progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.seekTo(0);
        mp.start();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{

        private TextView content;
        private SimpleDraweeView pic;
        private SurfaceView video;
        private ImageView play;
        private ProgressBar progress;

        public VideoViewHolder(View itemView) {
            super(itemView);
            content = ((TextView) itemView.findViewById(R.id.item_content));
            pic = ((SimpleDraweeView) itemView.findViewById(R.id.item_pic));
            video = ((SurfaceView) itemView.findViewById(R.id.item_video));
            play = ((ImageView) itemView.findViewById(R.id.item_play));
            progress = ((ProgressBar) itemView.findViewById(R.id.item_progress));
            pic.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
            pic.setAspectRatio(1);
        }
    }
}
