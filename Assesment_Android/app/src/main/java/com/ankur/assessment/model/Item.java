
package com.ankur.assessment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Questions")
public class Item extends Model implements Parcelable {

    @Column(name = "tags")
    @SerializedName("tags")
    @Expose
    private List<String> tags = new ArrayList<String>();

    @Column(name = "owner", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    @SerializedName("owner")
    @Expose
    private Owner owner;

    @Column(name = "isAnswered")
    @SerializedName("is_answered")
    @Expose
    private boolean isAnswered;

    @Column(name = "isFav")
    @SerializedName("isFav")
    @Expose
    private boolean isFav;

    @Column(name = "viewCount")
    @SerializedName("view_count")
    @Expose
    private int viewCount;

    @Column(name = "answerCount")
    @SerializedName("answer_count")
    @Expose
    private int answerCount;

    @Column(name = "score")
    @SerializedName("score")
    @Expose
    private int score;

    @Column(name = "lastActivityDate")
    @SerializedName("last_activity_date")
    @Expose
    private int lastActivityDate;

    @Column(name = "creationDate")
    @SerializedName("creation_date")
    @Expose
    private int creationDate;

    @Column(name = "questionId")
    @SerializedName("question_id")
    @Expose
    private int questionId;

    @Column(name = "link")
    @SerializedName("link")
    @Expose
    private String link;

    @Column(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @Column(name = "lastEditDate")
    @SerializedName("last_edit_date")
    @Expose
    private int lastEditDate;

    @Column(name = "tagString")
    @Expose
    private String tagString;

    public Item() {
        super();
    }

    protected Item(Parcel in) {
        tags = in.createStringArrayList();
        isAnswered = in.readByte() != 0;
        isFav = in.readByte() != 0;
        viewCount = in.readInt();
        answerCount = in.readInt();
        score = in.readInt();
        lastActivityDate = in.readInt();
        creationDate = in.readInt();
        questionId = in.readInt();
        link = in.readString();
        title = in.readString();
        tagString = in.readString();
        lastEditDate = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(tags);
        dest.writeByte((byte) (isAnswered ? 1 : 0));
        dest.writeByte((byte) (isFav ? 1 : 0));
        dest.writeInt(viewCount);
        dest.writeInt(answerCount);
        dest.writeInt(score);
        dest.writeInt(lastActivityDate);
        dest.writeInt(creationDate);
        dest.writeInt(questionId);
        dest.writeString(link);
        dest.writeString(tagString);
        dest.writeString(title);
        dest.writeInt(lastEditDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    /**
     * @return The tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @param tags The tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * @return The owner
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * @param owner The owner
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * @return The isAnswered
     */
    public boolean isIsAnswered() {
        return isAnswered;
    }

    /**
     * @param isAnswered The is_answered
     */
    public void setIsAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    /**
     * @return The viewCount
     */
    public int getViewCount() {
        return viewCount;
    }

    /**
     * @param viewCount The view_count
     */
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * @return The answerCount
     */
    public int getAnswerCount() {
        return answerCount;
    }

    /**
     * @param answerCount The answer_count
     */
    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    /**
     * @return The score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score The score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return The lastActivityDate
     */
    public int getLastActivityDate() {
        return lastActivityDate;
    }

    /**
     * @param lastActivityDate The last_activity_date
     */
    public void setLastActivityDate(int lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    /**
     * @return The creationDate
     */
    public int getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate The creation_date
     */
    public void setCreationDate(int creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return The questionId
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId The question_id
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The lastEditDate
     */
    public int getLastEditDate() {
        return lastEditDate;
    }

    /**
     * @param lastEditDate The last_edit_date
     */
    public void setLastEditDate(int lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public static List<Item> getFavQuestionListFromDataBase() {
        return new Select().from(Item.class).execute();
    }

    public static void deleteItemById(Item item) {
        try {
//            Item item = new Select().from(Item.class).where("id = ? ", itemId).executeSingle();
            item.getOwner().delete();
            item.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAlreadyAdded(int questionId) {
        List<Item> items = new Select().from(Item.class).execute();
        if(items==null || items.size()<=0){
            return false;
        }
        else {
            for (Item item : items) {
                if (item.questionId == questionId) {
                    return true;
                }
            }
        }
        return false;
    }

}
