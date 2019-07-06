package com.widyatama.tvshowtime.core.db.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_BACKDROP;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_ID;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_OVERVIEW;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_RATING;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_TITLE;
import static com.widyatama.tvshowtime.core.db.helper.DatabaseContract.MovieColumn.COLUMN_MOVIE_VOTER;

public class FavoriteTVShows {

    private int id;
    private int movieId;
    private String movieTitle;
    private String movieOverview;
    private double movieRating;
    private int movieVoter;
    private String movieBackdrop;

    public FavoriteTVShows() {
    }

    public FavoriteTVShows(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        this.movieId = cursor.getInt(cursor.getColumnIndex(COLUMN_MOVIE_ID));
        this.movieTitle = cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_TITLE));
        this.movieOverview = cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_OVERVIEW));
        this.movieRating = cursor.getDouble(cursor.getColumnIndex(COLUMN_MOVIE_RATING));
        this.movieVoter = cursor.getInt(cursor.getColumnIndex(COLUMN_MOVIE_VOTER));
        this.movieBackdrop = cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_BACKDROP));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public int getMovieVoter() {
        return movieVoter;
    }

    public void setMovieVoter(int movieVoter) {
        this.movieVoter = movieVoter;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
    }


}
