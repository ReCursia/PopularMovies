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

@InjectViewState
class MovieDetailPresenter(
    private val detailScreenInteractor: DetailScreenInteractor,
    private val router: Router,
    private val movieId: Int
) : MvpPresenter<MovieDetailContract>() {
    private val compositeDisposable = CompositeDisposable()
    private var recommendationCurrentPage = 1
    private var isLoading = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initMovieData()
        initRecommendations()
    }

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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }

    fun onTrailerClicked(trailer: Trailer) {
        viewState.openTrailerUrl(trailer)
    }

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
