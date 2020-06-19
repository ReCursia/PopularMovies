package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.DetailScreenInteractor
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import com.recursia.popularmovies.presentation.views.contracts.DetailScreenContract
import com.recursia.popularmovies.utils.LangUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Detail screen presenter
 */
@InjectViewState
class DetailScreenPresenter(
        private val detailScreenInteractor: DetailScreenInteractor, //detail screen interactor
        private val router: Router, // router for navigating
        private val movieId: Int // movie id
) : MvpPresenter<DetailScreenContract>() {
    private val compositeDisposable = CompositeDisposable()

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
     * Calls on back pressed callback
     */
    fun onBackPressed() {
        router.exit()
    }

    /**
     * On share icon clicked callback
     * @param movie movie to share
     */
    fun onShareIconClicked(movie: Movie?) {
        movie?.let {
            viewState.shareMovie(movie)
        }
    }

    /**
     * On movie status clicked callback
     * save movie to user collection
     * @param movie movie
     * @param status status
     */
    fun onMovieStatusClicked(movie: Movie?, status: MovieStatus) {
        movie?.let {
            val d = detailScreenInteractor
                    .setMovieStatus(movie)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { movie.status = status }
                    .subscribe(
                            { viewState.setMovieStatus(status) },
                            { viewState.showErrorMessage(it.localizedMessage) }
                    )
            compositeDisposable.add(d)
        }
    }

    /**
     * On backdrop clicked callback
     * @param movie movie
     */
    fun onBackdropClicked(movie: Movie?) {
        movie?.let {
            router.navigateTo(Screens.PhotoScreen(movie.posterPath!!))
        }
    }

    /**
     * On genre clicked callback
     * @param genre genre
     */
    fun onGenreClicked(genre: Genre) {
        viewState.showBottomSheetGenreMovies(genre)
    }
}
