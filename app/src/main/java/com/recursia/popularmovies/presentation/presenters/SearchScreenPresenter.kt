package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.domain.SearchScreenInteractor
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.presentation.views.contracts.SearchScreenContract
import com.recursia.popularmovies.utils.LangUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Search screen presenter
 */
@InjectViewState
class SearchScreenPresenter(
        private val searchScreenInteractor: SearchScreenInteractor, //search screen interactor
        private val router: Router // router for navigating
) : MvpPresenter<SearchScreenContract>() {
    private val compositeDisposable = CompositeDisposable() // for all disposables when detach view
    private val subject = BehaviorSubject.create<String>() // subject to distinct user queries
    private var isLoading = false // is loading movies
    private var currentPage = 1 //current page, by default = 1
    private var currentQuery: String? = null // current user query

    /**
     * Calls on first view attach
     */
    override fun onFirstViewAttach() {
        initLiveSearch()
    }

    /**
     * Function to init live search
     */
    private fun initLiveSearch() {
        val d = subject
                .debounce(TIMEOUT.toLong(), TIME_UNIT, AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe { updateDisplayedList(it) }
        compositeDisposable.add(d)
    }

    /**
     * Function to update displayed list
     * @param query user query
     */
    private fun updateDisplayedList(query: String) {
        val d = searchScreenInteractor
                .getMoviesByQuery(query, currentPage, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { m -> viewState.setMovies(m) },
                        { t -> viewState.showErrorMessage(t.localizedMessage) }
                )
        compositeDisposable.add(d)
    }

    /**
     * Calls on destroy
     */
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    /**
     * On item clicekd callback
     * @param movie movie to navigate
     */
    fun onItemClicked(item: Movie) {
        router.navigateTo(Screens.DetailScreen(item.id))
    }

    /**
     * On query text changed handler (emit to subject)
     * @param query user query
     */
    fun onQueryTextChanged(query: String) {
        if (query.isNotEmpty()) {
            if (query != currentQuery) {
                currentPage = 1
            }
            currentQuery = query
            subject.onNext(query)
        }
    }

    /**
     * On back pressed callback
     */
    fun onBackPressed() {
        router.exit()
    }

    /**
     * On bottom list reached callback
     */
    fun bottomIsReached() {
        if (!isLoading) {
            val d = searchScreenInteractor
                    .getMoviesByQuery(currentQuery!!, currentPage + 1, LangUtils.defaultLanguage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { isLoading = false }
                    .doOnSuccess { currentPage += 1 }
                    .doOnSubscribe { isLoading = true }
                    .subscribe(
                            { viewState.addMovies(it) },
                            { viewState.showErrorMessage(it.localizedMessage) }
                    )
            compositeDisposable.add(d)
        }
    }

    companion object {
        private const val TIMEOUT = 300 // milis callback
        private val TIME_UNIT = TimeUnit.MILLISECONDS //time unit
    }
}
