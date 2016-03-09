package com.example.vikash;

import com.example.vikash.model.DataModel;
import com.example.vikash.model.TrailerModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by root on 10/2/16.
 */
public interface Api {

    @GET("discover/movie")
    Call<DataModel> getMovieList(@Query("sort_by") String sort_by, @Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<DataModel> getTopRatedMovieList(@Query("sort_by") String sort_by, @Query("api_key") String api_key);

    @GET("movie/popular")
    Call<DataModel> getPopularMovieList(@Query("sort_by") String sort_by, @Query("api_key") String api_key);

    @GET("movie/{id}/videos")
    Call<TrailerModel> getTrailer(@Path("id") int trailerId, @Query("api_key") String api_key);
}
