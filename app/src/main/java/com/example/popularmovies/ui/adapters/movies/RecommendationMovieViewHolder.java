package com.example.popularmovies.ui.adapters.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendationMovieViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.smallMoviePoster)
    ImageView moviePoster;
    @BindView(R.id.movieTitleTextView)
    TextView movieTitleTextView;
    @BindView(R.id.movieRatingTextView)
    TextView movieRatingTextView;

    public RecommendationMovieViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
