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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    ArrayList<StatusModel>  imageModelArrayList;
    Handler handler=new Handler();
    ImageAdapter imageAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_image,container,false);
        imageModelArrayList=new ArrayList<>();
        recyclerView=view.findViewById(R.id.imageRecyclerview);
        progressBar=view.findViewById(R.id.imageProgressbar);
        getStatus();
        return view;
    }

    private void getStatus() {
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

                            if (!statusModel.isVideo())
                            {
                                imageModelArrayList.add(statusModel);
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                             imageAdapter=new ImageAdapter(imageModelArrayList,getContext(),ImageFragment.this);
                             recyclerView.setAdapter(imageAdapter);
                             imageAdapter.notifyDataSetChanged();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

    }

    public void downloadImage(StatusModel statusModel) {
        File file=new File(MyConstraints.APP_DIR);
        if(!file.exists())
        {
            file.mkdirs();
        }

        File destfile=new File(file+File.separator+statusModel.getTitle());
        if(destfile.exists())
        {
            destfile.delete();
        }

        copyFile(statusModel.getFile(),destfile);
        Toast.makeText(getContext(), "Download complete", Toast.LENGTH_SHORT).show();
    }

    private void copyFile(File file, File destfile) {
        if (!destfile.getParentFile().exists())
        {
            destfile.getParentFile().mkdirs();
        }

        if (!destfile.exists())
        {
            try {
                destfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileChannel source=null;
        FileChannel destination=null;
        try {
            source=new FileInputStream(file).getChannel();
            destination=new FileOutputStream(destfile).getChannel();
            destination.transferFrom(source,0,source.size());
            source.close();
            destination.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
