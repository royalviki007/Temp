package com.example.vikash.popularmovies;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by root on 7/3/16.
 */
public class Logger {
    private static Logger ourInstance = new Logger();
    private OkHttpClient client;

    public static Logger getInstance() {
        return ourInstance;
    }

    private Logger() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    }

    public OkHttpClient getClient() {
        return client;
    }
}
