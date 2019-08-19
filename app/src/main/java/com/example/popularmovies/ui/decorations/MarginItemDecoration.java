package com.example.popularmovies.ui.decorations;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {
    private int marginLeft;
    private int marginRight;
    private int marginTop;
    private int marginBottom;

    public MarginItemDecoration(Context context, int marginLeft, int marginRight, int marginTop, int marginBottom) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        this.marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginLeft, metrics);
        this.marginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginRight, metrics);
        this.marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginTop, metrics);
        this.marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginBottom, metrics);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = marginLeft;
        outRect.right = marginRight;
        outRect.bottom = marginBottom;
        outRect.top = marginTop;
    }

}
