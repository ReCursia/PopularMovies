package com.recursia.popularmovies.presentation.views.fragments

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.User
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import com.recursia.popularmovies.presentation.presenters.AccountScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.adapters.common.MovieMediumItemType
import com.recursia.popularmovies.presentation.views.contracts.AccountScreenContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import com.recursia.popularmovies.utils.NetworkUtils
import com.recursia.popularmovies.utils.intro.PreferencesImpl
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class AccountScreenFragment : MvpAppCompatFragment(), AccountScreenContract {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.profile_image)
    lateinit var profileImage: ImageView

    @BindView(R.id.button_sign_out)
    lateinit var buttonSignOut: Button

    @BindView(R.id.text_view_user_name)
    lateinit var textViewUserName: TextView

    @BindView(R.id.text_view_email)
    lateinit var textViewEmail: TextView

    @BindView(R.id.recycler_view_want_to_watch)
    lateinit var recyclerViewWantToWatch: RecyclerView

    @BindView(R.id.recycler_view_already_saw)
    lateinit var recyclerViewAlreadySaw: RecyclerView

    @BindView(R.id.recycler_view_favorite)
    lateinit var recyclerViewFavorite: RecyclerView

    @InjectPresenter
    lateinit var presenter: AccountScreenPresenter

    private var aboutDialog: AlertDialog? = null

    @ProvidePresenter
    fun providePresenter(): AccountScreenPresenter {
        val app = TheApplication.getInstance().appComponent
        return AccountScreenPresenter(app.accountScreenInteractor, app.router, PreferencesImpl(context!!))
    }

    private val statusMap = mutableMapOf<MovieStatus, MoviesAdapter>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        // Recycler view
        initRecyclerViewsAndAdapters()

        // Button sign out
        buttonSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            presenter.onSignOutClicked()
        }

        // Image profile listener
        initImageProfileListener()
    }

    private fun initImageProfileListener() {
        profileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            presenter.onImageProfileChosen(data?.data?.toString())
        }
    }

    private fun initRecyclerViewsAndAdapters() {
        val statuses = MovieStatus.values().filter { it != MovieStatus.UNKNOWN }
        for (status in statuses) {
            val adapter = MoviesAdapter(context!!, MovieMediumItemType(), status.toString())
            adapter.setOnClickListener {
                presenter.onMovieClicked(it)
            }
            statusMap[status] = adapter
            setRecyclerViewAdapter(status, adapter)
        }
    }

    private fun setRecyclerViewAdapter(status: MovieStatus, adapter: MoviesAdapter) {
        when (status) {
            MovieStatus.WANT_TO_WATCH -> initRecyclerView(adapter, recyclerViewWantToWatch)
            MovieStatus.FAVORITE -> initRecyclerView(adapter, recyclerViewFavorite)
            MovieStatus.ALREADY_SAW -> initRecyclerView(adapter, recyclerViewAlreadySaw)
            else -> throw IllegalStateException()
        }
    }

    private fun initRecyclerView(adapter: MoviesAdapter, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(
                MarginItemDecoration(context!!, 7, 7, 0, 0)
        )
        recyclerView.adapter = adapter
    }

    private fun initToolbar() {
        toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        toolbar.title = resources.getString(R.string.account_title)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            presenter.onBackPressed()
        }

        toolbar.inflateMenu(R.menu.account_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.about_item) {
                presenter.onAboutIconClicked()
                return@setOnMenuItemClickListener true
            }
            false
        }
    }

    override fun setMoviesWithStatus(movies: List<Movie>, status: MovieStatus) {
        statusMap[status]?.setMovies(movies as MutableList<Movie>)
    }

    override fun setUserData(user: User) {
        textViewUserName.text = user.username
        textViewEmail.text = user.email

        textViewUserName.visibility = View.VISIBLE
        textViewEmail.visibility = View.VISIBLE

        Glide.with(context!!)
                .load(user.profileImagePath ?: "")
                .placeholder(R.drawable.ic_cast_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(profileImage)
    }

    override fun showAboutDialog() {
        aboutDialog = MaterialAlertDialogBuilder(context!!, R.style.CustomAlertDialogTheme)
                .setTitle(getString(R.string.about))
                .setMessage(getString(R.string.about_description))
                .setPositiveButton(getString(R.string.rate_app_dialog_positive_button)) { _: DialogInterface, _: Int -> presenter.onPositiveDialogButtonClicked() }
                .setNegativeButton(getString(R.string.rate_app_negative_button)) { _: DialogInterface, _: Int -> presenter.onNegativeDialogButtonClicked() }
                .setOnDismissListener { presenter.onDismissDialog() }
                .show()
    }

    override fun hideAboutDialog() {
        aboutDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        aboutDialog?.setOnDismissListener(null)
        aboutDialog?.dismiss()
    }

    override fun openGooglePlayPage() {
        val uri: Uri = Uri.parse(NetworkUtils.GOOGLE_PLAY_NATIVE + context!!.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            // Try to open in Google Play
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            // Open in browser
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(NetworkUtils.GOOGLE_PLAY_URL + context!!.packageName)))
        }
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun getInstance() = AccountScreenFragment()
        private const val FADE_OUT_DURATION = 100 // ms
        private const val IMAGE_REQUEST_CODE = 30 // does not matter
    }
}
