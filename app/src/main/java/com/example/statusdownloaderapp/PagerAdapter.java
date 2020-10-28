package com.example.statusdownloaderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private ImageFragment imageFragment;
    private VideoFragment videoFragment;
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        imageFragment=new ImageFragment();
        videoFragment=new VideoFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return imageFragment;
        }
        else
        {
            return videoFragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
        {
            return "Image";
        }
        else
        {
            return "Video";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
