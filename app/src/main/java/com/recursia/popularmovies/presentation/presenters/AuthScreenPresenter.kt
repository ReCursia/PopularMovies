package com.recursia.popularmovies.presentation.presenters

import android.util.Patterns
import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.presentation.views.contracts.AuthScreenContract
import com.recursia.popularmovies.utils.intro.AuthPreferences
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class AuthScreenPresenter(
        private val authPreferences: AuthPreferences,
        private val router: Router
) : MvpPresenter<AuthScreenContract>() {

    fun onBackPressed() {
        router.exit()
    }

    fun onForgotButtonClicked() {
        viewState.showResetPasswordBottomSheet()
    }

    fun onSignInButtonClicked(email: String, password: String) {
        if (isValid(email, password)) {
            viewState.signIn(email, password)
        }
    }

    fun onSignUpButtonClicked(email: String, password: String) {
        if (isValid(email, password)) {
            viewState.signUp(email, password)
        }
    }

    private fun isValid(email: String, password: String): Boolean {
        val emailIsValid = emailIsValid(email)
        val passwordIsValid = passwordIsValid(password)

        viewState.setShowEmailValidationError(emailIsValid)
        viewState.setShowPasswordValidationError(passwordIsValid)
        return emailIsValid && passwordIsValid
    }

    private fun passwordIsValid(password: String) = password.isNotEmpty()

    private fun emailIsValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun onSuccessSign() {
        authPreferences.setAuthorized(true)
        router.replaceScreen(Screens.AccountScreen())
    }
}
