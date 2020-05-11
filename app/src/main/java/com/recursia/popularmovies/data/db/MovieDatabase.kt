package com.recursia.popularmovies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.recursia.popularmovies.data.models.CastDatabaseModel
import com.recursia.popularmovies.data.models.GenreDatabaseModel
import com.recursia.popularmovies.data.models.MovieDatabaseModel
import com.recursia.popularmovies.data.models.TrailerDatabaseModel

@Database(entities = [MovieDatabaseModel::class, GenreDatabaseModel::class, TrailerDatabaseModel::class, CastDatabaseModel::class], version = 25, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
