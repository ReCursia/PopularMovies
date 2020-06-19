package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.MainScreenInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.Category
import com.recursia.popularmovies.presentation.views.contracts.MainScreenContract
import com.recursia.popularmovies.utils.LangUtils
import com.recursia.popularmovies.utils.intro.AuthPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router


/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Main screen presenter
 */
@InjectViewState
class MainScreenPresenter(
        private val mainScreenInteractor: MainScreenInteractor, //main screen interactor
        private val authPreferences: AuthPreferences, // auth prefs to save persistent data
        private val router: Router // router for navigating
) : MvpPresenter<MainScreenContract>() {
    private val compositeDisposable = CompositeDisposable() // for all disposables when view detach
    private val currentPage = mutableMapOf<Category, Int>() // to control current pages of lists
    private var isLoading = false // is loading new movies

    /**
     * Calls on first view attach
     */
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initData()
    }

    /**
     * Function to init data
     */
    private fun initData() {
        for (category in Category.values()) {
            currentPage[category] = 1
            val d = mainScreenInteractor
                    .getMoviesWithCategory(category, currentPage[category]!!, LangUtils.defaultLanguage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { viewState.setCategoryMovies(it, category) },
                            { viewState.showErrorMessage(it.localizedMessage) }
                    )
            compositeDisposable.add(d)
        }
    }

    /**
     * Calls on destroy
     */
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    /**
     * On account clicked callback
     */
    fun onAccountClicked() {
        val screen = if (authPreferences.isAuthorized) {
            Screens.AccountScreen()
        } else {
            Screens.WelcomeScreen()
        }
        router.navigateTo(screen)
    }

    /**
     * On search item clicked callback
     */
    fun onSearchItemClicked() {
        router.navigateTo(Screens.SearchScreen())
    }

    /**
     * On movie clicked callback
     * @param movie movie to open
     */
    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }

    /**
     * Calls when end of list reached with category
     */
    fun rightIsReached(category: Category) {
        if (!isLoading) {
            val d = mainScreenInteractor
                    .getMoviesWithCategory(category, currentPage[category]!! + 1, LangUtils.defaultLanguage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { isLoading = false }
                    .doOnSuccess { currentPage[category] = currentPage[category]!!.plus(1) }
                    .doOnSubscribe { isLoading = true }
                    .subscribe(
                            { viewState.addCategoryMovies(it, category) },
                            { viewState.showErrorMessage(it.localizedMessage) }
                    )
            compositeDisposable.add(d)
        }
    }
}
