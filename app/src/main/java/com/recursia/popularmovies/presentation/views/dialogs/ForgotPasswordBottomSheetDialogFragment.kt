package com.recursia.popularmovies.presentation.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.recursia.popularmovies.R
import com.recursia.popularmovies.presentation.views.dialogs.base.MvpBottomSheetDialogFragment

class ForgotPasswordBottomSheetDialogFragment : MvpBottomSheetDialogFragment() {
    @BindView(R.id.button_restore_password)
    lateinit var buttonRestorePassword: Button

    @BindView(R.id.text_input_email)
    lateinit var textInputEmail: TextInputLayout

    override fun getTheme() = R.style.CustomBottomSheetDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_forgot_password, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonRestorePassword.setOnClickListener {
            FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(textInputEmail.editText!!.text.toString().trim())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(context!!, getString(R.string.reset_password_success), Toast.LENGTH_LONG).show()
                            dismiss()
                        } else {
                            Toast.makeText(context!!, it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }

    companion object {
        fun getInstance() = ForgotPasswordBottomSheetDialogFragment()
    }
}
