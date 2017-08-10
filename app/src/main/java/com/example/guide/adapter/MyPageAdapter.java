package com.example.guide.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.guide.model.progress.ProgressImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MyPageAdapter extends PagerAdapter {

    private List<ProgressImageView> list;

    public MyPageAdapter(List<ProgressImageView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
