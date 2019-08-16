package com.example.popularmovies.models.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.popularmovies.models.pojo.Placeholder;

@Database(entities = {Placeholder.class}, version = 17, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DB_NAME = "movies2.db";
    private static MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build(); //Fallback creates new db when updating a version
        }
        return instance;
    }

    public abstract MovieDao movieDao();

    public abstract TrailerDao trailerDao();

    public abstract GenreDao genreDao();

}

