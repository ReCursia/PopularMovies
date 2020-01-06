package com.recursia.popularmovies.presentation.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.recursia.popularmovies.R;
import com.recursia.popularmovies.TheApplication;
import com.recursia.popularmovies.presentation.models.SectionItem;
import com.recursia.popularmovies.presentation.presenters.IntroScreenPresenter;
import com.recursia.popularmovies.presentation.views.adapters.SectionsPagerAdapter;
import com.recursia.popularmovies.presentation.views.contracts.IntroScreenContract;
import com.recursia.popularmovies.utils.intro.PrefUtilsImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroScreenFragment extends MvpAppCompatFragment implements IntroScreenContract {
    private static final int DOT_SIZE = 35;
    private static final String DOT_FROM_HTML = "&#8226";

    @BindView(R.id.section_view_pager)
    ViewPager sectionViewPager;
    @BindView(R.id.finish_button)
    Button finishButton;
    @BindView(R.id.previous_button)
    Button previousButton;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.dots_linear_layout)
    LinearLayout dotsLinearLayout;
    @InjectPresenter
    IntroScreenPresenter presenter;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private List<TextView> dots;

    public static IntroScreenFragment getInstance() {
        return new IntroScreenFragment();
    }

    @ProvidePresenter
    IntroScreenPresenter providePresenter() {
        String[] descriptions = getResources().getStringArray(R.array.intro_descriptions);
        String[] filePaths = getResources().getStringArray(R.array.intro_file_paths);
        List<SectionItem> sectionItems = new ArrayList<>();
        for (int i = 0; i < descriptions.length; i++) {
            sectionItems.add(new SectionItem(filePaths[i], descriptions[i]));
        }
        TheApplication app = TheApplication.getInstance();
        return new IntroScreenPresenter(new PrefUtilsImpl(getContext()), sectionItems, app.getAppComponent().getRouter());
    }

    @OnClick(R.id.next_button)
    public void onNextButtonClicked() {
        presenter.onNextButtonClicked();
    }

    @OnClick(R.id.previous_button)
    public void onPreviousButtonClicked() {
        presenter.onPreviousButtonClicked();
    }

    @OnClick(R.id.finish_button)
    public void onFinishButtonClicked() {
        presenter.onFinishButtonClicked();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
    }

    private void initViewPager() {
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        sectionViewPager.setAdapter(sectionsPagerAdapter);
        sectionViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                presenter.onPageSelected(i);
                updateDots(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void updateDots(int position) {
        for (TextView dot : dots) {
            dot.setTextColor(getResources().getColor(R.color.colorTransparentWhite));
        }
        dots.get(position).setTextColor(getResources().getColor(R.color.white));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setViewPagerData(List<SectionItem> sectionItems) {
        sectionsPagerAdapter.setSections(sectionItems);
        initDots();
    }

    private void initDots() {
        dots = new ArrayList<>();
        for (int i = 0; i < sectionsPagerAdapter.getCount(); i++) {
            TextView dot = new TextView(getContext());
            dot.setText(Html.fromHtml(DOT_FROM_HTML));
            dot.setTextSize(DOT_SIZE);
            dot.setTextColor(i == 0 ? getResources().getColor(R.color.white) :
                    getResources().getColor(R.color.colorTransparentWhite));
            dotsLinearLayout.addView(dot);
            dots.add(dot);
        }
    }

    @Override
    public void setNextSection() {
        sectionViewPager.setCurrentItem(sectionViewPager.getCurrentItem() + 1);
    }

    @Override
    public void setPreviousSection() {
        sectionViewPager.setCurrentItem(sectionViewPager.getCurrentItem() - 1);
    }

    @Override
    public void showNextButton() {
        nextButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNextButton() {
        nextButton.setVisibility(View.GONE);
    }

    @Override
    public void showPreviousButton() {
        previousButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePreviousButton() {
        previousButton.setVisibility(View.GONE);
    }

    @Override
    public void showFinishButton() {
        finishButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFinishButton() {
        finishButton.setVisibility(View.GONE);
    }

}
