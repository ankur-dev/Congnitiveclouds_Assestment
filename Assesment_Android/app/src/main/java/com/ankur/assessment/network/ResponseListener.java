package com.ankur.assessment.network;

/**
 * Created by Ankur on 12/03/2016.
 */
public interface ResponseListener {

    void onResponse(int requestCode, BaseResponse responseObject);

    void onError(int responseCode, BaseResponse responseObject);
}
