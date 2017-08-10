package com.example.guide.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.guide.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/25.
 */

public class AdvertisementActivity extends Activity {
    private Button btn;
    private boolean flag=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        initview();
        DelayedIntent();
    }

    private void initview() {
        btn= (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=false;
                startActivity(new Intent(AdvertisementActivity.this,HomeActivity.class));
                finish();
            }
        });
    }

    public void DelayedIntent(){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(flag){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(AdvertisementActivity.this,HomeActivity.class));
                            finish();
                        }
                    });
                }
            }
        },3000);
    }
}
