package com.example.guide;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.guide.progress.ProgressImageView;
import com.example.guide.progress.ProgressModelLoader;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private List list;
    private LinearLayout ll;
    private List<ImageView> imageViewList;
    private int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initdata();
        initview();
    }

    private void initview() {
        vp= (ViewPager) findViewById(R.id.mVp);
        ll= (LinearLayout) findViewById(R.id.ll);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clear();
                imageViewList.get(position).setImageResource(R.mipmap.yuan1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void clear(){
       for(ImageView iv:imageViewList){
           iv.setImageResource(R.mipmap.yuan);
       }
    }

    private void initdata() {

        Request request = new Request.Builder()
                .url(HttpConstants.JSON)
                .build();

        OkHttpClient okHttpClient=new OkHttpClient();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String string = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        Bean bean = gson.fromJson(string, Bean.class);

                        Upvp(bean);
                    }
                });
            }
        });
    }

    private void Upvp(Bean bean) {
        list=new ArrayList();
        imageViewList=new ArrayList<>();
        for(String str:bean.getData().getGuidepic()){
            ProgressImageView progressImageView= (ProgressImageView) LayoutInflater.from(this).inflate(R.layout.progressimageview,null,false).
                                 findViewById(R.id.pro);

            Glide.with(this).using(new ProgressModelLoader(new ProgressHandler(MainActivity.this, progressImageView)))
                    .load(str)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(progressImageView.getImageView());

            list.add(progressImageView);

            ImageView iv=new ImageView(this);
            if(i==0){
                iv.setImageResource(R.mipmap.yuan1);
            }else{
                iv.setImageResource(R.mipmap.yuan);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
            params.setMargins(10, 0, 10, 0);
            iv.setLayoutParams(params);

            imageViewList.add(iv);
            ll.addView(iv);

            i++;
        }
        MyPageAdapter myPageAdapter = new MyPageAdapter(list);
        vp.setAdapter(myPageAdapter);

    }

    private static class ProgressHandler extends Handler {

        private final WeakReference<Activity> mActivity;
        private final ProgressImageView mProgressImageView;

        public ProgressHandler(Activity activity, ProgressImageView progressImageView) {
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(activity);
            mProgressImageView = progressImageView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        int percent = msg.arg1*100/msg.arg2;
                        mProgressImageView.setProgress(percent);

                        if(percent>=100){
                            mProgressImageView.hideTextview();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list=null;
        imageViewList=null;
    }
}
