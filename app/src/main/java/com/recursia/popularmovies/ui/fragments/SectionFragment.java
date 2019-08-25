package com.recursia.popularmovies.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.models.pojo.SectionItem;
import com.recursia.popularmovies.presenters.SectionFragmentPresenter;
import com.recursia.popularmovies.utils.TagUtils;
import com.recursia.popularmovies.views.SectionContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionFragment extends MvpAppCompatFragment implements SectionContract {
    @BindView(R.id.section_lottieAnimation)
    LottieAnimationView sectionImage;
    @BindView(R.id.section_textView)
    TextView sectionText;

    @InjectPresenter
    SectionFragmentPresenter presenter;

    @ProvidePresenter
    SectionFragmentPresenter providePresenter() {
        Bundle bundle = getArguments();
        SectionItem item = (SectionItem) bundle.getSerializable(TagUtils.FRAGMENT_INTRO);
        return new SectionFragmentPresenter(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro_section, container, false);
    }

    @Override
    public void setSectionData(SectionItem item) {
        //Image
        sectionImage.setAnimation(item.getFileName());
        sectionImage.playAnimation();
        //Text
        sectionText.setText(item.getDescription());
    }

}
