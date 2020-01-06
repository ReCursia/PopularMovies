package com.recursia.popularmovies.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailersResponse {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<TrailerDatabaseModel> trailers = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerDatabaseModel> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailerDatabaseModel> trailers) {
        this.trailers = trailers;
    }

}