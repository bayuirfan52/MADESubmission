package com.bayuirfan.madesubmission.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    private String title;
    private String description;
    private String yearAired;
    private String score;
    private String genre;
    private String duration;
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

    public String getYearAired() {
        return yearAired;
    }

    public void setYearAired(String yearAired) {
        this.yearAired = yearAired;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.yearAired);
        dest.writeString(this.score);
        dest.writeString(this.genre);
        dest.writeString(this.duration);
        dest.writeInt(this.poster);
    }

    public MovieModel() {
    }

    private MovieModel(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.yearAired = in.readString();
        this.score = in.readString();
        this.genre = in.readString();
        this.duration = in.readString();
        this.poster = in.readInt();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
