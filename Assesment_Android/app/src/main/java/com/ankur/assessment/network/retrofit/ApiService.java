package com.ankur.assessment.network.retrofit;

import com.ankur.assessment.network.CacheUpdateFunction;
import com.ankur.assessment.network.DefaultNetworkResponse;
import com.ankur.assessment.network.NetworkUtils;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Ankur on 12/03/2016.
 */
public class ApiService {
    /*
    * @url : url for particular api
    * @params : parameter which you want to send as a query string in url
    * @clazz : response class type
    * @isCache : if u want to cache the object then u have to send true else false*/
    public static <T extends DefaultNetworkResponse> Observable<T> get(final String url, Map<String, String> params, final Class<T> clazz, boolean isCache) {
        return ApiRestClient.getApiService(IApiService.class, NetworkUtils.BASE_URL).getWithParams(url, params).flatMap(new CacheUpdateFunction<T>(url, clazz, isCache)).subscribeOn(Schedulers.io());
    }


    private interface IApiService {

        // for simple get Api with params in url
        @GET
        Observable<Response<ResponseBody>> getWithParams(
                @Url String url, @QueryMap Map<String, String> params);
    }
}
