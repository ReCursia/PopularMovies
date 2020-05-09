package com.recursia.popularmovies.presentation.views.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ortiz.touchview.TouchImageView
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.utils.NetworkUtils.getOriginalPosterUrl
import com.recursia.popularmovies.utils.TagUtils.IMAGE_PATH
import ru.terrakok.cicerone.Router

class PhotoScreenFragment : MvpAppCompatFragment() {
    @BindView(R.id.backdropImage)
    lateinit var backdropImage: TouchImageView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    private lateinit var router: Router
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photo, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appComponent = TheApplication
                .getInstance()
                .appComponent
        router = appComponent.router
        initToolbar()
        setImageData(arguments!!.getString(IMAGE_PATH)!!)
    }

    private fun initToolbar() {
        toolbar.setBackgroundColor(resources.getColor(R.color.black))
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { router.exit() }
        toolbar.title = getString(R.string.photo_detail)
    }

    private fun setImageData(imagePath: String) {
        Glide.with(this)
                .asBitmap()
                .load(getOriginalPosterUrl(imagePath))
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        backdropImage.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
    }

    companion object {
        fun getInstance(imagePath: String?): PhotoScreenFragment {
            val fragment = PhotoScreenFragment()
            val arguments = Bundle()
            arguments.putString(IMAGE_PATH, imagePath)
            fragment.arguments = arguments
            return fragment
        }
    }
}
