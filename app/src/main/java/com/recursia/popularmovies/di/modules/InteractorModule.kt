package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.domain.*

import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class InteractorModule {

    @Provides
    internal fun provideDetailScreenInteractor(): DetailScreenInteractor {
        return DetailScreenInteractorImpl()
    }

    @Provides
    internal fun provideMoviesListInteractor(): MainScreenInteractor {
        return MainScreenInteractorImpl()
    }

    @Provides
    internal fun provideSearchScreenInteractor(moviesRepository: MoviesRepository): SearchScreenInteractor {
        return SearchScreenInteractorImpl(moviesRepository)
    }
}
