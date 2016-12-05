package com.ankur.assessment.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtility {

    /**
     * * Created by Ankur on 12/03/2016.
     * Provides whether internet is connected or not
     *
     * @param context
     * @return true if connected ,
     * else false
     */
    public static boolean isInternetOn(Context context) {
        if (context == null)
            return false;

        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }

        return false;
    }
}
