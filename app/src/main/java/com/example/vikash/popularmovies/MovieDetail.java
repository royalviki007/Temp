package com.example.vikash.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.utils.DateUtil;
import com.example.vikash.Api;
import com.example.vikash.Constant;
import com.example.vikash.model.DataModel;
import com.example.vikash.model.ResultModel;
import com.example.vikash.model.TrailerModel;

import java.text.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetail extends AppCompatActivity implements Callback<TrailerModel> {


    @Bind(R.id.movie_detail_name_id)
    TextView mMovieDetailName;

    @Bind(R.id.movie_detail_image_id)
    ImageView mMovieDetailImage;

    @Bind(R.id.movie_detail_year_id)
    TextView mMovieDetailYear;

    @Bind(R.id.movie_detail_rating_id)
    TextView mMovieDetailRating;
    @Bind(R.id.movie_detail_duration_id)
    TextView mMovieDetailDuration;

    @Bind(R.id.movie_detail_make_favorite_id)
    Button mMakeFavoBut;

    @Bind(R.id.movie_detail_overview)
    TextView mMovieDetailOverview;

    @Bind(R.id.movie_detail_trailerList)
    RecyclerView mMovieDetailTrailerList;

    ResultModel resultModel;

    LinearLayoutManager lLayout;

    TrailerAdapter mTrailerAdapter;
    TrailerModel mTrailerModel;
    private Call<TrailerModel> call;
    private Retrofit retrofit;
    private Api methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        resultModel = bundle.getParcelable(String.valueOf(R.string.data));
        if (resultModel != null) {
            mMovieDetailName.setText(resultModel.getOriginalTitle());
            try {
                mMovieDetailYear.setText(String.valueOf(DateUtil.getYear(resultModel.getReleaseDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mMovieDetailRating.setText(String.valueOf(resultModel.getVoteAverage()) + "/10");
            String url = Constant.BASE_URL_IMAGE_DOWNLOAD + "" + resultModel.getBackdropPath();
            Glide.with(getApplicationContext())
                    .load(url)
                    .centerCrop()
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mMovieDetailImage);
            mMovieDetailOverview.setText(resultModel.getOverview());
            lLayout = new LinearLayoutManager(MovieDetail.this);
            mMovieDetailTrailerList = (RecyclerView) findViewById(R.id.movie_detail_trailerList);
            mMovieDetailTrailerList.setLayoutManager(lLayout);
//            mMovieDetailTrailerList.setAdapter(new TrailerAdapter(getApplicationContext()), );
            getTrailerDetail();

        }


    }


    private void getTrailerDetail() {

//        retrofit = new retrofit2.Retrofit.Builder()
//                .baseUrl("http://api.themovhghgjhgjgjhgjhhgjjhiedb.org/3/")
//                .client(Logger.getInstance().getClient())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        methods = retrofit.create(Api.class);
//        call = methods.getTrailer(resultModel.getId(), Constant.API_KEY);
////        //asynchronous call
//        call.enqueue(this);
        call = ReFitInst.getInstance().getMethod().getTrailer(resultModel.getId(), Constant.API_KEY);
        //asynchronous call
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<TrailerModel> call, Response<TrailerModel> response) {

        if (response.code() != 404 && response.body() != null) {
            mTrailerModel = (TrailerModel) response.body();
            if (mTrailerAdapter == null) {
                mTrailerAdapter = new TrailerAdapter(getApplicationContext(), mTrailerModel);
            } else {
                mTrailerAdapter.setmTrailerModel(mTrailerModel);
                mTrailerAdapter.notifyDataSetChanged();
            }
            mMovieDetailTrailerList.setAdapter(mTrailerAdapter);

        }

    }

    @Override
    public void onFailure(Call<TrailerModel> call, Throwable t) {
        Log.i("TAG", "onFailure: ");

    }
}


