package com.example.vikash.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.vikash.Constant;
import com.example.vikash.model.DataModel;


import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPopularMovies extends AppCompatActivity implements Callback {


    @Bind(R.id.recycler_view_id)
    RecyclerView mRecyclerView;

    @Bind(R.id.progess_bar_id)
    ProgressBar mProgessBar;

    DataModel mDataModel;

    MovieAdapter mMovieAdapter;
    // Variable for retrofit.
//    Api methods;

    Call<DataModel> call;

    private GridLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_popular_movies);
        // Initialize recycler view
        lLayout = new GridLayoutManager(MainActivityPopularMovies.this, 2);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        mRecyclerView.setLayoutManager(lLayout);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getMovie();
    }

    private void getMovie() {
        call = ReFitInst.getInstance().getMethod().getMovieList(Constant.Movie.POPULARITY_DESC, Constant.API_KEY);
        //asynchronous call
        call.enqueue(this);
    }

    private void getPopularMovie() {
        call = ReFitInst.getInstance().getMethod().getPopularMovieList(Constant.Movie.POPULARITY_DESC, Constant.API_KEY);
        //asynchronous call
        call.enqueue(this);
    }

    private void getTopRattedMovie() {
        call = ReFitInst.getInstance().getMethod().getMovieList(Constant.Movie.VOTE_COUNT_DESC, Constant.API_KEY);
        //asynchronous call
        call.enqueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_popular_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_most_popular) {
            getPopularMovie();
//            sortBaseOnPopularity();
            return true;
        } else if (id == R.id.action_heighest_rated) {
            getTopRattedMovie();
//            sortBaseOnRatting();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortBaseOnRatting() {
        Collections.sort(mDataModel.getResults(), MovieAdapter.rattingComparator);
        if (mMovieAdapter != null) {
            mMovieAdapter.notifyDataSetChanged();
        }
    }

    private void sortBaseOnPopularity() {
        Collections.sort(mDataModel.getResults(), MovieAdapter.popularityComparator);
        if (mMovieAdapter != null) {
            mMovieAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onResponse(Call call, Response response) {
        if (response.code() != 404 && response.body() != null) {
            mDataModel = (DataModel) response.body();
            showGrid();
            if (mMovieAdapter == null) {
                mMovieAdapter = new MovieAdapter(getApplicationContext(), mDataModel);
            } else {
                mMovieAdapter.setmDataModel(mDataModel);
                mMovieAdapter.notifyDataSetChanged();
            }
            mRecyclerView.setAdapter(mMovieAdapter);

        }

    }

    private void showGrid() {
//        mRecyclerView.setVisibility(View.VISIBLE);
        mProgessBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}




















