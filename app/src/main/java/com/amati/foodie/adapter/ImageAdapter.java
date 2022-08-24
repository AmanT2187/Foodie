package com.amati.foodie.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amati.foodie.R;
import com.amati.foodie.models.ImageModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Activity mActivity;
    private ArrayList<ImageModel> mContentList;

    public ImageAdapter(Context mContext, Activity mActivity, ArrayList<ImageModel> mContentList) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mContentList = mContentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_picks, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        final ImageModel model = mContentList.get(position);
        // setting data over views
        String imgUrl = model.getImgUrl();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(mContext)
                    .load(imgUrl)
                    .into(holder1.imgView);
        }
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            // Find all views ids
            imgView = (ImageView) itemView.findViewById(R.id.imgPick);
        }
    }
}
