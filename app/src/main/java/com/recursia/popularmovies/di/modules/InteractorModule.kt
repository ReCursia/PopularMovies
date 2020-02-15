package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.domain.DetailScreenInteractor
import com.recursia.popularmovies.domain.DetailScreenInteractorImpl
import com.recursia.popularmovies.domain.FavoriteScreenInteractor
import com.recursia.popularmovies.domain.FavoriteScreenInteractorImpl
import com.recursia.popularmovies.domain.MoviesListInteractor
import com.recursia.popularmovies.domain.MoviesListInteractorImpl
import com.recursia.popularmovies.domain.MoviesRepository
import com.recursia.popularmovies.domain.SearchScreenInteractor
import com.recursia.popularmovies.domain.SearchScreenInteractorImpl

import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class InteractorModule {

    @Provides
    internal fun provideDetailScreenInteractor(moviesRepository: MoviesRepository): DetailScreenInteractor {
        return DetailScreenInteractorImpl(moviesRepository)
    }

    @Provides
    internal fun provideFavoriteScreenInteractor(moviesRepository: MoviesRepository): FavoriteScreenInteractor {
        return FavoriteScreenInteractorImpl(moviesRepository)
    }

    @Provides
    internal fun provideMoviesListInteractor(moviesRepository: MoviesRepository): MoviesListInteractor {
        return MoviesListInteractorImpl(moviesRepository)
    }

    @Provides
    internal fun provideSearchScreenInteractor(moviesRepository: MoviesRepository): SearchScreenInteractor {
        return SearchScreenInteractorImpl(moviesRepository)
    }
}
