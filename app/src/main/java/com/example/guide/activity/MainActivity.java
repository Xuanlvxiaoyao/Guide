package com.example.guide.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.guide.R;
import com.example.guide.adapter.MyPageAdapter;
import com.example.guide.app.App;
import com.example.guide.bean.Bean;
import com.example.guide.constants.HttpConstants;
import com.example.guide.model.progress.ProgressImageView;
import com.example.guide.model.progress.ProgressModelLoader;
import com.example.guide.utils.GsonUtils;
import com.example.guide.utils.OkUtils;
import com.example.guide.utils.SharedUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private List list;
    private LinearLayout ll;
    private List<ImageView> imageViewList;
    private int i=0;
    private Button btn;
    private SharedUtils sharedUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedUtils= ((App)getApplication()).sharedUtils;
        if(sharedUtils.getShared_int("isFirst",this)==1){
            startActivity(new Intent(this,AdvertisementActivity.class));
            finish();
            return;
        }
        initdata();
        initview();

    }

    private void initview() {
        vp= (ViewPager) findViewById(R.id.mVp);
        ll= (LinearLayout) findViewById(R.id.ll);
        btn= (Button) findViewById(R.id.btn);

        btn.setVisibility(View.GONE);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clear();
                imageViewList.get(position).setImageResource(R.mipmap.yuan1);

                if(position==list.size()-1){
                    btn.setVisibility(View.VISIBLE);

                    Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.button_enter);
                    animation.setInterpolator(new OvershootInterpolator());
                    btn.setAnimation(animation);

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           sharedUtils.saveShared_int("isFirst",1,MainActivity.this);
                            startActivity(new Intent(MainActivity.this,AdvertisementActivity.class));
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void clear(){
       for(ImageView iv:imageViewList){
           iv.setImageResource(R.mipmap.yuan);
           btn.setVisibility(View.GONE);
       }
    }

    private void initdata() {
        OkUtils.get(HttpConstants.JSON, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = GsonUtils.getInstance();
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
