package com.recursia.popularmovies.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(
        foreignKeys = @ForeignKey(entity = MovieDatabaseModel.class,
                parentColumns = "id",
                childColumns = "movieId",
                onDelete = ForeignKey.CASCADE),
        primaryKeys = {"movieId", "name"}
)
public class GenreDatabaseModel {
    private int movieId;

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    @NonNull
    private String name;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

}