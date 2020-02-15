package com.recursia.popularmovies.presentation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.MoviesListInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.presentation.views.contracts.MoviesListContract
import com.recursia.popularmovies.utils.LangUtils
import com.recursia.popularmovies.utils.discover.DiscoverStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

@InjectViewState
class MoviesListPresenter(private val moviesListInteractor: MoviesListInteractor, private val discoverStrategy: DiscoverStrategy, private val router: Router) : MvpPresenter<MoviesListContract>() {
    private val compositeDisposable = CompositeDisposable()
    private var currentPage: Int = 1
    private var isRefreshing: Boolean = false
    private var isLoadingMore: Boolean = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMovies()
    }

    private fun loadMovies() {
        val d = moviesListInteractor
                .discoverMovies(
                        discoverStrategy.sortBy,
                        currentPage,
                        discoverStrategy.voteCount,
                        LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ handleMovies(it) }, { handleError(it) })
        compositeDisposable.add(d)
    }

    private fun handleMovies(movies: List<Movie>) {
        if (isRefreshing) {
            viewState.setMovies(movies)
        } else {
            viewState.addMovies(movies)
        }
        viewState.hideLoading()
        isRefreshing = false
        isLoadingMore = false
        currentPage++
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun onSwipeRefreshed() {
        viewState.showLoading()
        isRefreshing = true
        loadMovies()
    }

    private fun handleError(t: Throwable) {
        viewState.hideLoading()
        isRefreshing = false
        isLoadingMore = false
        viewState.showErrorMessage(t.localizedMessage)
    }

    fun bottomIsReached() {
        if (!isLoadingMore) {
            isLoadingMore = true
            loadMovies()
        }
    }

    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }

}
