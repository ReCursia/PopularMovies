package com.recursia.popularmovies.presentation.views.dialogs.base

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import moxy.MvpDelegate
import moxy.MvpDelegateHolder

open class MvpBottomSheetDialogFragment : BottomSheetDialogFragment(), MvpDelegateHolder {
    private var stateSaved = false
    private lateinit var mvpDelegate: MvpDelegate<out MvpBottomSheetDialogFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        stateSaved = false
        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        stateSaved = true
        getMvpDelegate().onSaveInstanceState(outState)
        getMvpDelegate().onDetach()
    }

    override fun onStop() {
        super.onStop()
        getMvpDelegate().onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getMvpDelegate().onDetach()
        getMvpDelegate().onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.activity!!.isFinishing) {
            getMvpDelegate().onDestroy()
        } else if (stateSaved) {
            stateSaved = false
        } else {
            var anyParentIsRemoving = false
            var parent = this.parentFragment
            while (!anyParentIsRemoving && parent != null) {
                anyParentIsRemoving = parent.isRemoving
                parent = parent.parentFragment
            }
            if (this.isRemoving || anyParentIsRemoving) {
                getMvpDelegate().onDestroy()
            }
        }
    }

    override fun getMvpDelegate(): MvpDelegate<*> {
        if (!this::mvpDelegate.isInitialized) {
            mvpDelegate = MvpDelegate(this)
        }
        return mvpDelegate
    }
}
