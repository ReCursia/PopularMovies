package com.recursia.popularmovies.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.recursia.popularmovies.ui.navigation.Navigator;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Navigator.openIntroActivity(this);
        finish();
    }
}
