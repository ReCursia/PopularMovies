package com.example.popularmovies.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.models.pojo.SectionItem;
import com.example.popularmovies.presenters.SectionFragmentPresenter;
import com.example.popularmovies.views.SectionContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionFragment extends MvpAppCompatFragment implements SectionContract {
    @BindView(R.id.section_imageView)
    ImageView sectionImage;
    @BindView(R.id.section_textView)
    TextView sectionText;

    @InjectPresenter
    SectionFragmentPresenter presenter;

    @ProvidePresenter
    SectionFragmentPresenter providePresenter() {
        Bundle bundle = getArguments();
        SectionItem item = (SectionItem) bundle.getSerializable("item");
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
    public void setData(SectionItem item) {
        //Image
        sectionImage.setImageResource(item.getImageId());
        //Text
        sectionText.setText(item.getDescription());
    }

}
