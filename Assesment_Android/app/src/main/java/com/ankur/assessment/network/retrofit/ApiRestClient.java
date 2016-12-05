package com.ankur.assessment.network.retrofit;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ankur on 12/03/2016.
 */
class ApiRestClient {

    private static OkHttpClient httpClient;

    static {
        setupHttpClient();

    }

    private static void setupHttpClient() {
        ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {

            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return clazz == Field.class || clazz == Method.class;
            }
        };


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor).connectTimeout(100 * 100, TimeUnit.MILLISECONDS)
                .addInterceptor(httpLoggingInterceptor).readTimeout(100 * 100, TimeUnit.MILLISECONDS)
                .build();
    }


    static <T> T getApiService(Class<T> service, String baseURL) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient);
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(service);

    }
}
