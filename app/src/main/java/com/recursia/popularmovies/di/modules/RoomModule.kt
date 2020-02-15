package com.recursia.popularmovies.di.modules

import android.app.Application
import android.arch.persistence.room.Room

import com.recursia.popularmovies.data.db.MovieDao
import com.recursia.popularmovies.data.db.MovieDatabase

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class RoomModule(application: Application) {
    private val movieDatabase: MovieDatabase

    init {
        movieDatabase = Room.databaseBuilder(application, MovieDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    internal fun provideMovieDao(): MovieDao {
        return movieDatabase.movieDao()
    }

    companion object {
        private const val DB_NAME = "movies2.db"
    }
}
