package com.example.popularmovies.utils.discover;

import com.example.popularmovies.utils.NetworkUtils;

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
