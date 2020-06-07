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

@InjectViewState
class GenresBottomSheetPresenter(
        private val genresBottomSheetInteractor: GenresBottomSheetInteractor,
        private val router: Router,
        private val genreId: Int
) : MvpPresenter<GenresBottomSheetDialogContract>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        val d = genresBottomSheetInteractor
                .getGenres(LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleGenres(it) },
                        { viewState.showErrorMessage(it.localizedMessage) }
                )
    }

    private fun handleGenres(genres: List<Genre>) {
        val genre = genres.find { it.id == genreId }
        viewState.setGenre(genre!!)
        val d = genresBottomSheetInteractor
                .getGenreMovies(genre, GENRE_MOVIES_PAGE, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { viewState.setMovies(it) },
                        { viewState.showErrorMessage(it.localizedMessage) }
                )
        compositeDisposable.add(d)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }

    companion object {
        private const val GENRE_MOVIES_PAGE = 1
    }
}
