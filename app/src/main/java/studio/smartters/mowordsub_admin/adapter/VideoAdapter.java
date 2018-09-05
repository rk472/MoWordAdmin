package studio.smartters.mowordsub_admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.viewHolder.VideoViewHolder;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    Context c;
    List<String> desc,path;
    public VideoAdapter(Context c, List desc, List path) {
        this.c=c;
        this.desc=desc;
        this.path=path;
    }

    @NonNull
    @Override

    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.video_row,parent,false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setName(desc.get(position));
        holder.setClick(path.get(position));
    }

    @Override
    public int getItemCount() {
        return desc.size();
    }
}

