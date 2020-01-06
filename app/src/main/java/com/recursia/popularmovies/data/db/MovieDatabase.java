package com.recursia.popularmovies.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.recursia.popularmovies.data.models.CastDatabaseModel;
import com.recursia.popularmovies.data.models.GenreDatabaseModel;
import com.recursia.popularmovies.data.models.MovieDatabaseModel;
import com.recursia.popularmovies.data.models.TrailerDatabaseModel;

@Database(entities = {
        MovieDatabaseModel.class,
        GenreDatabaseModel.class,
        TrailerDatabaseModel.class,
        CastDatabaseModel.class}, version = 25, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

}

