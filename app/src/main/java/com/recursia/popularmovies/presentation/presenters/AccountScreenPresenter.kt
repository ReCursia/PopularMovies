package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.AccountScreenInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import com.recursia.popularmovies.presentation.views.contracts.AccountScreenContract
import com.recursia.popularmovies.utils.intro.AuthPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * User entity model
 */
@InjectViewState
class AccountScreenPresenter(
        private val accountScreenInteractor: AccountScreenInteractor, //account screen interactor
        private val router: Router, // router for navigation
        private val authPreferences: AuthPreferences // prefs to save persistent data
) : MvpPresenter<AccountScreenContract>() {
    private val compositeDisposable = CompositeDisposable() // to dispose after detach view

    /**
     * Calls on first view attach
     */
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initUserData()
        initUserMovies()
    }

    /**
     * Function to init user movies
     */
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

    /**
     * Function to init user data
     */
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

    /**
     * Calls on destroy
     */
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    /**
     * On back pressed callback
     */
    fun onBackPressed() {
        router.exit()
    }

    /**
     * Ob about icon clicked callback
     */
    fun onAboutIconClicked() {
        viewState.showAboutDialog()
    }

    /**
     * On movie clicked callback
     */
    fun onMovieClicked(movie: Movie) {
        router.navigateTo(Screens.DetailScreen(movie.id))
    }

    /**
     * On sign out clicked callback
     */
    fun onSignOutClicked() {
        authPreferences.setAuthorized(false)
        router.exit()
    }

    /**
     * Function to set profile image
     * @param imageUri image path
     */
    fun onImageProfileChosen(imageUri: String?) {
        imageUri?.let {
            val d = accountScreenInteractor
                    .setUserProfileImage(imageUri)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { initUserData() },
                            { viewState.showErrorMessage(it.localizedMessage) }
                    )
            compositeDisposable.add(d)
        }
    }

    /**
     * On positive dialog button clicked callback
     */
    fun onPositiveDialogButtonClicked() {
        viewState.openGooglePlayPage()
    }

    /**
     * On negative dialog button clicked callback
     */
    fun onNegativeDialogButtonClicked() {
        viewState.hideAboutDialog()
    }

    /**
     * On dismiss dialog callback
     */
    fun onDismissDialog() {
        viewState.hideAboutDialog()
    }
}
