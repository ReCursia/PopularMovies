package com.recursia.popularmovies.presentation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.MainScreenInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.Category
import com.recursia.popularmovies.presentation.views.contracts.MainScreenContract
import com.recursia.popularmovies.utils.LangUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

@InjectViewState
class MainScreenPresenter(
        private val mainScreenInteractor: MainScreenInteractor,
        private val router: Router
) : MvpPresenter<MainScreenContract>() {
    private val compositeDisposable = CompositeDisposable()
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initData()
    }

    private fun initData() {
        for (category in Category.values()) {
            val d = mainScreenInteractor
                    .getMoviesWithCategory(category, LangUtils.defaultLanguage)
                    .subscribeOn(Schedulers.io())
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
        router.navigateTo(Screens.WelcomeScreen())
    }

    fun onSearchItemClicked() {
        router.navigateTo(Screens.SearchScreen())
    }

    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }
}