package com.bayuirfan.madesubmission.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShowModel implements Parcelable {
    private String title;
    private String description;
    private String firstAired;
    private String score;
    private String genre;
    private String seasons;
    private int poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSeasons() {
        return seasons;
    }

    public void setSeasons(String seasons) {
        this.seasons = seasons;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.firstAired);
        dest.writeString(this.score);
        dest.writeString(this.genre);
        dest.writeString(this.seasons);
        dest.writeInt(this.poster);
    }

    public TvShowModel() {
    }

    private TvShowModel(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.firstAired = in.readString();
        this.score = in.readString();
        this.genre = in.readString();
        this.seasons = in.readString();
        this.poster = in.readInt();
    }

    public static final Creator<TvShowModel> CREATOR = new Creator<TvShowModel>() {
        @Override
        public TvShowModel createFromParcel(Parcel source) {
            return new TvShowModel(source);
        }

        @Override
        public TvShowModel[] newArray(int size) {
            return new TvShowModel[size];
        }
    };
}
