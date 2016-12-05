package com.ankur.assessment.ui.questionList.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankur.assessment.R;
import com.ankur.assessment.interfaces.AdapterToActivityListener;
import com.ankur.assessment.model.Item;
import com.ankur.assessment.ui.questionList.FavQuestionListFragment;
import com.squareup.picasso.Picasso;
import com.ankur.assessment.ui.questionList.QuestionListFragment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ankur on 12/04/2016.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> mQuestionList;
    private Context mContext;
    private static final String DATE_FORMAT = "dd MMM, yyyy HH:mm";
    private AdapterToActivityListener mActivityListener;
    private Fragment mFragment;

    public QuestionListAdapter(Context context, @NonNull List<Item> items, Fragment fragment) {
        this.mQuestionList = items;
        mContext = context;
        mActivityListener = (AdapterToActivityListener) context;
        mFragment = fragment;
    }


    public void clearList() {
        mQuestionList.clear();
        notifyDataSetChanged();
    }

    public List<Item> getQuestionListFromAdapter() {
        return mQuestionList;
    }

    public void updateList(ArrayList<Item> questionList) {
        mQuestionList = questionList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.question_list_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ViewHolder customViewHolder = (ViewHolder) viewHolder;

        if (mQuestionList.get(position) != null) {
            if (mFragment instanceof QuestionListFragment)
                mQuestionList.get(position).setTagString(getTaggedString(mQuestionList.get(position).getTags()));

            setDataToTextView(customViewHolder.questionTitleTv, mQuestionList.get(position).getTitle());
            setDataToTextView(customViewHolder.timeStamp, getDataFormat(mQuestionList.get(position).getCreationDate()));
            setDataToTextView(customViewHolder.tagLine1, mQuestionList.get(position).getTagString());
            if (mQuestionList.get(position).getOwner() != null && !TextUtils.isEmpty(mQuestionList.get(position).getOwner().getProfileImage()))
                Picasso.with(mContext).load(mQuestionList.get(position).getOwner().getProfileImage()).into(customViewHolder.userImage);

            setDataToTextView(customViewHolder.ratingValueTv, String.valueOf(mQuestionList.get(position).getScore()));

            if (mQuestionList.get(position).isFav()) {
                customViewHolder.likeButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gery_color));
            } else {
                customViewHolder.likeButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            }
        }
    }


    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.time_stamp)
        TextView timeStamp;
        @BindView(R.id.user_image)
        ImageView userImage;
        @BindView(R.id.question_title_tv)
        TextView questionTitleTv;
        @BindView(R.id.tagLine1)
        TextView tagLine1;
        @BindView(R.id.rating_value_tv)
        TextView ratingValueTv;
        @BindView(R.id.like_button)
        Button likeButton;
        @BindView(R.id.share_button)
        Button shareButton;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

        }

        @OnClick({R.id.share_button, R.id.like_button})
        public void onClickButton(View view) {
            switch (view.getId()) {
                case R.id.share_button:
                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, mQuestionList.get(getAdapterPosition()).getLink());
                    mContext.startActivity(Intent.createChooser(share, mContext.getString(R.string.share_link_header)));
                    break;
                case R.id.like_button:
                    if (mQuestionList.get(getAdapterPosition()).isFav()) {
                        mQuestionList.get(getAdapterPosition()).setFav(false);
                        likeButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                        mActivityListener.deleteFavQuestionFromDataBase(mQuestionList.get(getAdapterPosition()));

                        if (mFragment instanceof FavQuestionListFragment) {
                            mQuestionList.remove(getAdapterPosition());
                        }
                        notifyDataSetChanged();

                    } else {
                        mQuestionList.get(getAdapterPosition()).setFav(true);
                        likeButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gery_color));
                        mActivityListener.saveFavQuestionIntoDataBase(mQuestionList.get(getAdapterPosition()));
                        notifyDataSetChanged();
                    }
                    break;

            }

        }

        @Override
        public void onClick(View v) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mQuestionList.get(getAdapterPosition()).getLink()));
            mContext.startActivity(browserIntent);
        }
    }

    /*
       * check for string data is null or not
       * if not then showing on ui
        * */
    private void setDataToTextView(TextView textView, String dataString) {
        if (!TextUtils.isEmpty(dataString))
            textView.setText(dataString);

    }

    /*
    * converting the time stamp in to date string which we have to show on ui
     * */
    private String getDataFormat(long timeStamp) {
        Date date = new Date(timeStamp * 1000);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    /* converting list of tags into a string for display
    *@tagList : tag list which is comming from server
     * @taggedString: which we have to show on ui
      * */
    private String getTaggedString(List<String> tagList) {
        StringBuilder taggedString = new StringBuilder();
        if (tagList != null && tagList.size() > 0) {
            for (String tag : tagList) {
                taggedString.append("#");
                taggedString.append(tag);
                taggedString.append(" ");
            }
        }
        return taggedString.toString();
    }
}
