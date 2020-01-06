package com.recursia.popularmovies.presentation.models;

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
