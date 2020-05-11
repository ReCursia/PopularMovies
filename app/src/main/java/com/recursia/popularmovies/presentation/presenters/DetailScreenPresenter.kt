package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.DetailScreenInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import com.recursia.popularmovies.presentation.views.contracts.DetailScreenContract
import com.recursia.popularmovies.utils.LangUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class DetailScreenPresenter(
        private val detailScreenInteractor: DetailScreenInteractor,
        private val router: Router,
        private val movieId: Int
) : MvpPresenter<DetailScreenContract>() {
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initData()
    }

    private fun initData() {
        val d = detailScreenInteractor
                .getMovieById(movieId, LangUtils.defaultLanguage)
                .subscribeOn(Schedulers.io())
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

    fun onBackPressed() {
        router.exit()
    }

    fun onShareIconClicked(movie: Movie?) {
        movie?.let {
            viewState.shareMovie(movie)
        }
    }

    fun onMovieStatusClicked(movie: Movie?, status: MovieStatus) {
        movie?.let {
            val d = detailScreenInteractor
                    .setMovieStatus(movie)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { movie.status = status }
                    .subscribe(
                            { viewState.setMovieStatus(status) },
                            { viewState.showErrorMessage(it.localizedMessage) }
                    )
            compositeDisposable.add(d)
        }
    }

    fun onBackdropClicked(movie: Movie?) {
        movie?.let {
            router.navigateTo(Screens.PhotoScreen(movie.backdropPath!!))
        }
    }

}
