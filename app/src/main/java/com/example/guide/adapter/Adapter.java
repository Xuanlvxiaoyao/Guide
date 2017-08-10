package com.example.guide.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guide.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyHolder> {
    private LayoutInflater inflater;
    private List<Bean1> list;
    private Context context;
    public Adapter(Context context,List<Bean1> list){
        this.list=list;
        this.context=context;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.from(context).inflate(R.layout.item,null,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
         holder.iv.setImageResource(list.get(position).getImg());
        holder.tv.setText(list.get(position).getName());
//        holder.tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public ImageView iv;
        public MyHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.tv);
            iv=(ImageView)itemView.findViewById(R.id.iv);
        }
    }
}
