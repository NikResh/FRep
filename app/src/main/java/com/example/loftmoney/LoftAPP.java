package com.example.loftmoney;

import android.app.Application;

import com.example.loftmoney.api.AuthorizationApi;
import com.example.loftmoney.api.LoftAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoftAPP extends Application {

    public LoftAPI loftAPI;
    public AuthorizationApi authAPI;
    public static String AUTH_KEY = "authKey";


    @Override
    public void onCreate() {
        super.onCreate();
        configureRetrofit();
    }

    private void configureRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        loftAPI = retrofit.create(LoftAPI.class);
        authAPI = retrofit.create(AuthorizationApi.class);
    }
}
