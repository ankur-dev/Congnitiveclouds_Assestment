package com.ankur.assessment.ui.questionList.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.ankur.assessment.model.Item;
import com.ankur.assessment.ui.questionList.FavQuestionListFragment;
import com.ankur.assessment.ui.questionList.QuestionListFragment;

import java.util.ArrayList;

/**
 * Created by Ankur on 12/04/2016.
 */

public class QuestionListPagerAdapter extends SaveFragmentStatePagerAdapter {

    private final String[] TITLES = {"Questions", "Fav"};

    public QuestionListPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new QuestionListFragment();
            case 1:
                return new FavQuestionListFragment();

        }
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
