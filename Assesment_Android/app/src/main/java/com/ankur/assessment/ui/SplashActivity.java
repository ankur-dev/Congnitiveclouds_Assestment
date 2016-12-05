package com.ankur.assessment.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ankur.assessment.R;
import com.ankur.assessment.ui.questionList.QuestionsListActivity;

/*
 * Created by Ankur on 12/03/2016.
* */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, QuestionsListActivity.class));
                finish();
            }
        }, 2000);
    }
}
