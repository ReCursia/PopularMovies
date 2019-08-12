package com.example.popularmovies.models.pojo;

/*
POJO class for Intro activity
 */

import java.io.Serializable;

public class SectionItem implements Serializable {
    private int imageId;
    private String description;

    public SectionItem(int imageId, String description) {
        this.imageId = imageId;
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public String getDescription() {
        return description;
    }
}
