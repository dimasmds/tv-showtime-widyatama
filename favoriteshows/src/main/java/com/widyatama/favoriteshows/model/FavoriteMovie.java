package com.widyatama.favoriteshows.model;

import android.database.Cursor;

import static android.provider.BaseColumns._ID;
import static com.widyatama.favoriteshows.db.DatabaseContract.MovieColumn.COLUMN_MOVIE_BACKDROP;
import static com.widyatama.favoriteshows.db.DatabaseContract.MovieColumn.COLUMN_MOVIE_OVERVIEW;
import static com.widyatama.favoriteshows.db.DatabaseContract.MovieColumn.COLUMN_MOVIE_RATING;
import static com.widyatama.favoriteshows.db.DatabaseContract.MovieColumn.COLUMN_MOVIE_TITLE;
import static com.widyatama.favoriteshows.db.DatabaseContract.MovieColumn.COLUMN_MOVIE_VOTER;

public class FavoriteMovie {

    private int id;
    private String movieTitle;
    private String movieOverview;
    private double movieRating;
    private int movieVoter;
    private String movieBackdrop;

    public FavoriteMovie(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        this.movieTitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_TITLE));
        this.movieOverview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_OVERVIEW));
        this.movieRating = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_RATING));
        this.movieVoter = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_VOTER));
        this.movieBackdrop = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_BACKDROP));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public int getMovieVoter() {
        return movieVoter;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

}
