package com.recursia.popularmovies.models.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class MovieExtra {
    @Embedded
    private Movie movie;

    @Relation(parentColumn = "id", entity = Genre.class, entityColumn = "movieId")
    private List<Genre> genres;
    @Relation(parentColumn = "id", entity = Cast.class, entityColumn = "movieId")
    private List<Cast> cast;
    @Relation(parentColumn = "id", entity = Trailer.class, entityColumn = "movieId")
    private List<Trailer> trailers;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

}
