package com.recursia.popularmovies.presentation.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.domain.models.Trailer;
import com.recursia.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
    private static final int FADE_OUT_DURATION = 100; //ms

    private final Context context;
    private List<Trailer> trailers;
    private OnItemClickListener<Trailer> clickListener;

    public TrailersAdapter(Context context) {
        this.context = context;
        trailers = new ArrayList<>();
    }

    public void setClickListener(OnItemClickListener<Trailer> clickListener) {
        this.clickListener = clickListener;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item, viewGroup, false);
        return new TrailerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int i) {
        Trailer trailer = trailers.get(i);
        //Image
        Glide.with(context)
                .load(String.format(NetworkUtils.TRAILER_IMAGE_FORMAT_URL, trailer.getKey()))
                .placeholder(R.drawable.ic_trailer_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(trailerViewHolder.trailerImage);
        //Title
        trailerViewHolder.trailerTitle.setText(trailer.getName());
        //Play button
        trailerViewHolder.playButton.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(trailer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.playButton)
        ImageView playButton;
        @BindView(R.id.trailerTitle)
        TextView trailerTitle;
        @BindView(R.id.trailerImage)
        ImageView trailerImage;

        TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
