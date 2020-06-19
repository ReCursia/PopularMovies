package com.recursia.popularmovies.presentation.presenters

import android.util.Patterns
import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.presentation.views.contracts.AuthScreenContract
import com.recursia.popularmovies.utils.intro.AuthPreferences
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Authorization screen presenter
 */
@InjectViewState
class AuthScreenPresenter(
        private val authPreferences: AuthPreferences, // prefs to save persistent data
        private val router: Router // router for navigating
) : MvpPresenter<AuthScreenContract>() {

    /**
     * On back pressed callback
     */
    fun onBackPressed() {
        router.exit()
    }

    /**
     * On forgot button clicked callback
     */
    fun onForgotButtonClicked() {
        viewState.showResetPasswordBottomSheet()
    }

    /**
     * On sign in button clicked callback
     * @param email email to sign in
     * @param password password to sign in
     */
    fun onSignInButtonClicked(email: String, password: String) {
        if (isValid(email, password)) {
            viewState.signIn(email, password)
        }
    }

    /**
     * On sign up button clicked callback
     * @param email email to sign up
     * @param password password to sign up
     */
    fun onSignUpButtonClicked(email: String, password: String) {
        if (isValid(email, password)) {
            viewState.signUp(email, password)
        }
    }

    /**
     * Function to valid email and password
     * @param email email
     * @param password password
     *
     * @return true if valid, false otherwise
     */
    private fun isValid(email: String, password: String): Boolean {
        val emailIsValid = emailIsValid(email)
        val passwordIsValid = passwordIsValid(password)

        viewState.setShowEmailValidationError(emailIsValid)
        viewState.setShowPasswordValidationError(passwordIsValid)
        return emailIsValid && passwordIsValid
    }

    /**
     * Function to check password is valid
     * @param password password
     *
     * @return true if valid, false otherwise
     */
    private fun passwordIsValid(password: String) = password.isNotEmpty()

    /**
     * Function to check email is valid
     * @param email email
     *
     * @return true if valid, false otherwise
     */
    private fun emailIsValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    /**
     * On success sign callback
     */
    fun onSuccessSign() {
        authPreferences.setAuthorized(true)
        router.replaceScreen(Screens.AccountScreen())
    }
}
