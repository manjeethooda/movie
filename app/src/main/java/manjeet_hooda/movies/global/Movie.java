package manjeet_hooda.movies.global;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.PortUnreachableException;
import java.util.Date;

import manjeet_hooda.movies.fragment.Search;

/**
 * Created by manjeet on 16/4/16.
 */
public class Movie implements Parcelable {

    private String mTitle;
    private Date mDate;
    private int mAudienceScore;
    private String mSynopsis;
    private String mUrlThumbnail;
    private String mUrlSelf;
    private String mUrlCast;
    private String mUrlReviews;
    private String mUrlSimiliar;

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(Parcel input) {
        mTitle = input.readString();
        long dateMillis=input.readLong();
        mDate = (dateMillis == -1 ? null : new Date(dateMillis));
        mAudienceScore = input.readInt();
        mSynopsis = input.readString();
        mUrlThumbnail = input.readString();
        mUrlSelf = input.readString();
        mUrlCast = input.readString();
        mUrlReviews = input.readString();
        mUrlSimiliar = input.readString();
    }

    public Movie(String title,
                 Date date,
                 int audsc,
                 String synopsis,
                 String urlThumb,
                 String urlSelf,
                 String urlcast,
                 String urlreview,
                 String mUrlSimiliar
    ){
        this.mTitle = title;
        this.mDate = date;
        this.mAudienceScore = audsc;
        this.mSynopsis = synopsis;
        this.mUrlThumbnail = urlThumb;
        this.mUrlSelf = urlSelf;
        this.mUrlCast = urlcast;
        this.mUrlReviews = urlreview;
        this.mUrlSimiliar = mUrlSimiliar;
    }

    public Movie(){

    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getmAudienceScore() {
        return mAudienceScore;
    }

    public void setmAudienceScore(int mAudienceScore) {
        this.mAudienceScore = mAudienceScore;
    }

    public String getmSynopsis() {
        return mSynopsis;
    }

    public void setmSynopsis(String mSynopsis) {
        this.mSynopsis = mSynopsis;
    }

    public String getmUrlThumbnail() {
        return mUrlThumbnail;
    }

    public void setmUrlThumbnail(String mUrlThumbnail) {
        this.mUrlThumbnail = mUrlThumbnail;
    }

    public String getmUrlSelf() {
        return mUrlSelf;
    }

    public void setmUrlSelf(String mUrlSelf) {
        this.mUrlSelf = mUrlSelf;
    }

    public String getmUrlCast() {
        return mUrlCast;
    }

    public void setmUrlCast(String mUrlCast) {
        this.mUrlCast = mUrlCast;
    }

    public String getmUrlReviews() {
        return mUrlReviews;
    }

    public void setmUrlReviews(String mUrlReviews) {
        this.mUrlReviews = mUrlReviews;
    }

    public String getmUrlSimiliar() {
        return mUrlSimiliar;
    }

    public void setmUrlSimiliar(String mUrlSimiliar) {
        this.mUrlSimiliar = mUrlSimiliar;
    }

    @Override
    public String toString() {
        return  "\nTitle " + mTitle +
                "\nDate " + mDate +
                "\nSynopsis " + mSynopsis +
                "\nScore " + mAudienceScore +
                "\nurlThumbnail " + mUrlThumbnail +
                "\nurlSelf " + mUrlSelf +
                "\nurlCast " + mUrlCast +
                "\nurlReviews " + mUrlReviews +
                "\nurlSimilar " + mUrlSimiliar +
                "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeLong(mDate == null ? -1 : mDate.getTime());
        dest.writeInt(mAudienceScore);
        dest.writeString(mSynopsis);
        dest.writeString(mUrlThumbnail);
        dest.writeString(mUrlSelf);
        dest.writeString(mUrlCast);
        dest.writeString(mUrlReviews);
        dest.writeString(mUrlSimiliar);

    }


}
