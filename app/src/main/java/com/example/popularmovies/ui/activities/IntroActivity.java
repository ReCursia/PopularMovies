package com.example.popularmovies.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.popularmovies.R;
import com.example.popularmovies.presenters.IntroPresenter;
import com.example.popularmovies.ui.adapters.intro.SectionsPagerAdapter;
import com.example.popularmovies.views.IntroContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends MvpAppCompatActivity implements IntroContract {
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
    IntroPresenter presenter;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private List<TextView> dots;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        initViewPager();
        initDots();
    }

    private void initViewPager() {
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
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

    private void initDots() {
        dots = new ArrayList<>();
        for (int i = 0; i < sectionsPagerAdapter.getCount(); i++) {
            TextView dot = new TextView(this);
            dot.setText(Html.fromHtml(DOT_FROM_HTML));
            dot.setTextSize(DOT_SIZE);
            dot.setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            dotsLinearLayout.addView(dot);
            dots.add(dot);
        }
        dots.get(0).setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void openMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish(); // call this to finish the current activity
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
