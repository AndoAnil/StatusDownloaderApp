package com.example.statusdownloaderapp;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statusdownloaderapp.Model.StatusModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<StatusModel> imageList;
    private Context mcontext;
    private ImageFragment imageFragment;

    public ImageAdapter(List<StatusModel> imageList, Context mcontext, ImageFragment imageFragment) {
        this.imageList = imageList;
        this.mcontext = mcontext;
        this.imageFragment = imageFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
     final StatusModel statusModel=imageList.get(position);
     holder.thumbnail.setImageBitmap(statusModel.getThumbnail());

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             AlertDialog.Builder alBuilder=new AlertDialog.Builder(mcontext);
             ViewGroup viewGroup=(ViewGroup) view.findViewById(R.id.content);
             View v=LayoutInflater.from(mcontext).inflate(R.layout.image_show, viewGroup, false);
             ImageView imageView=v.findViewById(R.id.selectedImage);
             File f=new File(imageList.get(position).getPath());
             Picasso.get().load(f).into(imageView);
             Button share=v.findViewById(R.id.shareimage);
             Button download=v.findViewById(R.id.downloadimage);
             download.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     imageFragment.downloadImage(statusModel);
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
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail=itemView.findViewById(R.id.thumbNail);

        }
    }

}
