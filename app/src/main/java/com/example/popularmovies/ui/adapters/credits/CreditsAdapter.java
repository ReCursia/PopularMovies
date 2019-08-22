package com.example.popularmovies.ui.adapters.credits;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.popularmovies.R;
import com.example.popularmovies.models.pojo.Cast;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditViewHolder> {
    private static final int FADE_OUT_DURATION = 100; //ms

    private final Context context;
    private List<Cast> cast;

    public CreditsAdapter(Context context) {
        this.context = context;
        cast = new ArrayList<>();
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_item, viewGroup, false);
        return new CreditViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CreditViewHolder movieViewHolder, int i) {
        Cast castItem = cast.get(i);
        //Image
        Glide.with(context)
                .load(NetworkUtils.getMediumProfileUrl(castItem.getProfilePath()))
                .placeholder(R.drawable.ic_user_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(FADE_OUT_DURATION))
                .into(movieViewHolder.castImage);
        //Name
        movieViewHolder.castName.setText(castItem.getName());
    }

}
