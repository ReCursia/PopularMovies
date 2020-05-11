package com.recursia.popularmovies.di

import com.recursia.popularmovies.di.modules.InteractorModule
import com.recursia.popularmovies.di.modules.NavigationModule
import com.recursia.popularmovies.domain.AccountScreenInteractor
import com.recursia.popularmovies.domain.DetailScreenInteractor
import com.recursia.popularmovies.domain.MainScreenInteractor
import com.recursia.popularmovies.domain.SearchScreenInteractor
import dagger.Component
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Singleton
@Component(modules = [InteractorModule::class, NavigationModule::class])
interface AppComponent {
    val detailScreenInteractor: DetailScreenInteractor

    val mainScreenInteractor: MainScreenInteractor

    val searchScreenInteractor: SearchScreenInteractor

    val accountScreenInteractor: AccountScreenInteractor

    val navigationHolder: NavigatorHolder

    val router: Router
}
