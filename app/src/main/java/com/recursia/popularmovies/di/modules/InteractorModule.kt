package com.recursia.popularmovies.di.modules

import com.recursia.popularmovies.domain.*

import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class InteractorModule {

    @Provides
    internal fun provideDetailScreenInteractor(accountRepository: AccountRepository, moviesRepository: MoviesRepository): DetailScreenInteractor {
        return DetailScreenInteractorImpl(accountRepository, moviesRepository)
    }

    @Provides
    internal fun provideMainScreenInteractor(moviesRepository: MoviesRepository): MainScreenInteractor {
        return MainScreenInteractorImpl(moviesRepository)
    }

    @Provides
    internal fun provideSearchScreenInteractor(moviesRepository: MoviesRepository): SearchScreenInteractor {
        return SearchScreenInteractorImpl(moviesRepository)
    }

    @Provides
    internal fun provideAccountScreenInteractor(accountRepository: AccountRepository): AccountScreenInteractor {
        return AccountScreenInteractorImpl(accountRepository)
    }
}
