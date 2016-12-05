
package com.ankur.assessment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "Owner")
public class Owner extends Model implements Parcelable {
    @Column(name = "reputation")
    @SerializedName("reputation")
    @Expose
    private int reputation;

    @Column(name = "userId")
    @SerializedName("user_id")
    @Expose
    private int userId;

    @Column(name = "userType")
    @SerializedName("user_type")
    @Expose
    private String userType;

    @Column(name = "acceptRate")
    @SerializedName("accept_rate")
    @Expose
    private int acceptRate;

    @Column(name = "profileImage")
    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    @Column(name = "displayName")
    @SerializedName("display_name")
    @Expose
    private String displayName;

    @Column(name = "link")
    @SerializedName("link")
    @Expose
    private String link;


    public Owner() {
        super();
    }

    protected Owner(Parcel in) {
        reputation = in.readInt();
        userId = in.readInt();
        userType = in.readString();
        acceptRate = in.readInt();
        profileImage = in.readString();
        displayName = in.readString();
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reputation);
        dest.writeInt(userId);
        dest.writeString(userType);
        dest.writeInt(acceptRate);
        dest.writeString(profileImage);
        dest.writeString(displayName);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    /**
     * @return The reputation
     */
    public int getReputation() {
        return reputation;
    }

    /**
     * @param reputation The reputation
     */
    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    /**
     * @return The userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return The userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType The user_type
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return The acceptRate
     */
    public int getAcceptRate() {
        return acceptRate;
    }

    /**
     * @param acceptRate The accept_rate
     */
    public void setAcceptRate(int acceptRate) {
        this.acceptRate = acceptRate;
    }

    /**
     * @return The profileImage
     */
    public String getProfileImage() {
        return profileImage;
    }

    /**
     * @param profileImage The profile_image
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * @return The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName The display_name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return The link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(String link) {
        this.link = link;
    }

}
