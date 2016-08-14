package com.asus.mysimpletestactivity01;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wscheng on 2016/7/20.
 */
public class RecAdapter extends RecyclerView.Adapter<VH>{


    List<String> mNameList;

    RecAdapter() {
        mNameList = Utilities.getNameList();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_content_view, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.mName.setText(mNameList.get(position));
        if (position == mNameList.size() - 1) {
            holder.mImg.setImageResource(android.R.drawable.btn_minus);
            holder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://yahoo.com"));
                    browseIntent.setPackage("com.android.chrome");
                    v.getContext().startActivity(browseIntent);
                }
            });
            holder.mName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://apple.com"));
                    browseIntent.setPackage("com.android.chrome");
                    v.getContext().startActivity(browseIntent);
                }
            });
            holder.mMyLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
                    browseIntent.setPackage("com.android.chrome");
                    v.getContext().startActivity(browseIntent);
                }
            });
        } else {
            holder.mImg.setImageResource(android.R.drawable.btn_star);
        }
    }

    @Override
    public int getItemCount() {
        return mNameList.size();
    }
}

