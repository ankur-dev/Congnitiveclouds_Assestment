package com.ankur.assessment.common;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.ankur.assessment.model.Item;
import com.ankur.assessment.model.Owner;
import com.ankur.assessment.util.LogUtil;

/**
 * Created by Ankur on 12/03/2016.
 */

public class AppApplication extends Application {

    private AppApplication mApplicationContext;

    public AppApplication getApplicationContext() {
        return mApplicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;

        //for show the log
        LogUtil.isLog = true;

        // Initialized Active Android
        initializedActiveAndroid();


    }

    /*
    * initializing active android with two table Item and Owner*/
    private void initializedActiveAndroid() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(getApplicationContext());
        configurationBuilder.addModelClass(Item.class);
        configurationBuilder.addModelClass(Owner.class);
        ActiveAndroid.initialize(configurationBuilder.create());
    }
}
