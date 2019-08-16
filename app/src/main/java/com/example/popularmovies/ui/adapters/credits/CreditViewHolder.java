package com.example.popularmovies.ui.adapters.credits;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreditViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.castImage)
    ImageView castImage;
    @BindView(R.id.castName)
    TextView castName;

    public CreditViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
