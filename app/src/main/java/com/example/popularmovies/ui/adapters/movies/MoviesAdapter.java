package com.example.popularmovies.ui.adapters.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.popularmovies.R;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.ui.adapters.OnItemClickListener;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int FADE_OUT_DURATION = 100; //ms
    private final Context context;
    private List<Movie> movies;
    private OnItemClickListener<Movie> clickListener;
    private boolean isRecommendationMovies;

    public MoviesAdapter(Context context, boolean isRecommendationMovies) {
        this.context = context;
        this.isRecommendationMovies = isRecommendationMovies;
        movies = new ArrayList<>();
    }

    public void setClickListener(OnItemClickListener<Movie> clickListener) {
        this.clickListener = clickListener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> newMovies) {
        int posBefore = getItemCount();
        movies.addAll(newMovies);
        notifyItemRangeInserted(posBefore, newMovies.size());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (isRecommendationMovies) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recommendation_movie_item, viewGroup, false);
            return new RecommendationMovieViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
            return new MovieViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder movieViewHolder, int i) {
        Movie movie = movies.get(i);
        if (isRecommendationMovies) {
            setRecommendationMovieData(movie, (RecommendationMovieViewHolder) movieViewHolder);
        } else {
            setPlainMovieData(movie, (MovieViewHolder) movieViewHolder);
        }
    }

    private void setRecommendationMovieData(Movie movie, RecommendationMovieViewHolder movieViewHolder) {
        //Title
        movieViewHolder.movieTitleTextView.setText(movie.getTitle());
        //Rating
        movieViewHolder.movieRatingTextView.setText(Double.toString(movie.getVoteAverage()));
        //Image
        Glide.with(context)
                .load(NetworkUtils.getBigPosterUrl(movie.getPosterPath()))
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(movieViewHolder.moviePoster);
        //Listener
        movieViewHolder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(movie);
            }
        });
    }

    private void setPlainMovieData(Movie movie, MovieViewHolder movieViewHolder) {
        //Title
        movieViewHolder.movieTitleTextView.setText(movie.getTitle());
        //Rating
        movieViewHolder.movieRatingTextView.setText(Double.toString(movie.getVoteAverage()));
        //Image
        Glide.with(context)
                .load(NetworkUtils.getBigPosterUrl(movie.getPosterPath()))
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(movieViewHolder.moviePoster);
        //Listener
        movieViewHolder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(movie);
            }
        });
    }

}
