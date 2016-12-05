package com.ankur.assessment.network;

import android.content.Context;

import com.ankur.assessment.model.UnAnswerQuestionResponse;
import com.ankur.assessment.network.retrofit.ApiService;

import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ankur on 12/03/2016.
 */

public class QuestionListIOManager {
    private static final String PAGE_SIZE = "10";
    private static final String TAGGED = "android";
    private static final String SITE = "stackoverflow";

    public static void getUnAnsweredQuestionFromServer(final int requestCode, int pageNo, String orderType, String sortType, final ResponseListener responseListener, Context context) {

        String url = NetworkUtils.UNANSWERED_QUESTION;
        HashMap<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(pageNo));
        params.put("pagesize", PAGE_SIZE);
        params.put("order", orderType);
        params.put("sort", sortType);
        params.put("tagged", TAGGED);
        params.put("site", SITE);

        Observable<UnAnswerQuestionResponse> observable =
                ApiService.get(url, params, UnAnswerQuestionResponse.class, false)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread());
        observable.subscribe(new AbstractNetworkObservable<UnAnswerQuestionResponse>() {
            @Override
            public void success(UnAnswerQuestionResponse responseModel) {
                if (responseModel != null) {
                    if (responseModel.getErrorStatus() == null && responseModel.getItems() != null)
                        responseListener.onResponse(requestCode, responseModel);
                    else
                        responseListener.onError(requestCode, responseModel);
                } else {
                    responseListener.onError(requestCode, null);
                }
            }

            @Override
            public void failure(Throwable error) {
                responseListener.onError(requestCode, null);
            }
        });

    }
}
