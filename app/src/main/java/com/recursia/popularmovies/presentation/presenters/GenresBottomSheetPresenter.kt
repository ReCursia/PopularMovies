package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.GenresBottomSheetInteractor
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.presentation.views.contracts.GenresBottomSheetDialogContract
import com.recursia.popularmovies.utils.LangUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Genres movies bottom sheet
 */
@InjectViewState
class GenresBottomSheetPresenter(
        private val genresBottomSheetInteractor: GenresBottomSheetInteractor, // genres bottom sheet interactor
        private val router: Router, // router for navigating
        private val genreId: Int // genre id
) : MvpPresenter<GenresBottomSheetDialogContract>() {
    private var isLoading = false // is movies loading
    private var currentPage = 1 // current page, by default = 1
    private val compositeDisposable = CompositeDisposable() // for disposables on view detach

    /**
     * Calls on first view attach
     */
    override fun onFirstViewAttach() {
        val d = genresBottomSheetInteractor
                .getGenres(LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleGenres(it) },
                        { viewState.showErrorMessage(it.localizedMessage) }
                )
    }

    /**
     * Function to handle genres and set movies
     * @param genres genres
     */
    private fun handleGenres(genres: List<Genre>) {
        val genre = genres.find { it.id == genreId }
        viewState.setGenre(genre!!)
        val d = genresBottomSheetInteractor
                .getGenreMovies(genre, currentPage, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { viewState.setMovies(it) },
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
     * Calls on bottom list reached
     * @param genre genre to load then
     */
    fun bottomIsReached(genre: Genre) {
        if (!isLoading) {
            val d = genresBottomSheetInteractor
                    .getGenreMovies(genre, currentPage + 1, LangUtils.defaultLanguage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { isLoading = false }
                    .doOnSuccess { currentPage += 1 }
                    .doOnSubscribe { isLoading = true }
                    .subscribe(
                            { viewState.addMovies(it) },
                            { viewState.showErrorMessage(it.localizedMessage) }
                    )
            compositeDisposable.add(d)
        }
    }
}
