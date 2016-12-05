package com.ankur.assessment.ui.questionList;


import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ankur.assessment.R;
import com.ankur.assessment.common.BaseFragment;
import com.ankur.assessment.interfaces.FragmentToActivityListener;
import com.ankur.assessment.model.Item;
import com.ankur.assessment.ui.questionList.adapter.QuestionListAdapter;
import com.ankur.assessment.ui.questionList.presenter.QuestionListPresenter;
import com.ankur.assessment.ui.questionList.view.QuestionListView;
import com.ankur.assessment.util.DividerItemDecoration;
import com.ankur.assessment.util.LogUtil;
import com.ankur.assessment.view.CustomRecyclerViewWithFooter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class QuestionListFragment extends BaseFragment implements QuestionListView {


    @BindView(R.id.rv_questionList)
    CustomRecyclerViewWithFooter mRVQuestionList;
    @BindView(R.id.tv_noNetworkText)
    TextView tvNoNetworkText;
    @BindView(R.id.no_network_strip)
    RelativeLayout noNetworkStrip;
    @BindView(R.id.no_internetContent)
    RelativeLayout mNoInternetView;


    LinearLayout mFooterLoaderLayout;
    LinearLayout mFooterErrorLayout;
    @BindView(R.id.pullRefreshLayout)
    SwipeRefreshLayout mPullRefreshLayout;
    private View mFooterLoadingView;

    private QuestionListPresenter mPresenter;
    private ArrayList<Item> mQuestionArrayList;

    private String mOrderType = "";
    private String mSortType = "";
    private LinearLayoutManager mRVLayoutManager;
    private QuestionListAdapter mRecyclerViewAdapter;

    private boolean mLoadNext;
    private FragmentToActivityListener mActivityListener;


    public QuestionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivityListener = (FragmentToActivityListener) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        setLayout(inflater, R.layout.fragment_question_list);
        mQuestionArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpPullToRefresh();
        setUpFooterLayout(inflater);
        mPresenter = new QuestionListPresenter(getActivity(), this);
        mOrderType = "desc";
        mSortType = "activity";
        mPresenter.getQuestionListFromServer(mOrderType, mSortType);
        return mBaseFragmentContainer;
    }

    private void setUpPullToRefresh() {
        mPullRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                mPresenter.onRefresh();
            }
        });
        // Configure the refreshing colors
        mPullRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    /*initialized recycler view
    * and register recycler view scroll listener*/
    private void setUpRecyclerView() {
        mRecyclerViewAdapter = new QuestionListAdapter(getActivity(), mQuestionArrayList,mActivityListener ,this);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.recycler_divider));
        mRVQuestionList.addItemDecoration(dividerItemDecoration);
        mRVLayoutManager = new LinearLayoutManager(getActivity());
        mRVQuestionList.setLayoutManager(mRVLayoutManager);
        mRVQuestionList.setAdapter(mRecyclerViewAdapter);

        mRVQuestionList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount, totalItemCount;
                int pastVisibleItem;
                if (dy > 0) {/*check for scroll down*/
                    visibleItemCount = mRVLayoutManager.getChildCount();
                    totalItemCount = mRVLayoutManager.getItemCount();
                    pastVisibleItem = mRVLayoutManager.findFirstVisibleItemPosition();

                    if (mLoadNext) {//Check to load next page
                        if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                            mPresenter.loadMore();
                            mLoadNext = false;
                        }
                    }
                }
            }
        });
    }

    /*
    adding question list to mQuestionArrayList
    * itemArrayList: question list from server
    * */
    @Override
    public void addToQuestionList(ArrayList<Item> itemArrayList) {

        if (itemArrayList != null) {
            mQuestionArrayList.addAll(itemArrayList);
            mRecyclerViewAdapter.updateList(mQuestionArrayList);
            addListButtonFooter();
            mLoadNext = true;
            LogUtil.d("ankur", "dataa come");
        }
    }

    /* show page loading
    * if it is first time then showing loader at center of screen
    *
    * else showing inline loader as footer of list */
    @Override
    public void showLazyPageLoading() {
        if (mQuestionArrayList != null && mQuestionArrayList.size() > 0) {
            mFooterLoadingView.setVisibility(View.VISIBLE);
            mFooterLoaderLayout.setVisibility(View.VISIBLE);
            mFooterErrorLayout.setVisibility(View.GONE);
        } else {
            showPageLoading();
        }
    }

    /* hide page loading

    * if it is first time then hiding loader at center of screen
    * else hiding inline loader as footer of list */
    @Override
    public void hideLazyPageLoading() {
        if (mQuestionArrayList != null && mQuestionArrayList.size() > 0) {
            mFooterLoadingView.setVisibility(View.GONE);
        } else {
            hidePageLoading();
        }
    }

    @Override
    public void onPullToRefresh() {
        mQuestionArrayList.clear();
        mRecyclerViewAdapter.updateList(mQuestionArrayList);
        mPullRefreshLayout.setRefreshing(false);
        mActivityListener.closeSearchLayout();
    }

    /*
    * handel click of Retry button
    * */
    @OnClick(R.id.refresh_internet_button)
    public void onClick() {
        hideNoInternetLayout();
        mPresenter.getQuestionListFromServer(mOrderType, mSortType);
    }

    /* show error layout
    * if it is first time then showing error layout at whole screen
    *
    * else showing
    * as footer of list */
    @Override
    public void showNoInternetView() {
        if (mQuestionArrayList != null && mQuestionArrayList.size() > 0) {
            mFooterLoadingView.setVisibility(View.VISIBLE);
            mFooterErrorLayout.setVisibility(View.VISIBLE);
            mFooterLoaderLayout.setVisibility(View.GONE);

        } else {
            mRVQuestionList.setVisibility(View.INVISIBLE);
            mNoInternetView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideNoInternetLayout() {
        if (mQuestionArrayList != null && mQuestionArrayList.size() > 0) {
            mFooterLoadingView.setVisibility(View.GONE);

        } else {
            mNoInternetView.setVisibility(View.INVISIBLE);
            mRVQuestionList.setVisibility(View.VISIBLE);
        }

    }

    /* change list according to type of sort and order
     * @orderType is describe that order of list in desc and asc order
     *
     * @sortType is type of sort which is selected by user from menu
     * */
    public void changeOrderAndSortType(String orderType, String sortType) {
        if (!TextUtils.isEmpty(sortType))
            mSortType = sortType;

        if (!TextUtils.isEmpty(orderType))
            mOrderType = orderType;

        mRecyclerViewAdapter.clearList();
        mQuestionArrayList.clear();

        mPresenter.getQuestionListFromServer(mOrderType, mSortType);
    }

    public void updateList() {
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    /*initialized Footer layout for recycler view*/
    private void setUpFooterLayout(LayoutInflater inflater) {
        mFooterLoadingView = inflater.inflate(R.layout.custom_progress_bar_large, null);
        mFooterLoaderLayout = (LinearLayout) mFooterLoadingView.findViewById(R.id.spinner_layout);
        mFooterErrorLayout = (LinearLayout) mFooterLoadingView.findViewById(R.id.footer_error_layout);
        mFooterLoadingView.setMinimumWidth(getDeviceScreenWidth(getActivity()));
        mFooterLoadingView.setVisibility(View.INVISIBLE);

        Button footerRefreshButton = (Button) mFooterLoadingView.findViewById(R.id.refresh_button);
        footerRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideNoInternetLayout();
                mPresenter.loadMore();
            }
        });
    }

    private int getDeviceScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /*adding loading and error layout in recycler view as a footer */
    public void addListButtonFooter() {
        //Check if already added
        if (mRVQuestionList.getFooterViewsCount() == 0) {
            mRVQuestionList.addFooterView(mFooterLoadingView);
        }
    }

    /*filter the question list base on search string
    * searchString
     * */
    public void filterInQuestionList(String searchString) {
        if (!TextUtils.isEmpty(searchString)) {
            ArrayList<Item> filteredQuestionList = new ArrayList<>();

            for (Item item : mQuestionArrayList) {
                if (item.getTags() != null) {
                    for (String string : item.getTags()) {
                        if (string.toLowerCase().contains(searchString.toLowerCase()))
                            filteredQuestionList.add(item);
                    }
                }
            }
            mRecyclerViewAdapter.updateList(filteredQuestionList);
        } else {
            mRecyclerViewAdapter.updateList(mQuestionArrayList);
        }
    }

}
