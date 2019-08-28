package com.recursia.popularmovies.models.pojo;

/*
POJO class for Intro activity
 */

import java.io.Serializable;

public class SectionItem implements Serializable {
    private final String fileName;
    private final String description;

    public SectionItem(String fileName, String description) {
        this.fileName = fileName;
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDescription() {
        return description;
    }

}

