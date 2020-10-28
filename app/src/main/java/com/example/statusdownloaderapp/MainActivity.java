package com.example.statusdownloaderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("StatusDownloader");
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        if(savedInstanceState==null)
        {
            bottomNavigationView.setSelectedItemId(R.id.status);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectFrag=null;
            switch (menuItem.getItemId())
            {
                case R.id.status:
                    selectFrag=new StatusFragment();
                    break;
                case R.id.saved:
                      selectFrag=new SavedFragment();
                      break;
                case R.id.more:
                    selectFrag=new MoreFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFrag).commit();
            return  true;
        }
    };


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.tabmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.rateus:
                final String appPackageName = "Nothing";// getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.edufree" + appPackageName)));
                } catch (Exception e) {
                   e.printStackTrace();
                }
                break;
            case R.id.share:
                try {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out my app at: Status downloader app" + BuildConfig.APPLICATION_ID);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.about:
                aboutFun();
                break;

        }
        return true;
    }

    public void aboutFun()
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.about);
        dialog.setTitle("Help");
        dialog.setCancelable(true);
        dialog.show();
    }

}