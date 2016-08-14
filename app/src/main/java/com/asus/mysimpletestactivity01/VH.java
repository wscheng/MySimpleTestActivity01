package com.asus.mysimpletestactivity01;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by wscheng on 2016/7/20.
 */
public class VH extends RecyclerView.ViewHolder {
    TextView mName;
    ImageView mImg;
    LinearLayout mMyLayout;
    VH(View v) {
        super(v);
        mName = (TextView) v.findViewById(R.id.my_name);
        mImg = (ImageView) v.findViewById(R.id.my_img);
        mMyLayout = (LinearLayout) v.findViewById(R.id.my_layout);
    }
}
