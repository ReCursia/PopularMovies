package com.recursia.popularmovies.presentation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.recursia.popularmovies.domain.DetailScreenInteractor
import com.recursia.popularmovies.presentation.views.contracts.DetailScreenContract
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

@InjectViewState
class DetailScreenPresenter(
        private val detailScreenInteractor: DetailScreenInteractor,
        private val router: Router,
        private val movieId: Int
) : MvpPresenter<DetailScreenContract>() {
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.hideFavoriteIcon()
        viewState.hideMovieDetail()
        viewState.hideRecommendationMovies()
        initData()
    }

    private fun initData() {
        //TODO implement
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onBackPressed() {
        router.exit()
    }

    companion object {
        private const val MOVIE_RECOMMENDATION_PAGE = 1
    }
}
