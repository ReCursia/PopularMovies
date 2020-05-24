package com.recursia.popularmovies.presentation.presenters

import com.recursia.popularmovies.domain.DetailScreenInteractor
import com.recursia.popularmovies.domain.models.Review
import com.recursia.popularmovies.presentation.views.contracts.ReviewsContract
import com.recursia.popularmovies.utils.LangUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ReviewsPresenter(
        private val detailScreenInteractor: DetailScreenInteractor,
        private val movieId: Int
) : MvpPresenter<ReviewsContract>() {
    private val compositeDisposable = CompositeDisposable()
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initData()
    }

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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

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
