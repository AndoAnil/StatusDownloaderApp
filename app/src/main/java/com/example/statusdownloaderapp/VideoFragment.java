package com.example.statusdownloaderapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statusdownloaderapp.Model.StatusModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class VideoFragment extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Handler handler=new Handler();
    ArrayList<StatusModel> videosModelArrayList;
    VideoAdapter videoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_video,container,false);
        recyclerView=view.findViewById(R.id.videoRecyclerview);
        progressBar=view.findViewById(R.id.videoProgressbar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        
        videosModelArrayList=new ArrayList<>();
        getVideoStatus();
    }

    private void getVideoStatus() {
        if(MyConstraints.Status_Directory.exists())
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File[] statusFiles=MyConstraints.Status_Directory.listFiles();

                    if(statusFiles!=null && statusFiles.length>0)
                    {
                        Arrays.sort(statusFiles);

                        for(final File statusFile:statusFiles)
                        {
                            StatusModel statusModel=new StatusModel(statusFile,
                                    statusFile.getName(),statusFile.getAbsolutePath());
                            statusModel.setThumbnail(getThumbNail(statusModel));

                            if (statusModel.isVideo())
                            {
                                videosModelArrayList.add(statusModel);
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                videoAdapter=new VideoAdapter(videosModelArrayList,getContext(),VideoFragment.this);
                                recyclerView.setAdapter(videoAdapter);
                                videoAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    else
                    {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                             progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Dir does not exist", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }

    }

    private Bitmap getThumbNail(StatusModel statusModel) {
        if(statusModel.isVideo())
        {
            return ThumbnailUtils.createVideoThumbnail(statusModel.getFile().getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
        }
        else
        {
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(statusModel.getFile().getAbsolutePath()),
                    MyConstraints.THUMBSIZE,MyConstraints.THUMBSIZE);
        }
    }
}
