package com.recursia.popularmovies.presentation.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.presentation.models.SectionItem;
import com.recursia.popularmovies.utils.TagUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionScreenFragment extends MvpAppCompatFragment {
    @BindView(R.id.section_lottieAnimation)
    LottieAnimationView sectionImage;
    @BindView(R.id.section_textView)
    TextView sectionText;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //Setting data
        Bundle bundle = getArguments();
        SectionItem item = (SectionItem) bundle.getSerializable(TagUtils.FRAGMENT_INTRO);
        setSectionData(item);
    }

    private void setSectionData(SectionItem item) {
        //Image
        sectionImage.setAnimation(item.getFileName());
        sectionImage.playAnimation();
        //Text
        sectionText.setText(item.getDescription());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro_section, container, false);
    }

}
