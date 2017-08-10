package com.example.guide.utils;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/7/31.
 */

public class OkUtils {
    private static OkHttpClient okHttpClient;
    private OkUtils(){

    }

    public static OkHttpClient getInstance(){
        if(okHttpClient==null){
            synchronized (OkHttpClient.class){
                if(okHttpClient==null){
                    okHttpClient=new OkHttpClient();

                }
            }
        }

        return okHttpClient;
    }


    public static void get(String url, Callback callback){
        Request request = new Request.Builder()
                            .url(url)
                            .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }
}
