package com.ankur.assessment.util;

import android.util.Log;

/**
 * Created by Ankur on 12/03/2016.
 */
public class LogUtil {

    private final static String LOG_TAG = "Assessment";
    public static boolean isLog = true;

    public static void i(String message) {
        if (isLog) {
            Log.i(LOG_TAG, message == null ? "info" : message);
        }
    }

    public static void w(String message) {
        if (isLog) {
            Log.i(LOG_TAG, message == null ? "warn" : message);
        }
    }

    public static void d(String message) {
        if (isLog) {
            Log.d(LOG_TAG, message == null ? "debug" : message);
        }
    }

    public static void e(String message, String e) {
        //if(isLog)
        Log.e(LOG_TAG, message == null ? "error" : message);
    }

    public static void e(String message) {
        if (isLog) {
            Log.e(LOG_TAG, message == null ? "error" : message);
        }
    }

    public static void d(String tag, String message) {
        if (isLog) {
            Log.d(tag, message == null ? "debug" : message);
        }
    }
}
