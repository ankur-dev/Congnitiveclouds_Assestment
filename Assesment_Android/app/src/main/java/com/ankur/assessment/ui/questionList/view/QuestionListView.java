package com.ankur.assessment.ui.questionList.view;

import com.ankur.assessment.model.Item;
import com.ankur.assessment.view.BaseView;

import java.util.ArrayList;

/**
 * Created by Ankur on 12/04/2016.
 */

public interface QuestionListView extends BaseView {

    void showNoInternetView();

    void hideNoInternetLayout();

    void addToQuestionList(ArrayList<Item> itemArrayList);

    void showLazyPageLoading();

    void hideLazyPageLoading();

    void onPullToRefresh();
}
