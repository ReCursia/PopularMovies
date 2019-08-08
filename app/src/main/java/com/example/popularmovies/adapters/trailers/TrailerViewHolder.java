package com.example.popularmovies.adapters.trailers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.playButton)
    ImageView playButton;
    @BindView(R.id.trailerTitle)
    TextView trailerTitle;

    TrailerViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
