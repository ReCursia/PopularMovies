package com.recursia.popularmovies.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.recursia.popularmovies.presentation.ui.navigation.Navigator;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Navigator.openIntroActivity(this);
        finish();
    }
}
