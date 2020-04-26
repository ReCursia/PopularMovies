package com.recursia.popularmovies;

import android.app.Application;

import com.recursia.popularmovies.di.AppComponent;
import com.recursia.popularmovies.di.DaggerAppComponent;
import com.recursia.popularmovies.di.modules.InteractorModule;
import com.recursia.popularmovies.di.modules.MapperModule;
import com.recursia.popularmovies.di.modules.MoviesRetrofitModule;
import com.recursia.popularmovies.di.modules.NavigationModule;
import com.recursia.popularmovies.di.modules.RepositoryModule;
import com.recursia.popularmovies.di.modules.RetrofitAdaptersModule;
import com.recursia.popularmovies.di.modules.RoomModule;
import com.recursia.popularmovies.di.modules.TranslateRetrofitModule;

public class TheApplication extends Application {
    private static TheApplication instance;
    private AppComponent appComponent;

    public static TheApplication getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                    .builder()
                    .interactorModule(new InteractorModule())
                    .mapperModule(new MapperModule())
                    .repositoryModule(new RepositoryModule())
                    .retrofitAdaptersModule(new RetrofitAdaptersModule())
                    .moviesRetrofitModule(new MoviesRetrofitModule())
                    .translateRetrofitModule(new TranslateRetrofitModule())
                    .roomModule(new RoomModule(this))
                    .navigationModule(new NavigationModule())
                    .build();
        }
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
