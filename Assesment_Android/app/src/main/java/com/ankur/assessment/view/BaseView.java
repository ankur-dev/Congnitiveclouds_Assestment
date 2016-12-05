package com.ankur.assessment.view;

/**
 * Created by Ankur on 12/04/2016.
 */

public interface BaseView {

    void showPageLoading();

    void hidePageLoading();

    void showContent();

    void hideContent();

    void showRetryView();

    void hideRetryView();
}
