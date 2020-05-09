package com.recursia.popularmovies.utils.discover

import com.recursia.popularmovies.utils.NetworkUtils

class TopRatedDiscoverStrategy : DiscoverStrategy {

    override val sortBy: String
        get() = NetworkUtils.TOP_RATED

    override val voteCount: Int
        get() = NetworkUtils.VOTE_COUNT_TOP_RATED
}
