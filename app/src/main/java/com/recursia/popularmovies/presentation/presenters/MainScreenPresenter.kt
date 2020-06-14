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

@InjectViewState
class MainScreenPresenter(
        private val mainScreenInteractor: MainScreenInteractor,
        private val authPreferences: AuthPreferences,
        private val router: Router
) : MvpPresenter<MainScreenContract>() {
    private val compositeDisposable = CompositeDisposable()
    private val currentPage = mutableMapOf<Category, Int>()
    private var isLoading = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initData()
    }

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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onAccountClicked() {
        val screen = if (authPreferences.isAuthorized) {
            Screens.AccountScreen()
        } else {
            Screens.WelcomeScreen()
        }
        router.navigateTo(screen)
    }

    fun onSearchItemClicked() {
        router.navigateTo(Screens.SearchScreen())
    }

    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }

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
