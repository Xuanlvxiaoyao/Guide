package com.example.guide.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.guide.utils.SharedUtils;

/**
 * Created by Administrator on 2017/7/26.
 */

public class App extends Application {
    public SharedUtils sharedUtils;
    public static int versionCode;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedUtils=new SharedUtils();
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
