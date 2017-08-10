package com.example.guide.utils;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Administrator on 2017/7/31.
 */

public class GsonUtils {
    private static Gson gson;

    private GsonUtils(){

    }

    public static Gson getInstance(){
        if(gson==null){
            synchronized (OkHttpClient.class){
                if(gson==null){
                    gson=new Gson();
                }
            }
        }
        return gson;
    }

//    public static Class getclass(String json,Class clazz){
//        return (Class) getInstance().fromJson(json,clazz);
//    }
}
