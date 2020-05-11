package com.recursia.popularmovies.di.modules

import android.app.Application
import androidx.room.Room
import com.recursia.popularmovies.data.db.MovieDao
import com.recursia.popularmovies.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
