package com.example.popularmovies.ui.adapters.moviesRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.popularmovies.R;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.ui.adapters.OnItemClickListener;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private final static int FADE_OUT_DURATION = 100; //ms

    private final Context context;
    private List<Movie> movies;
    private OnItemClickListener<Movie> clickListener;

    public MoviesAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    public void setClickListener(OnItemClickListener<Movie> clickListener) {
        this.clickListener = clickListener;
    }

    public Movie getItem(int position) {
        return movies.get(position);
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
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int i) {
        Movie movie = movies.get(i);
        Glide.with(context)
                .load(NetworkUtils.BASE_POSTER_URL + NetworkUtils.BIG_POSTER_SIZE + movie.getPosterPath())
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(movieViewHolder.moviePoster);

        movieViewHolder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(movie);
            }
        });
    }

}
