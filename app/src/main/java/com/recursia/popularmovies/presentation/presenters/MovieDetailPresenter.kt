package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.DetailScreenInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.presentation.views.contracts.MovieDetailContract
import com.recursia.popularmovies.utils.LangUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router


/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Movie detail presenter
 */
@InjectViewState
class MovieDetailPresenter(
        private val detailScreenInteractor: DetailScreenInteractor, //detail screen interactor
        private val router: Router, // router for navigating
        private val movieId: Int // movie id
) : MvpPresenter<MovieDetailContract>() {
    private val compositeDisposable = CompositeDisposable() // for all disposables
    private var recommendationCurrentPage = 1 // recommendation current page
    private var isLoading = false //is loading

    /**
     * Calls on first view attach
     */
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initMovieData()
        initRecommendations()
    }

    /**
     * Function to init recommendations
     */
    private fun initRecommendations() {
        val d = detailScreenInteractor
                .getMovieRecommendations(movieId, recommendationCurrentPage, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { viewState.setRecommendationMovies(it) },
                        { viewState.showErrorMessage(it.localizedMessage) }
                )
        compositeDisposable.add(d)
    }

    /**
     * Function to init movie data
     */
    private fun initMovieData() {
        val d = detailScreenInteractor
                .getMovieById(movieId, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { viewState.setMovieDetail(it) },
                        { viewState.showErrorMessage(it.localizedMessage) }
                )
        compositeDisposable.add(d)
    }

    /**
     * Calls on destroy
     */
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    /**
     * On movie clicked callback
     * @param movie movie to navigate
     */
    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }

    /**
     * On trailer clicked
     * @param trailer trailer to open in browser
     */
    fun onTrailerClicked(trailer: Trailer) {
        viewState.openTrailerUrl(trailer)
    }

    /**
     * When end of list reached callback
     */
    fun rightIsReached() {
        if (!isLoading) {
            val d = detailScreenInteractor
                    .getMovieRecommendations(movieId, recommendationCurrentPage + 1, LangUtils.defaultLanguage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { isLoading = false }
                    .doOnSuccess { recommendationCurrentPage += 1 }
                    .doOnSubscribe { isLoading = true }
                    .subscribe(
                            { viewState.addRecommendationMovies(it) },
                            { viewState.showErrorMessage(it.localizedMessage) }
                    )
            compositeDisposable.add(d)
        }
    }
}
