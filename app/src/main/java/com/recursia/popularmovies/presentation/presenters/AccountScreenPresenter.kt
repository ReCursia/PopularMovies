package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.AccountScreenInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import com.recursia.popularmovies.presentation.views.contracts.AccountScreenContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class AccountScreenPresenter(
        private val accountScreenInteractor: AccountScreenInteractor,
        private val router: Router
) : MvpPresenter<AccountScreenContract>() {
    private val compositeDisposable = CompositeDisposable()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initUserData()
        initUserMovies()
    }

    private fun initUserMovies() {
        val statuses = MovieStatus.values().filter { it != MovieStatus.UNKNOWN }
        for (status in statuses) {
            val d = accountScreenInteractor
                    .getUserMoviesWithStatus(status)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { viewState.setMoviesWithStatus(it, status) },
                            { viewState.showErrorMessage(it.localizedMessage) }
                    )
            compositeDisposable.add(d)
        }
    }

    private fun initUserData() {
        val d = accountScreenInteractor
                .getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { viewState.setUserData(it) },
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

    fun onAboutIconClicked() {
        viewState.showAboutDialog()
    }

    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }
}