package com.example.guide.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.guide.R;
import com.example.guide.utils.SimpleImageLoader;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MineFragment extends Fragment {
    private ImageView iv;
    private String url="https://ps.ssl.qhimg.com/sdmt/214_135_100/t01b8b08f69f74e48ec.jpg";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment3, null);
        initView(v);
        initData();

        return v;
    }

    private void initData() {


        }

    private void initView(View v) {
        iv = (ImageView) v.findViewById(R.id.iv);

        iv.setImageBitmap(SimpleImageLoader.getInsance().getMemoryImg(url));
    }
}
