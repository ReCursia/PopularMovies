package com.example.popularmovies.adapters.trailers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.R;
import com.example.popularmovies.adapters.OnItemClickListener;
import com.example.popularmovies.pojo.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private List<Trailer> trailers;
    private OnItemClickListener clickListener;

    public TrailersAdapter() {
        trailers = new ArrayList<>();
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public Trailer getItem(int position) {
        return trailers.get(position);
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
        trailerViewHolder.trailerTitle.setText(trailer.getName());
        trailerViewHolder.playButton.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(trailerViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}