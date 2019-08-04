package com.example.popularmovies.pojo;

import android.arch.persistence.room.Entity;

@Entity(tableName = "favorite_movies")
public class FavoriteMovie extends Movie {
    public FavoriteMovie(int voteCount, int id, boolean video,
                         double voteAverage, String title, double popularity,
                         String posterPath, String originalLanguage,
                         String originalTitle, String backdropPath,
                         boolean adult, String overview,
                         String releaseDate) {
        super(voteCount, id, video,
                voteAverage, title, popularity,
                posterPath, originalLanguage, originalTitle,
                backdropPath, adult, overview, releaseDate);
    }

    public static FavoriteMovie fromMovie(Movie movie) {
        return new FavoriteMovie(movie.getVoteCount(), movie.getId(), movie.isVideo(), movie.getVoteAverage(),
                movie.getTitle(), movie.getPopularity(), movie.getPosterPath(), movie.getOriginalLanguage(),
                movie.getOriginalTitle(), movie.getBackdropPath(), movie.isAdult(),
                movie.getOverview(), movie.getReleaseDate());
    }
}
