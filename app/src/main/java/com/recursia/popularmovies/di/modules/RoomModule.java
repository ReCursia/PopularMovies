package com.recursia.popularmovies.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.recursia.popularmovies.data.db.MovieDao;
import com.recursia.popularmovies.data.db.MovieDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private static final String DB_NAME = "movies2.db";
    private final MovieDatabase movieDatabase;

    public RoomModule(Application application) {
        movieDatabase = Room.databaseBuilder(application, MovieDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    MovieDao provideMovieDao() {
        return movieDatabase.movieDao();
    }

}
