package com.ankur.assessment.ui.questionList;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ankur.assessment.R;
import com.ankur.assessment.constant.AppConstants;
import com.ankur.assessment.interfaces.FragmentToActivityListener;
import com.ankur.assessment.model.Item;
import com.ankur.assessment.ui.questionList.adapter.QuestionListPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Created by Ankur on 12/04/2016.
* */
public class QuestionsListActivity extends AppCompatActivity implements FragmentToActivityListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private QuestionListPagerAdapter mPagerAdapter;
    private Menu mMenu;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);
        ButterKnife.bind(this);

        // setUpToolBar
        setUpToolBar();

        mPagerAdapter = new QuestionListPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabs.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
    }

    private void setUpToolBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.title_activity_question_list);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        mMenu = menu;
        // Retrieve the SearchView and plug it into SearchManager
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment fragment = mPagerAdapter.getRegisteredFragment(0);
                if (fragment instanceof QuestionListFragment)
                    ((QuestionListFragment) fragment).filterInQuestionList(newText);
                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Fragment fragment = mPagerAdapter.getRegisteredFragment(0);
                if (fragment instanceof QuestionListFragment)
                    ((QuestionListFragment) fragment).filterInQuestionList("");
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String orderType = "";
        String sortType = "";
        switch (AppConstants.SORT_TYPE.valueOf(item.getTitle().toString().toUpperCase())) {
            case ACTIVITY:
            case CREATION:
            case VOTES:
                sortType = item.getTitle().toString().toLowerCase();
                break;
            case DESCENDING:
                orderType = AppConstants.DESCENDING_ORDER;
                break;
            case ASCENDING:
                orderType = AppConstants.ASCENDING_ORDER;
                break;
            case SEARCH:
            default:
                return super.onOptionsItemSelected(item);

        }
        Fragment fragment = mPagerAdapter.getRegisteredFragment(0);
        if (fragment instanceof QuestionListFragment)
            ((QuestionListFragment) fragment).changeOrderAndSortType(orderType, sortType);
        return super.onOptionsItemSelected(item);
    }

    /*
    * save fav question item from data base
    * @item : which user want to add in future ref
    */
    @Override
    public void saveFavQuestionIntoDataBase(Item item) {
        if (!Item.isAlreadyAdded(item.getQuestionId())) {
            item.getOwner().save();
            item.save();
        }
    }

    /*
    * delete fav question item from data base
    * @item : which user want vto delete from database
    */
    @Override
    public void deleteFavQuestionFromDataBase(Item item) {
        Item.deleteItemById(item.getId());
    }

    @Override
    public void closeSearchLayout() {
        mSearchView.onActionViewCollapsed();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (position == 1) {
            mMenu.getItem(0).setVisible(false);
            mMenu.getItem(1).setVisible(false);
            Fragment fragment = mPagerAdapter.getRegisteredFragment(1);
            if (fragment instanceof FavQuestionListFragment) {
                Item item = new Item();
                ArrayList<Item> itemArrayList = new ArrayList<>();
                if (item.getFavQuestionListFromDataBase() != null) {
                    itemArrayList.addAll(item.getFavQuestionListFromDataBase());
                }
                ((FavQuestionListFragment) fragment).updateList(itemArrayList);
            }
        } else if (position == 0) {
            mMenu.getItem(0).setVisible(true);
            mMenu.getItem(1).setVisible(true);
            Fragment fragment = mPagerAdapter.getRegisteredFragment(1);
            if (fragment instanceof QuestionListFragment) {
                ((QuestionListFragment) fragment).updateList();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
