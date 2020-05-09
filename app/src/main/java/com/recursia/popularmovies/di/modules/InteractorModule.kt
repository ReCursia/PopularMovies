package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.domain.*

import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class InteractorModule {

    @Provides
    internal fun provideDetailScreenInteractor(
            moviesRepository: MoviesRepository,
            translateRepository: TranslateRepository
    ): DetailScreenInteractor {
        return DetailScreenInteractorImpl(moviesRepository, translateRepository)
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
