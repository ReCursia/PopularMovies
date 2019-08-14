package com.example.popularmovies.models.pojo;

/*
POJO class for Intro activity
 */

import java.io.Serializable;

public class SectionItem implements Serializable {
    private String fileName;
    private String description;

    public String getFileName() {
        return fileName;
    }

    public String getDescription() {
        return description;
    }

    public SectionItem(String fileName, String description) {
        this.fileName = fileName;
        this.description = description;
    }

}

