package com.recursia.popularmovies.utils.discover;

import com.recursia.popularmovies.utils.NetworkUtils;

public class TopRatedDiscoverStrategy implements DiscoverStrategy {

    @Override
    public String getSortBy() {
        return NetworkUtils.TOP_RATED;
    }

    @Override
    public int getVoteCount() {
        return NetworkUtils.VOTE_COUNT_TOP_RATED;
    }

}
