package com.recursia.popularmovies.presentation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.AuthScreenInteractor
import com.recursia.popularmovies.presentation.views.contracts.AuthScreenContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router


@InjectViewState
class AuthScreenPresenter(
        private val authScreenInteractor: AuthScreenInteractor,
        private val router: Router
) : MvpPresenter<AuthScreenContract>() {
    private val compositeDisposable = CompositeDisposable()
    //TODO save data to preferences

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onBackPressed() {
        router.exit()
    }

    fun onForgotButtonClicked() {
        router.navigateTo(Screens.ResetPasswordScreen())
    }

    fun onSignInButtonClicked(email: String, password: String) {
        val d = authScreenInteractor
                .signIn(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgressingDialog() }
                .subscribe(
                        { router.replaceScreen(Screens.AccountScreen()) },
                        {
                            viewState.hideProgressingDialog()
                            viewState.showErrorMessage(it.localizedMessage)
                        }
                )
        compositeDisposable.add(d)
    }

    fun onSignUpButtonClicked(email: String, password: String) {
        //TODO move subscribeOn in repository btw (do finally??)
        val d = authScreenInteractor
                .signUp(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgressingDialog() }
                .subscribe(
                        {
                            router.replaceScreen(Screens.AccountScreen())
                        },
                        {
                            viewState.hideProgressingDialog()
                            viewState.showErrorMessage(it.localizedMessage)
                        }
                )
        compositeDisposable.add(d)
    }
}