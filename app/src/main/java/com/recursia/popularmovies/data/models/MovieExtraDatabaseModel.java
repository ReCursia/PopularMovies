package com.recursia.popularmovies.data.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieExtraDatabaseModel {
    @Embedded
    private MovieDatabaseModel movie;

    @Relation(parentColumn = "id", entity = GenreDatabaseModel.class, entityColumn = "movieId")
    private List<GenreDatabaseModel> genres;
    @Relation(parentColumn = "id", entity = CastDatabaseModel.class, entityColumn = "movieId")
    private List<CastDatabaseModel> cast;
    @Relation(parentColumn = "id", entity = TrailerDatabaseModel.class, entityColumn = "movieId")
    private List<TrailerDatabaseModel> trailers;

    public MovieDatabaseModel getMovie() {
        return movie;
    }

    public void setMovie(MovieDatabaseModel movie) {
        this.movie = movie;
    }

    public List<GenreDatabaseModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDatabaseModel> genres) {
        this.genres = genres;
    }

    public List<CastDatabaseModel> getCast() {
        return cast;
    }

    public void setCast(List<CastDatabaseModel> cast) {
        this.cast = cast;
    }

    public List<TrailerDatabaseModel> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailerDatabaseModel> trailers) {
        this.trailers = trailers;
    }

}
