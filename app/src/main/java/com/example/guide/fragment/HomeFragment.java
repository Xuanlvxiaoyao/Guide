package com.example.guide.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.guide.R;
import com.example.guide.bean.Bean;
import com.example.guide.constants.HttpConstants;
import com.example.guide.utils.GsonUtils;
import com.example.guide.utils.OkUtils;
import com.example.guide.utils.SimpleImageLoader;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/7/27.
 */

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener {
    private SliderLayout mDemoSlider;
    private List<String> list;
    private View v;
    private String url="https://ps.ssl.qhimg.com/sdmt/214_135_100/t01b8b08f69f74e48ec.jpg";
    private OkUtils okUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            v = inflater.inflate(R.layout.fragment1, null);

        initView(v);
        initData();
        return v;

    }

    private void initView(View v) {
        mDemoSlider = (SliderLayout)v.findViewById(R.id.slider);


    }

    private void initData() {

        okUtils.get(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                final byte[] bytes = response.body().bytes();
                Log.e(TAG, "onResponse: "+bytes.length);



                SimpleImageLoader.getInsance().saveMemoryImg(url, bytes,getActivity());
                SimpleImageLoader.getInsance().saveToDisk(url,bytes);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getActivity(),"1"+bytes.length, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(),"存储图片成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        OkUtils.get(HttpConstants.JSON, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!TextUtils.isEmpty(string)){
                            Gson gson= GsonUtils.getInstance();
                            Bean bean = gson.fromJson(string, Bean.class);

                            Upvp(bean);
                        }

                    }
                });
            }
        });
    }

    private void Upvp(Bean bean) {
        list=new ArrayList<>();

        for (String str : bean.getData().getGuidepic()) {
            list.add(str);
        }

        for(String s:list){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(s)
                    .setScaleType(DefaultSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setDuration(4000);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){
            mDemoSlider.stopAutoCycle();
        }else{
            mDemoSlider.startAutoCycle();
        }
    }
}
