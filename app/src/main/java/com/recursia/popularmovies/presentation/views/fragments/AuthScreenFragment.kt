package com.recursia.popularmovies.presentation.views.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.presentation.presenters.AuthScreenPresenter
import com.recursia.popularmovies.presentation.views.contracts.AuthScreenContract
import com.recursia.popularmovies.utils.intro.PreferencesImpl
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

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
        return AuthScreenPresenter(PreferencesImpl(context!!), appComponent.router)
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
            val email = textInputEmail.editText!!.text.toString().trim()
            val password = textInputPassword.editText!!.text.toString().trim()
            presenter.onSignInButtonClicked(email, password)
        }
        signUpButton.setOnClickListener {
            val email = textInputEmail.editText!!.text.toString().trim()
            val password = textInputPassword.editText!!.text.toString().trim()
            presenter.onSignUpButtonClicked(email, password)
        }
    }

    private fun showProgressingBar() {
        signInButton.visibility = View.INVISIBLE
        signUpButton.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressingBar() {
        signInButton.visibility = View.VISIBLE
        signUpButton.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    override fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity as Activity) {
                    if (it.isSuccessful) {
                        presenter.onSuccessSign()
                    } else {
                        showErrorMessage(it.exception!!.localizedMessage)
                    }
                    hideProgressingBar()
                }
        showProgressingBar()
    }

    override fun signUp(email: String, password: String) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity as Activity) {
                    if (it.isSuccessful) {
                        presenter.onSuccessSign()
                    } else {
                        showErrorMessage(it.exception!!.localizedMessage)
                    }
                    hideProgressingBar()
                }
        showProgressingBar()
    }

    override fun setShowEmailValidationError(error: Boolean) {
        textInputEmail.error = if (error) {
            resources.getString(R.string.edit_text_email_error)
        } else {
            ""
        }
    }

    override fun setShowPasswordValidationError(error: Boolean) {
        textInputPassword.error = if (error) {
            resources.getString(R.string.edit_text_password_error)
        } else {
            ""
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
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