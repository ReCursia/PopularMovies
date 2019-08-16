package com.example.popularmovies.ui.adapters.trailers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.popularmovies.R;
import com.example.popularmovies.models.pojo.Trailer;
import com.example.popularmovies.ui.adapters.OnItemClickListener;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
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

}
