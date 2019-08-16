package com.example.popularmovies.models.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
POJO class with Room and GSON annotation
 */

//@Entity(tableName = "genres")
public class Genre {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
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

}
