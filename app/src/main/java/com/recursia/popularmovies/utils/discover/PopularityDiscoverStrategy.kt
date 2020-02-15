package com.recursia.popularmovies.utils.discover

import com.recursia.popularmovies.utils.NetworkUtils

class PopularityDiscoverStrategy : DiscoverStrategy {

    override val sortBy: String
        get() = NetworkUtils.POPULARITY

    override val voteCount: Int
        get() = NetworkUtils.VOTE_COUNT_POPULARITY

}
