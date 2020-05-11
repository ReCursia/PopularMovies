package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.presentation.presenters.AuthScreenPresenter
import com.recursia.popularmovies.presentation.views.contracts.AuthScreenContract

class AuthScreenFragment : MvpAppCompatFragment(), AuthScreenContract {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.text_input_email)
    lateinit var textInputEmail: TextInputLayout

    @BindView(R.id.text_input_password)
    lateinit var textInputPassword: TextInputLayout

    @BindView(R.id.forgot_password_button)
    lateinit var forgotPasswordButton: Button

    @BindView(R.id.button_sign_in)
    lateinit var signInButton: Button

    @BindView(R.id.button_sign_up)
    lateinit var signUpButton: Button

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @InjectPresenter
    lateinit var presenter: AuthScreenPresenter

    @ProvidePresenter
    fun providePresenter(): AuthScreenPresenter {
        val appComponent = TheApplication.getInstance().appComponent
        return AuthScreenPresenter(appComponent.authScreenInteractor, appComponent.router)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initListeners()
    }


    private fun initListeners() {
        forgotPasswordButton.setOnClickListener {
            presenter.onForgotButtonClicked()
        }
        signInButton.setOnClickListener {
            val email = textInputEmail.editText.toString()
            val password = textInputPassword.editText.toString()
            Toast.makeText(context!!, isEmailValid(email).toString() + " " + isPasswordValid(password).toString(), Toast.LENGTH_LONG).show()

            if (isEmailValid(email) && isPasswordValid(password)) {
                textInputEmail.isErrorEnabled = false
                textInputPassword.isErrorEnabled = false
                presenter.onSignInButtonClicked(email, password)
            }
        }
        signUpButton.setOnClickListener {
            val email = textInputEmail.editText.toString()
            val password = textInputPassword.editText.toString()
            Toast.makeText(context!!, isEmailValid(email).toString() + " " + isPasswordValid(password).toString(), Toast.LENGTH_LONG).show()

            if (isEmailValid(email) && isPasswordValid(password)) {
                textInputEmail.isErrorEnabled = false
                textInputPassword.isErrorEnabled = false
                presenter.onSignUpButtonClicked(email, password)
            }
        }
    }

    override fun showProgressingDialog() {
        signInButton.visibility = View.INVISIBLE
        signUpButton.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressingDialog() {
        signInButton.visibility = View.VISIBLE
        signUpButton.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO move all strings to resources
        if (password.isBlank()) {
            textInputPassword.error = "Password cannot be empty!"
            return false
        }
        return true
    }

    private fun isEmailValid(email: String): Boolean {
        if (email.isBlank()) {
            textInputEmail.error = "Email cannot be empty!"
            return false
        }
        //TODO fix it with pattern
        val isCorrectEmail = true
        if (!isCorrectEmail) {
            textInputEmail.error = "Email is invalid!"
            return false
        }
        return true
    }

    private fun initToolbar() {
        toolbar.title = resources.getString(R.string.account_title)
        toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            presenter.onBackPressed()
        }
    }

    companion object {
        fun getInstance() = AuthScreenFragment()
    }


}