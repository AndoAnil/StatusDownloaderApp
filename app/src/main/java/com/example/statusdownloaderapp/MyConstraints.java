package com.example.statusdownloaderapp;

import android.os.Environment;

import java.io.File;

public class MyConstraints {

    public static final File Status_Directory=new File(Environment.getExternalStorageDirectory()
    +File.separator+"WhatsApp/Media/.Statuses");

    public static final String APP_DIR=Environment.getExternalStorageDirectory()+File.separator+"WhatsAppStatusProDir";

    public static final int THUMBSIZE=120;

}
