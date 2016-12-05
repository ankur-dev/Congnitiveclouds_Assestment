package com.ankur.assessment.network;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/*
 * Created by Ankur on 12/03/2016.
 */
public class CacheUpdateFunction<T> implements Func1<Response<ResponseBody>, Observable<T>> {
    private Class<T> clazz;
    private String url;
    boolean isCache;


    public CacheUpdateFunction(String url, Class<T> clazz, boolean isCache) {
        this.url = url;
        this.isCache = isCache;
        this.clazz = clazz;
    }

    @Override
    public Observable<T> call(Response<ResponseBody> networkResponse) {
        T response = null;
        try {
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

            Gson gson = new GsonBuilder()
                    .addSerializationExclusionStrategy(exclusionStrategy)
                    .addDeserializationExclusionStrategy(exclusionStrategy)
                    .create();

            String str = networkResponse.body().string();
            response = gson.fromJson(str, clazz);

            //   response = ParseUtil.getObject(str, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final T finalResponse = response;
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (finalResponse == null) {
                    subscriber.onError(new RuntimeException("Network response is null."));
                } else {
                    subscriber.onNext(finalResponse);
                }
                subscriber.onCompleted();
            }
        });
    }
}