package com.ankur.assessment.ui.questionList;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ankur.assessment.R;
import com.ankur.assessment.common.BaseFragment;
import com.ankur.assessment.interfaces.FragmentToActivityListener;
import com.ankur.assessment.model.Item;
import com.ankur.assessment.ui.questionList.adapter.QuestionListAdapter;
import com.ankur.assessment.util.DividerItemDecoration;
import com.ankur.assessment.view.CustomRecyclerViewWithFooter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FavQuestionListFragment extends BaseFragment {

    @BindView(R.id.rv_questionList)
    CustomRecyclerViewWithFooter rvQuestionList;
    @BindView(R.id.pullRefreshLayout)
    SwipeRefreshLayout mPullRefreshLayout;

    private ArrayList<Item> mQuestionArrayList;

    private QuestionListAdapter mRecyclerViewAdapter;
    private FragmentToActivityListener mActivityListener;

    public FavQuestionListFragment() {
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
        super.onCreateView(inflater, container, savedInstanceState);
        setLayout(inflater, R.layout.fragment_question_list);
        mQuestionArrayList = new ArrayList<>();
        setUpRecyclerView();
        mPullRefreshLayout.setEnabled(false);
        return mBaseFragmentContainer;
    }

    private void setUpRecyclerView() {

        mRecyclerViewAdapter = new QuestionListAdapter(getActivity(), mQuestionArrayList,mActivityListener ,this);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.recycler_divider));
        rvQuestionList.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(getActivity());
        rvQuestionList.setLayoutManager(rvLayoutManager);
        rvQuestionList.setAdapter(mRecyclerViewAdapter);

    }


    public void updateList(ArrayList<Item> updatedQuestionList) {
        if (updatedQuestionList != null) {
            hidePageLoading();
            mQuestionArrayList.clear();
            mQuestionArrayList.addAll(updatedQuestionList);
            mRecyclerViewAdapter.updateList(mQuestionArrayList);
        }
    }
}
