package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.data.db.MovieDao
import com.recursia.popularmovies.data.network.MoviesApi
import com.recursia.popularmovies.data.repositories.AccountRepositoryImpl
import com.recursia.popularmovies.data.repositories.MoviesRepositoryImpl
import com.recursia.popularmovies.domain.AccountRepository
import com.recursia.popularmovies.domain.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    MoviesRetrofitModule::class,
    TranslateRetrofitModule::class,
    RoomModule::class,
    MapperModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideMoviesRepository(
            movieDao: MovieDao,
            moviesApi: MoviesApi
    ): MoviesRepository {
        return MoviesRepositoryImpl(
                movieDao,
                moviesApi)
    }


    @Provides
    @Singleton
    internal fun provideAccountRepository(): AccountRepository {
        return AccountRepositoryImpl()
    }

}
