package com.example.statusdownloaderapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statusdownloaderapp.Model.StatusModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<StatusModel> videoList;
    private Context mcontext;
    private VideoFragment videoFragment;

    public VideoAdapter(List<StatusModel> videoList, Context mcontext, VideoFragment videoFragment) {
        this.videoList = videoList;
        this.mcontext = mcontext;
        this.videoFragment = videoFragment;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
     StatusModel statusModel=videoList.get(position);
     holder.thumbnail.setImageBitmap(statusModel.getThumbnail());

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             AlertDialog.Builder alBuilder=new AlertDialog.Builder(mcontext);
             ViewGroup viewGroup=(ViewGroup) view.findViewById(R.id.content);
             View v=LayoutInflater.from(mcontext).inflate(R.layout.video_show, viewGroup, false);
             VideoView videoView=v.findViewById(R.id.selectedVideo);
             Button share=v.findViewById(R.id.sharevideo);
             Button download=v.findViewById(R.id.downloadvideo);
             download.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     
                 }
             });
             alBuilder.setView(v);
             AlertDialog dialog=alBuilder.create();
             dialog.show();

         }
     });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder
    {
        ImageView thumbnail;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail=itemView.findViewById(R.id.thumbNail);

        }
    }
}
