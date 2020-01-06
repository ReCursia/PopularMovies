package com.recursia.popularmovies.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.recursia.popularmovies.Screens;
import com.recursia.popularmovies.domain.SearchScreenInteractor;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.presentation.views.contracts.SearchScreenContract;
import com.recursia.popularmovies.utils.LangUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class SearchScreenPresenter extends MvpPresenter<SearchScreenContract> {
    private final static int TIMEOUT = 300;
    private final static TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private final static int QUERY_PAGE = 1;
    private final SearchScreenInteractor searchScreenInteractor;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Subject<String> subject = BehaviorSubject.create();
    private final Router router;

    public SearchScreenPresenter(SearchScreenInteractor searchScreenInteractor,
                                 Router router) {
        this.searchScreenInteractor = searchScreenInteractor;
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        initLiveSearch();
    }

    private void initLiveSearch() {
        Disposable d = subject
                .debounce(TIMEOUT, TIME_UNIT, AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe(this::updateDisplayedList);
        compositeDisposable.add(d);
    }

    private void updateDisplayedList(String query) {
        Disposable d = searchScreenInteractor
                .getMoviesByQuery(query, QUERY_PAGE, LangUtils.getDefaultLanguage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (m) -> getViewState().setMovies(m),
                        (t) -> getViewState().showErrorMessage(t.getLocalizedMessage())
                );
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    public void onItemClicked(Movie item) {
        router.navigateTo(new Screens.DetailScreen(item.getId()));
    }

    public void onQueryTextChanged(String query) {
        if (!query.isEmpty()) {
            subject.onNext(query);
        }
    }

    public void onBackPressed() {
        router.exit();
    }

}
