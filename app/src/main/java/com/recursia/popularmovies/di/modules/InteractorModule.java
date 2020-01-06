package com.recursia.popularmovies.di.modules;


import com.recursia.popularmovies.domain.DetailScreenInteractor;
import com.recursia.popularmovies.domain.DetailScreenInteractorImpl;
import com.recursia.popularmovies.domain.FavoriteScreenInteractor;
import com.recursia.popularmovies.domain.FavoriteScreenInteractorImpl;
import com.recursia.popularmovies.domain.MoviesListInteractor;
import com.recursia.popularmovies.domain.MoviesListInteractorImpl;
import com.recursia.popularmovies.domain.MoviesRepository;
import com.recursia.popularmovies.domain.SearchScreenInteractor;
import com.recursia.popularmovies.domain.SearchScreenInteractorImpl;

import dagger.Module;
import dagger.Provides;

@Module(includes = RepositoryModule.class)
public class InteractorModule {

    @Provides
    DetailScreenInteractor provideDetailScreenInteractor(MoviesRepository moviesRepository) {
        return new DetailScreenInteractorImpl(moviesRepository);
    }

    @Provides
    FavoriteScreenInteractor provideFavoriteScreenInteractor(MoviesRepository moviesRepository) {
        return new FavoriteScreenInteractorImpl(moviesRepository);
    }

    @Provides
    MoviesListInteractor provideMoviesListInteractor(MoviesRepository moviesRepository) {
        return new MoviesListInteractorImpl(moviesRepository);
    }

    @Provides
    SearchScreenInteractor provideSearchScreenInteractor(MoviesRepository moviesRepository) {
        return new SearchScreenInteractorImpl(moviesRepository);
    }
}
