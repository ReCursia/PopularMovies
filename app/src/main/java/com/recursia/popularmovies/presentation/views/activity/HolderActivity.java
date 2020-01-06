package com.recursia.popularmovies.presentation.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.recursia.popularmovies.R;
import com.recursia.popularmovies.Screens;
import com.recursia.popularmovies.TheApplication;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class HolderActivity extends AppCompatActivity {

    private final Navigator navigator = new SupportAppNavigator(this, R.id.main_container) {
        @Override
        public void applyCommands(Command[] commands) {
            super.applyCommands(commands);
            getSupportFragmentManager().executePendingTransactions();
        }
    };
    private NavigatorHolder navigatorHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        navigatorHolder = TheApplication
                .getInstance()
                .getAppComponent()
                .getNavigationHolder();

        if (savedInstanceState == null) {
            navigator.applyCommands(new Command[]{new Replace(new Screens.IntroScreen())});
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

}
