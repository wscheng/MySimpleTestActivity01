package com.asus.mysimpletestactivity01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wscheng on 2016/7/21.
 */
public class ListAdapter extends BaseAdapter {
    List<String> mNameList;
    Activity mActivity;
    LayoutInflater mInflater;
    ListAdapter(Activity activity) {
        mNameList = Utilities.getNameList();
        mActivity = activity;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView mName;
        ImageView mImg;
        LinearLayout mLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        final Context context = parent.getContext();
        if(convertView == null) {
            v = mInflater.inflate(R.layout.rec_content_view, parent, false);
            holder = new ViewHolder();
            holder.mImg = (ImageView) v.findViewById(R.id.my_img);
            holder.mName = (TextView) v.findViewById(R.id.my_name);
            holder.mLayout = (LinearLayout) v.findViewById(R.id.my_layout);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String personName = mNameList.get(position);
        holder.mName.setText(personName);
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setMessage(getClickPersonMessage(context, personName)).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });

        if (position == mNameList.size() - 1) {
            holder.mImg.setImageResource(android.R.drawable.btn_minus);
        } else {
            holder.mImg.setImageResource(android.R.drawable.btn_star);
        }

        return v;
    }

    public static String getClickPersonMessage(Context context, String name) {
        return context.getString(R.string.click_name_msg, name);
    }
}
