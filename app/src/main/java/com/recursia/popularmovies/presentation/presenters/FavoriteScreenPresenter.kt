package com.recursia.popularmovies.presentation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.FavoriteScreenInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.presentation.views.contracts.FavoriteScreenContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

@InjectViewState
class FavoriteScreenPresenter(private val favoriteScreenInteractor: FavoriteScreenInteractor, private val router: Router) : MvpPresenter<FavoriteScreenContract>() {
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.hideNoFavoriteScreen()
        initMovies()
    }

    private fun initMovies() {
        val d = favoriteScreenInteractor.allFavoriteMovies
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ handleResult(it) }, { handleError(it) })
        compositeDisposable.add(d)
    }

    private fun handleResult(movies: List<Movie>) {
        if (movies.isNotEmpty()) {
            viewState.setMovies(movies)
            viewState.hideNoFavoriteScreen()
        } else {
            viewState.showNoFavoriteScreen()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun handleError(t: Throwable) {
        viewState.showErrorMessage(t.localizedMessage)
    }

    fun onItemClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }

    fun onBackPressed() {
        router.exit()
    }

}
