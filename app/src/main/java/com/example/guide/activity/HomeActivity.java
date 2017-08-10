package com.example.guide.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guide.R;
import com.example.guide.fragment.Fragment2;
import com.example.guide.fragment.HomeFragment;
import com.example.guide.fragment.MineFragment;
import com.example.guide.weight.TabFragmentHost;

import org.zackratos.ultimatebar.UltimateBar;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/7/26.
 */

public class HomeActivity extends FragmentActivity {
    private long lastTime=0;
    private long firstTime=0;
    private Class[] fragmentArray={HomeFragment.class,Fragment2.class,MineFragment.class};
    private String[] strs={"首页","啥都有","个人中心"};
    private int[] imgs={R.drawable.img_home_selector,R.drawable.img_gou_selector,R.drawable.img_mine_selector};
    private LayoutInflater inflater;
    private TabFragmentHost mTabHost;
    //fragment数量
    private int count;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();


        Observable.just("Hello, world!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: "+s );
                    }
                });

        initview();
    }


    private void initview() {
        inflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        mTabHost = (TabFragmentHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        count=fragmentArray.length;

        for(int i = 0; i < count; i++){
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(strs[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
        }
    }

    /**
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = inflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(strs[index]);
        imageView.setImageResource(imgs[index]);
        return view;
    }



    @Override
    public void onBackPressed() {
        lastTime=System.currentTimeMillis();
        if(lastTime-firstTime>2000){
            firstTime=lastTime;
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
        }else{
            finish();
        }
    }
}
