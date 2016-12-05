package com.ankur.assessment.common;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ankur.assessment.R;
import com.ankur.assessment.view.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * Created by Ankur on 12/03/2016.
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#} factory method to
 * create an instance of this fragment.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    @BindView(R.id.fragment_view_container)
    ViewGroup mContentContainer;

    @BindView(R.id.no_internetContent)
    public ViewGroup noInternetContent;

    @BindView(R.id.base_fragment_view_container)
    public ViewGroup mBaseFragmentContainer;
    @BindView(R.id.spinner_layout)
    LinearLayout mProgressLayout;
    @BindView(R.id.footer_error_layout)
    LinearLayout mFooterErrorLayout;
    @BindView(R.id.refresh_button)
    protected Button mRefreshButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        mContentContainer = (ViewGroup) view.findViewById(R.id.fragment_view_container);
        mBaseFragmentContainer = (ViewGroup) view.findViewById(R.id.base_fragment_view_container);
        return view;
    }

    protected void setLayout(LayoutInflater inflater, int layoutID) {
        if (inflater != null && layoutID != 0) {
            mContentContainer.addView(inflater.inflate(layoutID, null));
        }
        ButterKnife.bind(this, mBaseFragmentContainer);
    }

    protected void setLayoutWithoutBind(LayoutInflater inflater, int layoutID) {
        if (inflater != null && layoutID != 0) {
            mContentContainer.addView(inflater.inflate(layoutID, null));
        }
    }

    @Override
    public void showPageLoading() {
        mProgressLayout.setVisibility(View.VISIBLE);
        mFooterErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void hidePageLoading() {
        mProgressLayout.setVisibility(View.INVISIBLE);
        mFooterErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {

    }

    @Override
    public void hideContent() {

    }

    @Override
    public void showRetryView() {
        mProgressLayout.setVisibility(View.GONE);
        mFooterErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetryView() {
        mProgressLayout.setVisibility(View.INVISIBLE);
        mFooterErrorLayout.setVisibility(View.GONE);
    }
}