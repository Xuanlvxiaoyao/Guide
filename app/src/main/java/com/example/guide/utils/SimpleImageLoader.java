package com.example.guide.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.LruCache;

import com.example.guide.app.App;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/8/10.
 */

public class SimpleImageLoader {

    private static SimpleImageLoader simpleImageLoader=new SimpleImageLoader();
    private   LruCache<String,Bitmap> lruCache;
    private static final String TAG = "SimpleImageLoader";
    private DiskLruCache diskLruCache=null;


    private SimpleImageLoader(){
        //一般定位为android虚拟机内存的1/8
        int i = (int) ((Runtime.getRuntime().maxMemory() / 8));
        //初始化内存缓存
        lruCache=new LruCache<String, Bitmap>(i){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return  value.getByteCount();
            }
        };
        String s = Environment.getExternalStorageDirectory() + "/howto";
        File file = new File(s);

        //初始化本地缓存
        try {
            diskLruCache = DiskLruCache.open(file, App.versionCode, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static  SimpleImageLoader getInsance(){
        return  simpleImageLoader;
    }



    public  void saveMemoryImg(String url, byte[] bytes , Activity act){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if(lruCache.get(url)==null){
            if(url!=null&& bitmap !=null){
                lruCache.put(url, bitmap);
            }
        }
    }

    //存到本地
    public void saveToDisk(String url,byte[] bytes) throws IOException {
        DiskLruCache.Editor edit = diskLruCache.edit(url);
        OutputStream outputStream = edit.newOutputStream(0);
        outputStream.write(bytes);
        edit.commit();

    }

    public  Bitmap getMemoryImg(String url){
        Bitmap bitmap=null;
        if(url!=null){
            bitmap = lruCache.get(url);
        }

        return bitmap;
    }

    public Bitmap getBitmapFromDisk(String url) throws IOException {
        Bitmap bitmap=null;
        DiskLruCache.Snapshot snapshot = diskLruCache.get(url);
        if (snapshot!=null){
            InputStream inputStream = snapshot.getInputStream(0);
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        return bitmap;
    }

    public  void removeMemoryImg(String url){
        if(lruCache!=null&&url!=null){
            lruCache.remove(url);
        }
    }

    public void removeFromDisk(String url) throws IOException {
        if (diskLruCache!=null){
            diskLruCache.remove(url);
        }
    }
    public void deleteDiskCache() throws IOException {
        long size = diskLruCache.size();
        diskLruCache.delete();
    }

}
