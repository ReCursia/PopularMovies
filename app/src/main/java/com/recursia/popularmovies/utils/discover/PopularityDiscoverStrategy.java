package com.recursia.popularmovies.utils.discover;

import com.recursia.popularmovies.utils.NetworkUtils;

public class PopularityDiscoverStrategy implements DiscoverStrategy {

    @Override
    public String getSortBy() {
        return NetworkUtils.POPULARITY;
    }

    @Override
    public int getVoteCount() {
        return NetworkUtils.VOTE_COUNT_POPULARITY;
    }

}
