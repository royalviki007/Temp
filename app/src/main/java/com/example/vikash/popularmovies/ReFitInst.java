package com.example.vikash.popularmovies;

import com.example.vikash.Api;
import com.example.vikash.Constant;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 6/3/16.
 */
public class ReFitInst {
    private static ReFitInst ourInstance = new ReFitInst();
    private final retrofit2.Retrofit retrofit;
    private final Api methods;

    public static ReFitInst getInstance() {
        return ourInstance;
    }

    private ReFitInst() {
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(Logger.getInstance().getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        methods = retrofit.create(Api.class);
    }

    public Api getMethod() {
        return methods;
    }


}
