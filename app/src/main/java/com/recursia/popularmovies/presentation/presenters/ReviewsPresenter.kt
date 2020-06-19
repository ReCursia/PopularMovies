package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.domain.DetailScreenInteractor
import com.recursia.popularmovies.domain.models.Review
import com.recursia.popularmovies.presentation.views.contracts.ReviewsContract
import com.recursia.popularmovies.utils.LangUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Movie detail presenter
 */
@InjectViewState
class ReviewsPresenter(
        private val detailScreenInteractor: DetailScreenInteractor, // detail screen interacotr
        private val movieId: Int // movie id
) : MvpPresenter<ReviewsContract>() {
    private val compositeDisposable = CompositeDisposable() // for all disposables when detach view

    /**
     * Calls on first view attach
     */
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initData()
    }

    /**
     * Function to init data
     */
    private fun initData() {
        val d = detailScreenInteractor
                .getMovieById(movieId, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { viewState.setReviews(it.reviews) },
                        { viewState.showErrorMessage(it.localizedMessage) }
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
     * On translate review clicked callback
     */
    fun onTranslateReviewClicked(review: Review, position: Int) {
        val d = detailScreenInteractor
                .translateReview(review, LangUtils.defaultLanguage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { viewState.updateReview(review, position) },
                        { viewState.showErrorMessage(it.localizedMessage) }
                )
        compositeDisposable.add(d)
    }
}
