package com.recursia.popularmovies.data.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(
        foreignKeys = @ForeignKey(entity = MovieDatabaseModel.class,
                parentColumns = "id",
                childColumns = "movieId",
                onDelete = ForeignKey.CASCADE),
        primaryKeys = {"movieId", "name"}
)
public class CastDatabaseModel {
    private int movieId;
    @SerializedName("cast_id")
    @Expose
    private int castId;
    @SerializedName("character")
    @Expose
    private String character;
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("gender")
    @Expose
    private int gender;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    @NonNull
    private String name;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

}
