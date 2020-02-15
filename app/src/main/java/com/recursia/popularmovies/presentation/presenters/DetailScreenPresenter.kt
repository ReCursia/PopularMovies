package com.recursia.popularmovies.presentation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.DetailScreenInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.presentation.views.contracts.DetailScreenContract
import com.recursia.popularmovies.utils.LangUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

@InjectViewState
class DetailScreenPresenter(private val detailScreenInteractor: DetailScreenInteractor, private val router: Router, private val movieId: Int) : MvpPresenter<DetailScreenContract>() {
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.hideFavoriteIcon()
        viewState.hideMovieDetail()
        viewState.hideRecommendationMovies()
        initData()
    }

    private fun initData() {
        loadMovie()
        loadMovieRecommendations()
    }

    private fun loadMovie() {
        val d = detailScreenInteractor
                .getMovieById(movieId, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleMovie(it) },
                        { t -> viewState.showErrorMessage(t.localizedMessage) }
                )
        compositeDisposable.add(d)
    }

    private fun loadMovieRecommendations() {
        val d = detailScreenInteractor
                .getMovieRecommendations(movieId, MOVIE_RECOMMENDATION_PAGE, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ r ->
                    viewState.setRecommendationMovies(r)
                    viewState.showRecommendationMovies()
                },
                        { t -> viewState.showErrorMessage(t.localizedMessage) }
                )
        compositeDisposable.add(d)
    }

    private fun handleMovie(movie: Movie) {
        viewState.setMovieDetail(movie)
        viewState.showMovieDetail()
        if (movie.isFavorite) {
            viewState.setFavoriteIconOn()
        } else {
            viewState.setFavoriteIconOff()
        }
        viewState.showFavoriteIcon()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose() //our activity is destroyed, disposing all subscriptions
    }

    fun onFavoriteIconClicked(movie: Movie) {
        if (movie.isFavorite) {
            removeMovieFavorite(movie)
        } else {
            makeMovieFavorite(movie)
        }

    }

    private fun removeMovieFavorite(movie: Movie) {
        val d = detailScreenInteractor
                .removeFavoriteMovie(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.setFavoriteIconOff()
                    viewState.showMovieRemovedMessage()
                }
        compositeDisposable.add(d)
    }

    private fun makeMovieFavorite(movie: Movie) {
        val d = detailScreenInteractor
                .makeFavoriteMovie(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.setFavoriteIconOn()
                    viewState.showMovieAddedMessage()
                }
        compositeDisposable.add(d)
    }

    fun onShareIconClicked(movie: Movie?) {
        if (movie != null) {
            viewState.shareMovie(movie)
        }
    }

    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }

    fun onBackdropImageClicked(movie: Movie?) {
        if (movie != null) {
            router.navigateTo(Screens.PhotoScreen(movie.backdropPath!!))
        }
    }

    fun onTrailerPlayButtonClicked(trailer: Trailer) {
        viewState.openTrailerUrl(trailer)
    }

    fun onBackPressed() {
        router.exit()
    }

    companion object {
        private const val MOVIE_RECOMMENDATION_PAGE = 1
    }

}
