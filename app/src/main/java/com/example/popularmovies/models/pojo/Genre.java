package com.example.popularmovies.models.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
POJO class with Room and GSON annotation
 */

@Entity(tableName = "genres")
public class Genre {
    @PrimaryKey(autoGenerate = true)
    private int tableId;

    private int movieId;
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
