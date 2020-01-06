package com.recursia.popularmovies.di;

import com.recursia.popularmovies.di.modules.InteractorModule;
import com.recursia.popularmovies.di.modules.NavigationModule;
import com.recursia.popularmovies.domain.DetailScreenInteractor;
import com.recursia.popularmovies.domain.FavoriteScreenInteractor;
import com.recursia.popularmovies.domain.MoviesListInteractor;
import com.recursia.popularmovies.domain.SearchScreenInteractor;

import javax.inject.Singleton;

import dagger.Component;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Singleton
@Component(modules = {InteractorModule.class, NavigationModule.class})
public interface AppComponent {
    DetailScreenInteractor getDetailScreenInteractor();

    FavoriteScreenInteractor getFavoriteScreenInteractor();

    MoviesListInteractor getMoviesListInteractor();

    SearchScreenInteractor getSearchScreenInteractor();

    NavigatorHolder getNavigationHolder();

    Router getRouter();
}
