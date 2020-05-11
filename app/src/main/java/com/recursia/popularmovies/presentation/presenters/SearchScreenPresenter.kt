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

@InjectViewState
class SearchScreenPresenter(
        private val searchScreenInteractor: SearchScreenInteractor,
        private val router: Router
) : MvpPresenter<SearchScreenContract>() {
    private val compositeDisposable = CompositeDisposable()
    private val subject = BehaviorSubject.create<String>()

    override fun onFirstViewAttach() {
        initLiveSearch()
    }

    private fun initLiveSearch() {
        val d = subject
                .debounce(TIMEOUT.toLong(), TIME_UNIT, AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe { updateDisplayedList(it) }
        compositeDisposable.add(d)
    }

    private fun updateDisplayedList(query: String) {
        val d = searchScreenInteractor
                .getMoviesByQuery(query, QUERY_PAGE, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { m -> viewState.setMovies(m) },
                        { t -> viewState.showErrorMessage(t.localizedMessage) }
                )
        compositeDisposable.add(d)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onItemClicked(item: Movie) {
        router.navigateTo(Screens.DetailScreen(item.id))
    }

    fun onQueryTextChanged(query: String) {
        if (query.isNotEmpty()) {
            subject.onNext(query)
        }
    }

    fun onBackPressed() {
        router.exit()
    }

    companion object {
        private const val TIMEOUT = 300
        private val TIME_UNIT = TimeUnit.MILLISECONDS
        private const val QUERY_PAGE = 1
    }
}
