package com.example.vikash.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.vikash.Constant;
import com.example.vikash.model.DataModel;
import com.example.vikash.model.ResultModel;

import java.util.Comparator;

/**
 * Created by root on 4/2/16.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CustomViewHolder> {
    private DataModel mDataModel;
    private Context mContext;
    private Bundle mBundle;

    public MovieAdapter(Context paramContext, DataModel paramDataModel) {
        this.mDataModel = paramDataModel;
        this.mContext = paramContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_gridview_item, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(viewGroup.getMeasuredWidth() / 2, viewGroup.getMeasuredHeight() / 2));
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    public void setmDataModel(DataModel mDataModel) {
        this.mDataModel = mDataModel;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {

        String url = Constant.BASE_URL_IMAGE_DOWNLOAD + "" + mDataModel.getResults().get(i).getBackdropPath();

        //Download image using picasso library
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(customViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return (mDataModel.getResults() != null ? mDataModel.getResults().size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.movie_image_id);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mBundle = new Bundle();
            if (mDataModel.getResults() != null) {
                mBundle.putParcelable(String.valueOf(R.string.data), mDataModel.getResults().get(getAdapterPosition()));
            }
            Intent intent = new Intent(mContext, MovieDetail.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(mBundle);
            mContext.startActivity(intent);
        }
    }

    public static final Comparator<ResultModel> rattingComparator = new Comparator<ResultModel>() {

        @Override
        public int compare(ResultModel o1, ResultModel o2) {
            return o1.getVoteCount() - o2.getVoteCount();  // This will work because age is positive integer
        }

    };

    public static final Comparator<ResultModel> popularityComparator = new Comparator<ResultModel>() {

        @Override
        public int compare(ResultModel o1, ResultModel o2) {
            return (o1.getPopularity() - o2.getPopularity() > 0.0 ? 1 : (o1.getPopularity() - o2.getPopularity() < 0 ? -1 : 0));
        }

    };


}
