
package com.ankur.assessment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ankur.assessment.network.BaseResponse;
import com.ankur.assessment.network.DefaultNetworkResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UnAnswerQuestionResponse extends BaseResponse implements Parcelable, DefaultNetworkResponse {

    @SerializedName("items")
    @Expose
    private ArrayList<Item> items = new ArrayList<Item>();
    @SerializedName("has_more")
    @Expose
    private boolean hasMore;
    @SerializedName("quota_max")
    @Expose
    private int quotaMax;
    @SerializedName("quota_remaining")
    @Expose
    private int quotaRemaining;

    @SerializedName("error_id")
    private String errorStatus;
    @SerializedName("error_message")
    private String errorMsg;
    @SerializedName("error_name")
    private String errorName;


    public UnAnswerQuestionResponse(Parcel in) {
        items = in.createTypedArrayList(Item.CREATOR);
        hasMore = in.readByte() != 0;
        quotaMax = in.readInt();
        quotaRemaining = in.readInt();
        errorStatus = in.readString();
        errorMsg = in.readString();
        errorName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
        dest.writeByte((byte) (hasMore ? 1 : 0));
        dest.writeInt(quotaMax);
        dest.writeInt(quotaRemaining);
        dest.writeString(errorStatus);
        dest.writeString(errorMsg);
        dest.writeString(errorName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UnAnswerQuestionResponse> CREATOR = new Creator<UnAnswerQuestionResponse>() {
        @Override
        public UnAnswerQuestionResponse createFromParcel(Parcel in) {
            return new UnAnswerQuestionResponse(in);
        }

        @Override
        public UnAnswerQuestionResponse[] newArray(int size) {
            return new UnAnswerQuestionResponse[size];
        }
    };

    /**
     * @return The items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * @return The hasMore
     */
    public boolean isHasMore() {
        return hasMore;
    }

    /**
     * @param hasMore The has_more
     */
    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    /**
     * @return The quotaMax
     */
    public int getQuotaMax() {
        return quotaMax;
    }

    /**
     * @param quotaMax The quota_max
     */
    public void setQuotaMax(int quotaMax) {
        this.quotaMax = quotaMax;
    }

    /**
     * @return The quotaRemaining
     */
    public int getQuotaRemaining() {
        return quotaRemaining;
    }

    /**
     * @param quotaRemaining The quota_remaining
     */
    public void setQuotaRemaining(int quotaRemaining) {
        this.quotaRemaining = quotaRemaining;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

}
