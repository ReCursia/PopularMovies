package com.example.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.pojo.Trailer;

@Database(entities = {Movie.class, Trailer.class}, version = 5, exportSchema = false)
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
}

