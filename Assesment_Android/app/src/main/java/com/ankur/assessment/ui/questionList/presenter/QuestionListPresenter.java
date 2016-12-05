package com.ankur.assessment.ui.questionList.presenter;

import android.content.Context;

import com.ankur.assessment.model.UnAnswerQuestionResponse;
import com.ankur.assessment.network.BaseResponse;
import com.ankur.assessment.network.QuestionListIOManager;
import com.ankur.assessment.network.ResponseListener;
import com.ankur.assessment.ui.questionList.view.QuestionListView;
import com.ankur.assessment.constant.AppConstants;
import com.ankur.assessment.util.NetworkUtility;

/**
 * Created by Ankur on 12/04/2016.
 */

public class QuestionListPresenter implements ResponseListener {

    private QuestionListView mQuestionListView;
    private Context mContext;
    private int mPageIndex;
    private String mOrderType = "";
    private String mSortType = "";
    private UnAnswerQuestionResponse mQuestionResponseCollection;

    public QuestionListPresenter(Context context, QuestionListView questionListView) {
        this.mContext = context;
        this.mQuestionListView = questionListView;
        mPageIndex = 1;
    }


    public void getQuestionListFromServer(String orderType, String sortType) {
        this.mOrderType = orderType;
        this.mSortType = sortType;

        mQuestionListView.showLazyPageLoading();
        if (NetworkUtility.isInternetOn(mContext)) {
            QuestionListIOManager.getUnAnsweredQuestionFromServer(AppConstants.QUESTION_LIST_REQUEST_CODE, mPageIndex, mOrderType, mSortType, this, mContext);
        } else {
            mQuestionListView.hideLazyPageLoading();
            mQuestionListView.showNoInternetView();
        }

    }

    /*
    * getting the server response when its success
    * @requestCode which we sent at time of making api call
    * @responseObject this is base object response which questionListResponse
    * */
    @Override
    public void onResponse(int requestCode, BaseResponse responseObject) {

        if (requestCode == AppConstants.QUESTION_LIST_REQUEST_CODE) {

            mQuestionResponseCollection = (UnAnswerQuestionResponse) responseObject;

            if (mQuestionResponseCollection.getItems() != null && mQuestionResponseCollection.getItems().size() > 0) {
                mQuestionListView.hideLazyPageLoading();
                mQuestionListView.addToQuestionList(mQuestionResponseCollection.getItems());
                mPageIndex++;
            } else {
                onError(requestCode, responseObject);
            }

        }
    }


    @Override
    public void onError(int responseCode, BaseResponse responseObject) {
        if (responseCode == AppConstants.QUESTION_LIST_REQUEST_CODE) {
            mQuestionListView.hideLazyPageLoading();
            mQuestionListView.showRetryView();
        }
    }

    public void loadMore() {
        if (mQuestionResponseCollection != null && mQuestionResponseCollection.isHasMore()) {
            getQuestionListFromServer(mOrderType, mSortType);
        }
    }

    /*for hwne user change sort type*/
    public void changeSortType(String sortType) {
        if (!mSortType.equalsIgnoreCase(sortType)) {
            mPageIndex = 1;
            getQuestionListFromServer(mOrderType, sortType);
        }
    }


    /*for when user change order type*/
    public void changeOrderType(String orderType) {
        if (!mOrderType.equalsIgnoreCase(orderType)) {
            mPageIndex = 1;
            getQuestionListFromServer(orderType, mSortType);
        }
    }
}
