package com.example.popularmovies.models.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "placeholder")
public class Placeholder {
    @PrimaryKey(autoGenerate = true)
    int id;
}
