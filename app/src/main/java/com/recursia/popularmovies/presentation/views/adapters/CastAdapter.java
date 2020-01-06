package com.recursia.popularmovies.presentation.views.adapters;

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
import com.recursia.popularmovies.domain.models.Cast;
import com.recursia.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private static final int FADE_OUT_DURATION = 100; //ms

    private final Context context;
    private List<Cast> cast;

    public CastAdapter(Context context) {
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
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_item, viewGroup, false);
        return new CastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CastViewHolder movieViewHolder, int i) {
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

    class CastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.castImage)
        ImageView castImage;
        @BindView(R.id.castName)
        TextView castName;

        CastViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
