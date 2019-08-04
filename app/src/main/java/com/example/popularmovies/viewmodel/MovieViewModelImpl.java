package com.example.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.pojo.FavoriteMovie;
import com.example.popularmovies.pojo.Movie;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieViewModelImpl extends AndroidViewModel implements MovieViewModel {

    private static MovieDatabase database;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavoriteMovie>> favoriteMovies;
    private LifecycleOwner owner;
    private ViewModelDataChangedListener listener;

    public MovieViewModelImpl(@NonNull Application application, LifecycleOwner owner) {
        super(application);
        this.owner = owner;
        database = MovieDatabase.getInstance(getApplication());
        movies = database.movieDao().getAllMovies();
        favoriteMovies = database.favoriteMovieDao().getAllFavoriteMovies();
        initMoviesListener();
        initFavoriteMoviesListener();
    }

    private void initMoviesListener() {
        movies.observe(owner, movies -> {
            if (listener != null) {
                listener.moviesDataChanged(movies);
            }
        });
    }

    private void initFavoriteMoviesListener() {
        favoriteMovies.observe(owner, favoriteMovies -> {
            if (listener != null) {
                listener.favoriteMoviesDataChanged(favoriteMovies);
            }
        });
    }

    @Override
    public void deleteFavoriteMovie(FavoriteMovie movie) {
        (new DeleteFavoriteMovieTask()).execute(movie);
    }

    @Override
    public void getAllMovies() {
        try {
            movies = new GetAllMoviesTask().execute().get();
            initMoviesListener();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllFavoriteMovies() {
        (new DeleteAllFavoriteMoviesTask()).execute();
    }

    @Override
    public void insertMovies(List<Movie> movies) {
        (new InsertMoviesTask()).execute(movies);
    }

    @Override
    public void insertFavoriteMovie(FavoriteMovie movie) {
        (new InsertFavoriteMovieTask()).execute(movie);
    }

    @Override
    public void insertFavoriteMovies(List<FavoriteMovie> movies) {
        (new InsertFavoriteMoviesTask()).execute(movies);
    }

    @Override
    public void setDataChangedListener(ViewModelDataChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public FavoriteMovie getFavoriteMovieById(int id) {
        try {
            return new GetFavoriteMovieTask().execute(id).get(); //TODO разве не блокирует поток?
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Movie getMovieById(int id) {
        try {
            return new GetMovieTask().execute(id).get(); //TODO разве не блокирует поток?
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAllMovies() {
        (new DeleteAllMoviesTask()).execute();
    }

    @Override
    public void insertMovie(Movie movie) {
        (new InsertMovieTask()).execute(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        (new DeleteMovieTask()).execute(movie);
    }

    private static class GetAllMoviesTask extends AsyncTask<Void, Void, LiveData<List<Movie>>> {
        @Override
        protected LiveData<List<Movie>> doInBackground(Void... voids) {
            return database.movieDao().getAllMovies();
        }
    }

    private static class GetFavoriteMovieTask extends AsyncTask<Integer, Void, FavoriteMovie> {
        @Override
        protected FavoriteMovie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.favoriteMovieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, Movie> {
        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }

    private static class InsertFavoriteMovieTask extends AsyncTask<FavoriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.favoriteMovieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertFavoriteMoviesTask extends AsyncTask<List<FavoriteMovie>, Void, Void> {
        @Override
        protected Void doInBackground(List<FavoriteMovie>... movies) {
            if (movies != null && movies.length > 0) {
                database.favoriteMovieDao().insertMovies(movies[0]);
            }
            return null;
        }
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertMoviesTask extends AsyncTask<List<Movie>, Void, Void> {
        @Override
        protected Void doInBackground(List<Movie>... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertMovies(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteFavoriteMovieTask extends AsyncTask<FavoriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.favoriteMovieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteAllFavoriteMoviesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.favoriteMovieDao().deleteAllMovies();
            return null;
        }
    }


    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }
}
