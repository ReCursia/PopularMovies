package com.recursia.popularmovies.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.recursia.popularmovies.R
import com.recursia.popularmovies.TheApplication
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.User
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import com.recursia.popularmovies.presentation.presenters.AccountScreenPresenter
import com.recursia.popularmovies.presentation.views.adapters.MoviesAdapter
import com.recursia.popularmovies.presentation.views.contracts.AccountScreenContract
import com.recursia.popularmovies.presentation.views.decorations.MarginItemDecoration
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class AccountScreenFragment : MvpAppCompatFragment(), AccountScreenContract {


    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.profile_image)
    lateinit var profileImage: ImageView

    @BindView(R.id.text_view_user_name)
    lateinit var textViewUserName: TextView

    @BindView(R.id.text_view_registration_date)
    lateinit var textViewRegistrationDate: TextView

    @BindView(R.id.recycler_view_want_to_watch)
    lateinit var recyclerViewWantToWatch: RecyclerView

    @BindView(R.id.recycler_view_already_saw)
    lateinit var recyclerViewAlreadySaw: RecyclerView

    @BindView(R.id.recycler_view_favorite)
    lateinit var recyclerViewFavorite: RecyclerView

    @InjectPresenter
    lateinit var presenter: AccountScreenPresenter


    @ProvidePresenter
    fun providePresenter(): AccountScreenPresenter {
        val app = TheApplication.getInstance().appComponent
        return AccountScreenPresenter(app.accountScreenInteractor, app.router)
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
    }

    private fun initRecyclerViewsAndAdapters() {
        val statuses = MovieStatus.values().filter { it != MovieStatus.UNKNOWN }
        for (status in statuses) {
            val adapter = MoviesAdapter(context!!, status.toString())
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
        textViewRegistrationDate.text = user.registrationDate

        textViewUserName.visibility = View.VISIBLE
        textViewRegistrationDate.visibility = View.VISIBLE
    }

    override fun showAboutDialog() {
        //TODO load old dialog about
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun getInstance() = AccountScreenFragment()
    }
}