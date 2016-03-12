package com.example.vikash.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikash.model.TrailerModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by root on 29/2/16.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.CustomViewHolder> {

    Context mContext;
    TrailerModel mTrailerModel;

    public TrailerAdapter(Context context, TrailerModel trailerModel) {
        this.mContext = context;
        mTrailerModel = trailerModel;
    }

    @Override
    public TrailerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_trailer_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.CustomViewHolder holder, int position) {
        holder.mTextView.setText(mTrailerModel.getResults().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (mTrailerModel.getResults() != null ? mTrailerModel.getResults().size() : 0);
    }

    public void setmTrailerModel(TrailerModel mTrailerModel) {
        this.mTrailerModel = mTrailerModel;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;


        public CustomViewHolder(View itemView) {
            super(itemView);
            this.mImageView = (ImageView) itemView.findViewById(R.id.trailer_play_pause_id);
            this.mTextView = (TextView) itemView.findViewById(R.id.trailer_detail_id);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" +
                            mTrailerModel.getResults().get(getAdapterPosition()).getId()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }


    }
}
