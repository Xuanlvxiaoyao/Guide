package com.example.guide.weight;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/7/26.
 */

public class ZitiTextView extends TextView {
    public ZitiTextView(Context context) {
        super(context);

    }

    public ZitiTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZitiTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(createTypeface(getContext(),"font/kaiti.ttf"));
    }
}
